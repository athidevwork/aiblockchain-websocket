package load.client;

public class WalletAddress {
	private String type;
	private String name;
	private String address;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public WalletAddress(String type, String name, String address) {
		super();
		this.type = type;
		this.name = name;
		this.address = address;
	}
	@Override
	public String toString() {
		return "[name=" + name + ", address=" + address + "]\n";
	}	
}
