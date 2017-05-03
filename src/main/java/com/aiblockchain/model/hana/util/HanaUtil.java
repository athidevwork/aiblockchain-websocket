/**
 * 
 */
package com.aiblockchain.model.hana.util;

import com.aiblockchain.model.hana.HanaBlockInfo;
import com.aiblockchain.model.hana.HanaInfo;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.google.gson.Gson;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Athi
 *
 */
public class HanaUtil {
	Logger l = Logger.getLogger(HanaUtil.class);
	
	public List<HanaBlockItem> getBlockItems(HanaItems hanaItems) {
		List<HanaBlockItem> BlockItemList = hanaItems.getHanaBlockItems();
		String jsonStr = new Gson().toJson(BlockItemList);
		//l.info("Hana transaction item = " + jsonStr);
		//l.info("-------------");
		return BlockItemList;
	}
	
	public HanaBlockItem getBlockItem(Gson gson, HanaBlockItem item) {
		HanaBlockItem blockItem = gson.fromJson(GetHanaBlockItem(item), HanaBlockItem.class);
		String jsonStr = GetHanaBlockItem(blockItem);
		//System.out.println(jsonStr);
		System.out.println(gson.fromJson(getHanaBlockInfo(blockItem), HanaBlockInfo.class));
		return blockItem;
	}
	
	public String wrapObjectInHanaInfo(String hanaObject, String objectType) {
		HanaInfo hanaInfo = new HanaInfo(objectType, hanaObject);
	   String jsonStr = new Gson().toJson(hanaInfo);
	   l.info("Hana Info = " + jsonStr);
	   return jsonStr;
	}
	
	public String getHanaBlockInfo(HanaBlockItem blockItem) {
		HanaBlockInfo blockInfo = blockItem.getHanaBlockInfo();
		String jsonStr = new Gson().toJson(blockInfo);
		//l.info("Hana block info = " + jsonStr);
		//l.info("-------------");
		return jsonStr;
	}

	public String GetHanaBlockItem(HanaBlockItem blockItem) {
		String jsonStr = new Gson().toJson(blockItem);
		//l.info("Hana block item = " + jsonStr);
		//l.info("=======================");
		return jsonStr;
	}
	
	public void getTransactionItems(Gson gson, HanaBlockItem blockItem) {
		List<HanaTransactionItem> transactionList = getTransactionItem(blockItem);
		for (HanaTransactionItem transitem : transactionList) {
			System.out.println(gson.fromJson(getTransactionInfo(transitem), HanaTransactionInfo.class));
			
			JSONArray jsonArray = new JSONArray(getTransactionInputInfo(transitem));
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
		        	JSONObject jsonObject = jsonArray.getJSONObject(i);
		        	String json1Str = jsonObject.toString();
		        	HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(json1Str, HanaTransactionInputInfo.class);
					System.out.println("Transaction Input = " + hanaTransInputInfo);		                    			
				}
			}
			
			jsonArray = new JSONArray(getTransactionOutputInfo(transitem));
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
		        	JSONObject jsonObject = jsonArray.getJSONObject(i);
		        	String json1Str = jsonObject.toString();
		        	HanaTransactionOutputInfo hanaTransOutputInfo = gson.fromJson(json1Str, HanaTransactionOutputInfo.class);
					System.out.println("Transaction Output = " + hanaTransOutputInfo);		                    			
				}
			}
		}
	}
	
	public void getHanaTransactionItem(Gson gson, HanaUtil util, HanaTransactionItem transactionItem) {
		JSONArray jsonArray = new JSONArray(getTransactionInputInfo(transactionItem));
		if (jsonArray.length() > 0) {
			for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        	String json1Str = jsonObject.toString();
	        	HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(json1Str, HanaTransactionInputInfo.class);
				System.out.println("Transaction Input = " + hanaTransInputInfo);		                    			
			}
		}
		
		jsonArray = new JSONArray(getTransactionOutputInfo(transactionItem));
		if (jsonArray.length() > 0) {
			for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        	String json1Str = jsonObject.toString();
	        	HanaTransactionOutputInfo hanaTransOutputInfo = gson.fromJson(json1Str, HanaTransactionOutputInfo.class);
				System.out.println("Transaction Output = " + hanaTransOutputInfo);		                    			
			}
		}	
		System.out.println(gson.fromJson(getTransactionInfo(transactionItem), HanaTransactionInfo.class));
	}

	public String GetHanaTransactionItem(HanaTransactionItem transactionItem) {
		String jsonStr = new Gson().toJson(transactionItem);
		//l.info("Hana block item = " + jsonStr);
		//l.info("=======================");
		return jsonStr;
	}
	
	
	public List<HanaTransactionItem> getTransactionItem(HanaBlockItem blockItem) {
		List<HanaTransactionItem> transactionList = blockItem.getHanaTransactionItems();
		String jsonStr = new Gson().toJson(transactionList);
		//l.info("Hana transaction item = " + jsonStr);
		//l.info("-------------");
		return transactionList;
	}

	public String getTransactionInfo(HanaTransactionItem item) {
		HanaTransactionInfo transInfo = item.getHanaTransactionInfo();
		String jsonStr = new Gson().toJson(transInfo);
		//l.info("Hana Transaction Info = " + jsonStr);
		return jsonStr;
	}
	
	public String getTransactionInputInfo(HanaTransactionItem item) {
		List<HanaTransactionInputInfo> inputInfoList = item.getHanaTransactionInputInfos();
		String jsonStr = new Gson().toJson(inputInfoList);
		//l.info("Hana Input Info = " + jsonStr);
		return jsonStr;
	}
	
	public String getTransactionOutputInfo(HanaTransactionItem item) {
		List<HanaTransactionOutputInfo> outputInfoList = item.getHanaTransactionOutputInfos();
		String jsonStr = new Gson().toJson(outputInfoList);
		//l.info("Hana Output Info = " + jsonStr);
		//l.info("-------------");
		return jsonStr;
	}
}
