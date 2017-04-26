package com.aiblockchain.server.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.aiblockchain.model.hana.HanaBlockInfo;
import com.aiblockchain.model.hana.HanaInfo;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.util.HanaUtil;
import com.aiblockchain.server.websocket.blockticker.TickerRequest;
import com.aiblockchain.server.websocket.blockticker.TickerResponse;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Created by Athi.
 */
public class BlockTickerMessageHandler implements WebSocketMessageHandler {
   private static final String STOCK_URL_START = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
   private static final String STOCK_URL_END = ")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
   
   // Keep track of the tickers the user has asked for info about
   private List<String> tickerSymbols = new CopyOnWriteArrayList<>();
   
   // Keep track of the BlockNumber the user has asked for
   private int blockNumber;

   // stateless JSON serializer/deserializer
   private Gson gson = new Gson();

   private static final AtomicBoolean keepRunning = new AtomicBoolean(true);

   //private static final Logger logger = LoggerFactory.getLogger(BlockTickerMessageHandler.class);
   Logger logger = Logger.getLogger("BlockTickerMessageHandler");
	  
   // Keep track of the current channel so we can talk directly to the client
   private AtomicReference<Channel> channel = new AtomicReference();

   // need an executor for the thread that will intermittently send data to the client
   private ExecutorService executor = Executors.newSingleThreadExecutor(
         new ThreadFactoryBuilder()
               .setDaemon(true)
               .setNameFormat("ticker-processor-%d")
               .build()
   );

   public BlockTickerMessageHandler() {
      SendResultsCallable toClientCallable = new SendResultsCallable();
      FutureTask<String> toClientPc = new FutureTask<>(toClientCallable);
      executor.execute(toClientPc);
   }

   public String handleMessage(ChannelHandlerContext ctx, String frameText) {
      this.channel.set(ctx.channel());
      TickerResponse tickerResponse = new TickerResponse();
      TickerRequest tickerRequest = gson.fromJson(frameText, TickerRequest.class);
	  
      logger.info("Ticket Server handleMessage " + frameText);
		 
      if (tickerRequest.getCommand() != null) {
         if ("add".equals(tickerRequest.getCommand())) {
            tickerSymbols.add(tickerRequest.getTickerSymbol());
            tickerResponse.setResult("success");
         } else if ("remove".equals(tickerRequest.getCommand())) {
            tickerSymbols.remove(tickerRequest.getTickerSymbol());
            tickerResponse.setResult("success");
         } else if ("startblock".equals(tickerRequest.getCommand())) {
			blockNumber = tickerRequest.getBlockNumber();
            tickerResponse.setResult("success");
         } else if ("getnewblock".equals(tickerRequest.getCommand())) {
        	 logger.info("Getting blocks from ai block chain starting from number 1 to 10");
			//HanaItems hanaItems = APIAdapter.getInstance().getBlocksStartingWith(1, 10);
            tickerResponse.setResult("success");
         } else {
            tickerResponse.setResult("Failed. Command not recognized.");
         }
      } else {
         tickerResponse.setResult("Failed. Command not recognized.");
      }

      String response = gson.toJson(tickerResponse);
      return response;
   }

   /**
    * This class runs as a thread, and waits for messages to arrive in its queue that are to be sent back to the client.
    *
    * @author jwb
    */
   class SendResultsCallable implements Callable<String> {
      // one per callable as it is stateless, but not thread safe
      private Gson gson = new Gson();
	HanaUtil util = new HanaUtil();
	HanaBlockItem blockItem = HanaItems.makeTestHanaBlockItem();

      public SendResultsCallable() {
      }

