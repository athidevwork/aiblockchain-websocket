/**
 * 
 */
package com.aiblockchain.listener;

import java.util.logging.Logger;

import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.google.gson.Gson;

/**
 * @author Athi
 *
 */
public class AIBlockChainListener implements HanaListener {
	Logger logger = Logger.getLogger("AIBlockChainListener");
	
	@Override
	public void newBlockNotification(HanaBlockItem hanaBlockItem) {
		logger.info("Got new block notification ...");
		String blockStr = new Gson().toJson(hanaBlockItem);
		logger.info("HanaBlockItem = " + blockStr);
		
	}

}
