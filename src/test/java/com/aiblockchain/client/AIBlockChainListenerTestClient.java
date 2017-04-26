/**
 *
 */
package com.aiblockchain.client;

import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.util.HanaUtil;
import java.util.List;
import org.apache.log4j.Logger;

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
