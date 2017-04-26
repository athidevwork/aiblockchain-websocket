/**
 * 
 */
package com.aiblockchain.model.hana.util;

import com.aiblockchain.model.hana.HanaBlockInfo;
import com.aiblockchain.model.hana.HanaInfo;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.google.gson.Gson;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Athi
 *
 */
public class HanaUtil {
	Logger l = Logger.getLogger(HanaUtil.class);
	
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