      @Override
      public String call() throws Exception {
         // keep going until all messages are sent
         while (keepRunning.get()) {
            if (tickerSymbols.size() > 0) {			
               TickerResponse tickerResponse = new TickerResponse();
               tickerResponse.setResult("success");
               tickerResponse.setTickerData(getPricesForSymbols(tickerSymbols));

               String response = gson.toJson(tickerResponse);

               // send the client an update
               channel.get().writeAndFlush(new TextWebSocketFrame(response));
            }  
			
			if (blockNumber != 0){
			   blockNumber++;
			   
				util.GetHanaBlockItem(blockItem);		
				String blockInfo = util.getHanaBlockInfo(blockItem);
				wrapandSendObject(blockInfo, "HanaBlockInfo");
				
				List<HanaTransactionItem> transactionList = util.getTransactionItem(blockItem);
				for (HanaTransactionItem item : transactionList) {
					String transInfo = util.getTransactionInfo(item);
					wrapandSendObject(transInfo, "HanaTransactionInfo");
	                
					String transInputInfo = util.getTransactionInputInfo(item);	
					wrapandSendObject(transInputInfo, "HanaTransactionInputInfo");
					
					String transOutputInfo = util.getTransactionOutputInfo(item);
					wrapandSendObject(transOutputInfo, "HanaTransactionOutputInfo");
				}
				
				logger.info ("**********************");
			   /*
               HanaBlockInfo hanaBlockInfo = new HanaBlockInfo();
               hanaBlockInfo.setBlockNumber(blockNumber);
               hanaBlockInfo.setBlockVersion((short)1);
               hanaBlockInfo.setBlockMerkleRoot("HELLO FROM JB for Block " + blockNumber);
               hanaBlockInfo.setBlockTime("123456");
               hanaBlockInfo.setBlockNoOftransactions(2);

               HanaInfo hanaInfo = new HanaInfo("HanaBlockInfo", hanaBlockInfo);
               String response = gson.toJson(hanaInfo);
               System.out.println("block response = " + response);
               //String response = gson.toJson(hanaBlockInfo);

               // send the client an update
               channel.get().writeAndFlush(new TextWebSocketFrame(response));
               
	            HanaTransactionInfo transInfo = new HanaTransactionInfo();
	            transInfo.setBlockNumber(1);
	            transInfo.setTransactionId("x2gdegabcdfe");
	            transInfo.setTransactionIndex(0);
	            transInfo.setTransactionNoOfInputs((short)1);
	            transInfo.setTransactionNoOfOutputs((short)1);
	            
	            hanaInfo = new HanaInfo("HanaTransactionInfo", transInfo);
	            response = gson.toJson(hanaInfo);
	            System.out.println("trans response = " + response);
	            channel.get().writeAndFlush(new TextWebSocketFrame(response)); 
	            */              
            }
            
            // only try to send back to client every 5 seconds so it isn't overwhelmed with messages
            Thread.sleep(5000L);
         }

         return "done";
      }

	private void wrapandSendObject(String hanaObject, String objectType) {
		String jsonStr = util.wrapObjectInHanaInfo(hanaObject, objectType);
		channel.get().writeAndFlush(new TextWebSocketFrame(jsonStr));
	}
   }

   private Map<String, String> getPricesForSymbols(List<String> symbols) {
      Map<String, String> response = new HashMap<>();
      String url = STOCK_URL_START;
      boolean first = true;
      for (String symbol : symbols) {
         if (first) {
            first = false;
         } else {
            url += "%2C";
         }
         url += "%22" + symbol + "%22";
      }
      
      url += STOCK_URL_END;

      CloseableHttpClient httpClient = HttpClients.custom()
         .addInterceptorFirst(new HttpRequestInterceptor() {

            public void process(
                  final HttpRequest request,
                  final HttpContext context) throws HttpException, IOException {
               if (!request.containsHeader("Accept-Encoding")) {
                  request.addHeader("Accept-Encoding", "gzip");
               }

            }
         }).addInterceptorFirst(new HttpResponseInterceptor() {

               public void process(
                     final HttpResponse response,
                     final HttpContext context) throws HttpException, IOException {
                  HttpEntity entity = response.getEntity();
                  if (entity != null) {
                     Header ceheader = entity.getContentEncoding();
                     if (ceheader != null) {
                        HeaderElement[] codecs = ceheader.getElements();
                        for (int i = 0; i < codecs.length; i++) {
                           if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                              response.setEntity(
                                    new GzipDecompressingEntity(response.getEntity()));
                              return;
                           }
                        }
                     }
                  }
               }
            }).build();

      try {
         HttpUriRequest query = RequestBuilder.get()
             .setUri(url)
             .build();
         CloseableHttpResponse queryResponse = httpClient.execute(query);
         try {
            HttpEntity entity = queryResponse.getEntity();
            if (entity != null) {
               String data = EntityUtils.toString(entity);
               JsonObject jsonObject = JsonObject.readFrom(data);
               jsonObject = jsonObject.get("query").asObject();
               jsonObject = jsonObject.get("results").asObject();
               if (jsonObject.get("quote").isArray()) {
                  JsonArray jsonArray = jsonObject.get("quote").asArray();
                  for (int i = 0; i < jsonArray.size(); i++) {
                     jsonObject = jsonArray.get(i).asObject();
                     String symbol = jsonObject.get("Symbol").asString();
                     String price = jsonObject.get("LastTradePriceOnly").asString();
                     response.put(symbol, price);
                  }
               } else {
                  jsonObject = jsonObject.get("quote").asObject();
                  String symbol = jsonObject.get("Symbol").asString();
                  String price = jsonObject.get("LastTradePriceOnly").asString();
                  response.put(symbol, price);
               }
            }
         } finally {
            queryResponse.close();
         }
      } catch (Exception e) {
      } finally {
         try {httpClient.close();} catch (Exception e) {}
      }

      return response;
   }
}
