/**
 * 
 */
package com.aiblockchain.client;

import java.util.List;

import java.util.logging.Logger;

import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.aiblockchain.model.hana.util.HanaUtil;
import com.google.gson.Gson;

/**
 * @author Athi
 *
 */
public class AIBlockChainListenerClient {	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testHanaBlock();
		
		/*AIBlockChainListener aiListener = new AIBlockChainListener();		
		APIAdapter.getInstance().addHanaListener(aiListener);
		System.out.println("Added AI Block Chain Listener to get block notifications");*/
	}
	
	private static void testHanaBlock() {
		Logger l = Logger.getLogger("AIBlockChainListenerClient");
		
		l.info("Start of procedure");
		HanaUtil util = new HanaUtil();
		HanaBlockItem blockItem = HanaItems.makeTestHanaBlockItem();
		
		util.GetHanaBlockItem(blockItem);		
		util.getHanaBlockInfo(blockItem);
		
		List<HanaTransactionItem> transactionList = util.getTransactionItem(blockItem);
		for (HanaTransactionItem item : transactionList) {
			util.getTransactionInfo(item);		
			util.getTransactionInputInfo(item);		
			util.getTransactionOutputInfo(item);
		}	
		l.info("End of procedure");
	}
}
