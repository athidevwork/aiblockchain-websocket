package com.aiblockchain.websocket;

import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;


public class webSocketClient {
  @Test
  public void testSendMessage() {	  
	  String orig = "original String before base64 encoding in Java";
	  String newString = "{id:2;rid:29;token:123443606811c7305ccc6abb2be116579bfd}";
	  String new1String = "{id:3;rid:30;token:567843606811c7305ccc6abb2be116579bfd}";

      //encoding  byte array into base 64
      byte[] encoded = Base64.encodeBase64(orig.getBytes());     
    
      System.out.println("Original String: " + orig );
      System.out.println("Base64 Encoded String : " + new String(encoded));
    
      //decoding byte array into base64
      byte[] decoded = Base64.decodeBase64(encoded);      
      System.out.println("Base 64 Decoded  String : " + new String(decoded));
      
      
      byte[] encoded1 = Base64.encodeBase64(newString.getBytes());       
      System.out.println("Original String: " + newString );
      System.out.println("Base64 Encoded String : " + new String(encoded1));
      byte[] encoded2 = Base64.encodeBase64(new1String.getBytes());       
      System.out.println("Original String: " + new1String );
      System.out.println("Base64 Encoded String : " + new String(encoded2));      
	  //WebSocket socket = new webSocket("ws://localhost:8083/websocket/?request=e2lkOjE7cmlkOjI2O3Rva2VuOiI0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExNjU3OWJmZCJ9123");
  }
}
