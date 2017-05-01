/**
 *
 */
package com.aiblockchain.client;

import com.aiblockchain.model.hana.HanaBlockInfo;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.util.HanaUtil;
import com.google.gson.Gson;

import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Athi
 *
 */
public class AIBlockChainListenerTestClient {

  /**
   * @param args
   */
  public static void main(String[] args) {
    testHanaBlock();
  }

  private static void testHanaBlock() {
    Logger l = Logger.getLogger(AIBlockChainListenerTestClient.class);
    Gson gson = new Gson();

    l.info("Start to get contents Hana Block Item");
    HanaUtil util = new HanaUtil();
    HanaBlockItem blockItem = HanaItems.makeTestHanaBlockItem();

    util.GetHanaBlockItem(blockItem);
    System.out.println(gson.fromJson(util.getHanaBlockInfo(blockItem), HanaBlockInfo.class));

    List<HanaTransactionItem> transactionList = util.getTransactionItem(blockItem);
    for (HanaTransactionItem item : transactionList) {
    	System.out.println(gson.fromJson(util.getTransactionInfo(item), HanaTransactionInfo.class));
    	
    	JSONArray jsonArray = new JSONArray(util.getTransactionInputInfo(item));
    	if (jsonArray.length() > 0) {
    		for (int i = 0; i < jsonArray.length(); i++) {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
            	String jsonStr = jsonObject.toString();
            	HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(jsonStr, HanaTransactionInputInfo.class);
				System.out.println("Transaction Input = " + hanaTransInputInfo);		                    			
    		}
    	}
    	
    	jsonArray = new JSONArray(util.getTransactionOutputInfo(item));
    	if (jsonArray.length() > 0) {
    		for (int i = 0; i < jsonArray.length(); i++) {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
            	String jsonStr = jsonObject.toString();
            	HanaTransactionOutputInfo hanaTransOutputInfo = gson.fromJson(jsonStr, HanaTransactionOutputInfo.class);
				System.out.println("Transaction Output = " + hanaTransOutputInfo);		                    			
    		}
    	}
    }
    l.info("End of procedure");
  }
}
