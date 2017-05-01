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
