/**
 * 
 */
package com.aiblockchain.listener;

import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;

/**
 * @author Athi
 *
 */
public class AIBlockChainListener implements HanaListener {

	@Override
	public void newBlockNotification(HanaBlockItem hanaBlockItem) {
		System.out.println("Got new HanaBlockItem");
		
	}

}
