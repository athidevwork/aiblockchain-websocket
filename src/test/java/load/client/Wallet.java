package load.client;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
	private List<WalletAddress> toAddrList = new ArrayList<WalletAddress>();
	private List<WalletAddress> fromAddrList = new ArrayList<WalletAddress>();
	
	public List<WalletAddress> getToAddrList() {
		return toAddrList;
	}

	public void setToAddrList(List<WalletAddress> toAddrList) {
		this.toAddrList = toAddrList;
	}

	public List<WalletAddress> getFromAddrList() {
		return fromAddrList;
	}

	public String getFromWalletAddressForName(String name) {
		for (WalletAddress walletAddress : getFromAddrList()) {
			if (walletAddress.getName().equalsIgnoreCase(name))
				return walletAddress.getAddress();
		}
		return null;
	}
	
	public void setFromAddrList(List<WalletAddress> fromAddrList) {
		this.fromAddrList = fromAddrList;
	}

	public void add2ToList(WalletAddress address) {
		getToAddrList().add(address);
	}
	
	public void addToFromList(WalletAddress address) {
		getFromAddrList().add(address);
	}

	@Override
	public String toString() {
		return "[To Address=\n" + toAddrList + "\n, From Address=\n" + fromAddrList + "]\n";
	}
}
