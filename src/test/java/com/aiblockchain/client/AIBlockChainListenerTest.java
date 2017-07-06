package com.aiblockchain.client;

import com.aiblockchain.listener.AIBlockChainListener;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.server.websocket.fault.FaultRequest;
import com.aiblockchain.server.websocket.fault.FaultResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author reed
 */
public class AIBlockChainListenerTest {

  private static final Logger LOGGER = Logger.getLogger(AIBlockChainListenerTest.class);

  public AIBlockChainListenerTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    LOGGER.info("getInstance");
    AIBlockChainListener aiBlockChainListenerClient = AIBlockChainListener.getInstance();
    assertNotNull(aiBlockChainListenerClient);
    assertTrue(aiBlockChainListenerClient == AIBlockChainListener.getInstance());
    LOGGER.info("toString");
    assertEquals(
            "[AIBlockChainListener, singleton instance present: true, API adapter present: false]",
            aiBlockChainListenerClient.toString());
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of AIBlockChainListener methods.
   */
  @Test
  public void testVariousMethods() {
    LOGGER.info("setApiAdapter");
    AIBlockChainListener instance = AIBlockChainListener.getInstance();
    final AbstractAPIAdapter mockAPIAdapter = new MockAPIAdapter();
    instance.setApiAdapter(mockAPIAdapter);

    LOGGER.info("toString");
    assertEquals(
            "[AIBlockChainListener, singleton instance present: true, API adapter present: true]",
            instance.toString());
  }

  /**
   * Provides a mock HANA 2 demonstration API adapter for unit tests.
   */
  static final class MockAPIAdapter extends AbstractAPIAdapter {

    // the logger
    private static final Logger LOGGER = Logger.getLogger(MockAPIAdapter.class);

    /**
     * Returns a list of blocks starting with the given block number.
     *
     * @param startingBlockNumber the given block number of the first block returned
     * @param nbrOfBlocks the number of blocks to return, avoiding very large data structures possible with unlimited request
     *
     * @return the block items for blocks starting with the given block number limited by the number of blocks to return, or because the
     * blockchain has been exhausted by this request
     */
    @Override
    public HanaItems getBlocksStartingWith(long startingBlockNumber, int nbrOfBlocks) {
      final HanaItems hanaItems = new HanaItems();
      final List<HanaBlockItem> hanaBlockItems = new ArrayList<>();
      assertEquals("[HanaItems, 0 hanaBlockItems]", hanaItems.toString());
      hanaBlockItems.add(HanaItems.makeTestHanaBlockItem());
      hanaItems.setHanaBlockItems(hanaBlockItems);
      assertEquals("[HanaItems, 1 hanaBlockItems]", hanaItems.toString());
      return hanaItems;
    }

    /**
     * Gets the logger.
     *
     * @return the logger
     */
    @Override
    protected Logger getLogger() {
      return LOGGER;
    }

    @Override
    public FaultResponse updateFault(FaultRequest faultRequest) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  }

}
