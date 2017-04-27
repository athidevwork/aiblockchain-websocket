/**
 * 
 */
package com.aiblockchain.listener;


import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

/**
 * @author Athi
 *
 */
public class AIBlockChainListener implements HanaListener {
	Logger logger = Logger.getLogger(AIBlockChainListener.class);
	
	@Override
	public void newBlockNotification(HanaBlockItem hanaBlockItem) {
		logger.info("Got new block notification ...");
		String blockStr = new Gson().toJson(hanaBlockItem);
		logger.info("HanaBlockItem = " + blockStr);
		
	}

}
