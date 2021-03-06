package load.client;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Athi
 *
 */
public class LoadTransactionsClient {
	static LoadTestConfig config = new LoadTestConfig();
	static Wallet wallet = new Wallet();
	
	public static Wallet getWallet() {
		return wallet;
	}

	public static LoadTestConfig getConfig() {
		return config;
	}

	public static void setConfig(LoadTestConfig config) {
		LoadTransactionsClient.config = config;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getLogger("HanaLoadClient");

		setupLoadTestConfig(logger);
		
		String senderAddress = getWallet().getFromWalletAddressForName("Athi01");
		System.out.println("\nSender is Athi01 = " + senderAddress + '\n');
				
		getToAddresses();
		
		/*BitcoinRPCAccess ba = new BitcoinRPCAccess("rpc", "rpc", 31416, null, "");
		double balance = ba.getBalance("");
		System.out.print("Account balance = " + balance);*/
		//ba.sendToAddress(address, amount);
	}

	private static void getToAddresses() {
		Random random = new Random();
		int randomNumber = random.nextInt(getConfig().getTransactionToOutputMax() + 1 - getConfig().getTransactionToOutputMin()) 
				+ getConfig().getTransactionToOutputMin();
		System.out.println("Number of Receivers = " + randomNumber + '\n');
		
		for (int i = 0; i < randomNumber; i++) {
			getToAddress();	
			getTransactionAmount();
		}			
	}
	
	private static void getTransactionAmount() {
		Random random = new Random();
		int randomNumber = random.nextInt(getConfig().getTransactionAmountMax() + 1 - getConfig().getTransactionAmountMin()) 
				+ getConfig().getTransactionAmountMin();
		System.out.println("transaction amount = " + randomNumber + '\n');
	}

	private static String getToAddress() {
		Random g2 = new Random();
		int fromListSize = g2.nextInt(getWallet().getToAddrList().size());
		String toAddress = null;
		String toName = null;
		for (int i2 = 0; i2 < fromListSize; i2++) {
			toAddress = ((WalletAddress) getWallet().getToAddrList().get(fromListSize)).getAddress();
			toName = ((WalletAddress) getWallet().getToAddrList().get(fromListSize)).getName();
		}
		System.out.print("Receiver = " + toName + " - " + toAddress + ", ");
		return toAddress;
	}

	private static void setupLoadTestConfig(Logger logger) {
		try {
			File waFile = new File("src/test/resources/loadTestConfig.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(waFile);
			doc.getDocumentElement().normalize();
						
			NodeList nodeList = doc.getElementsByTagName("*");
			for(int i = 0; i < nodeList.getLength(); i++) {
			    Node node = nodeList.item(i);
			    
			    String name = null, type = null, addr = null;
                Attr typeAttr = (Attr) node.getAttributes().getNamedItem("type");
                if (typeAttr != null) {
                    type= typeAttr.getValue(); 
                    //System.out.println("type: " + type);                      
                }
                Attr nameAttr = (Attr) node.getAttributes().getNamedItem("name");
                if (nameAttr != null) {
                    name= nameAttr.getValue();                      
                    //System.out.println("name: " + name);                      
                }
                if (typeAttr != null && nameAttr != null) {
                	addr = node.getTextContent();
                	//System.out.println("address: " + addr);
                	            	
                	if (type.equalsIgnoreCase("from"))
                		getWallet().addToFromList(new WalletAddress(type, name, addr));
                	else
                		getWallet().add2ToList(new WalletAddress(type, name, addr));
                }
                //System.out.println(node.getNodeName());
                if (node.getNodeName().equalsIgnoreCase("transaction")) {
                	NodeList transactionChildren = node.getChildNodes();
                	int amountMin = 0, amountMax =0, fromInputMin = 0, fromInputMax = 0, toOutputMin = 0, toOutputMax = 0;
                    for ( int j = 0; j < transactionChildren.getLength(); j++ ) {
                        Node elem = transactionChildren.item(j);
                        //System.out.println("transaction : " + elem.getNodeName());
                        //System.out.println("value : " +elem.getTextContent());
                        switch (elem.getNodeName()) {
                        case "amountMin":
                        	amountMin = Integer.parseInt(elem.getTextContent());
                            break;
                        case "amountMax":
                        	amountMax = Integer.parseInt(elem.getTextContent());
                            break;
                        case "fromInputMin":
                        	fromInputMin = Integer.parseInt(elem.getTextContent());
                            break;
                        case "fromInputMax":
                        	fromInputMax = Integer.parseInt(elem.getTextContent());
                            break;
                        case "toOutputMin":
                        	toOutputMin = Integer.parseInt(elem.getTextContent());
                            break;
                        case "toOutputMax":
                        	toOutputMax = Integer.parseInt(elem.getTextContent());
                            break;                            
                        }                        
                    }
                    setConfig(new LoadTestConfig(amountMin, amountMax, fromInputMin, fromInputMax, toOutputMin, toOutputMax));
                }
			}
			System.out.println("Wallet Addresses to be used in test\n");
			System.out.println(getWallet());
			//System.out.println("Load Test Config");
			System.out.println(getConfig());
			/*System.out.println("Random transaction outputs");
			for (int i = 0; i < 10; i++) {
				System.out.println(getRandomNumberInRange(getConfig().getTransactionToOutputMin(), 
														  getConfig().getTransactionToOutputMax()));
			}*/
		}
		catch (Exception e) {
			logger.info ("Exception occurred - ");
			e.printStackTrace();
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		Random r = new Random();
		return r.ints(min, (max + 1)).findFirst().getAsInt();
	}
}
