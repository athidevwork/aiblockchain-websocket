/**
 * 
 */
package com.aiblockchain.server.websocket;


import java.util.logging.Logger;

import com.aiblockchain.client.AbstractAPIAdapter;
import com.aiblockchain.model.hana.HanaItems;

/**
 * @author User
 *
 */
public class AIBlockChainApiAdapter extends AbstractAPIAdapter {
	Logger logger = Logger.getLogger("AIBlockChainApiAdapter");
	
	@Override
	public HanaItems getBlocksStartingWith(long startingBlockNumber, int nbrOfBlocks) {
		logger.info("getBlocksStartingWith block number = " + startingBlockNumber + ", number of blocks = " + nbrOfBlocks);
		
		return null;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
