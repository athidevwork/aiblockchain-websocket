/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aiblockchain.client;

import com.aiblockchain.model.hana.HanaItems;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author reed
 */
public class AIBlockChainListenerClientTest {

  private static final Logger LOGGER = Logger.getLogger(AIBlockChainListenerClientTest.class);

  public AIBlockChainListenerClientTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    LOGGER.warn("setUpClass");
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
   * Test of getInstance method, of class AIBlockChainListenerClient.
   */
  @Test
  public void testGetInstance() {
    LOGGER.info("getInstance");
    AIBlockChainListenerClient expResult = null;
    AIBlockChainListenerClient result = AIBlockChainListenerClient.getInstance();
  }

  /**
   * Test of addHanaListener method, of class AIBlockChainListenerClient.
   */
  @Test
  public void testAddHanaListener() {
    LOGGER.info("addHanaListener");
    AIBlockChainListenerClient instance = null;
    instance.addHanaListener();
  }

  /**
   * Test of getBlocksStartingWith method, of class AIBlockChainListenerClient.
   */
  @Test
  public void testGetBlocksStartingWith() {
    LOGGER.info("getBlocksStartingWith");
    long startingBlockNumber = 0L;
    int nbrOfBlocks = 0;
    AIBlockChainListenerClient instance = null;
    HanaItems expResult = null;
    HanaItems result = instance.getBlocksStartingWith(startingBlockNumber, nbrOfBlocks);
  }

  /**
   * Test of setApiAdapter method, of class AIBlockChainListenerClient.
   */
  @Test
  public void testSetApiAdapter() {
    LOGGER.info("setApiAdapter");
    AbstractAPIAdapter apiAdapter = null;
    AIBlockChainListenerClient instance = null;
    instance.setApiAdapter(apiAdapter);
  }

  /**
   * Test of newBlockNotification method, of class AIBlockChainListenerClient.
   */
  @Test
  public void testNewBlockNotification() {
    LOGGER.info("newBlockNotification");
    HanaItems.HanaBlockItem hanaBlockItem = null;
    AIBlockChainListenerClient instance = null;
    instance.newBlockNotification(hanaBlockItem);
  }

  /**
   * Test of toString method, of class AIBlockChainListenerClient.
   */
  @Test
  public void testToString() {
    LOGGER.info("toString");
    AIBlockChainListenerClient instance = null;
    String expResult = "";
  }

}
