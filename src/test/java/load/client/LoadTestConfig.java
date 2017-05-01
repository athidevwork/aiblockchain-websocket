package load.client;

public class LoadTestConfig {
	private int transactionAmountMin;
	private int transactionAmountMax;
	private int transactionFromOutputMin;
	private int transactionFromOutputMax;	
	private int transactionToOutputMin;
	private int transactionToOutputMax;
	public int getTransactionAmountMin() {
		return transactionAmountMin;
	}
	public void setTransactionAmountMin(int transactionAmountMin) {
		this.transactionAmountMin = transactionAmountMin;
	}
	public int getTransactionAmountMax() {
		return transactionAmountMax;
	}
	public void setTransactionAmountMax(int transactionAmountMax) {
		this.transactionAmountMax = transactionAmountMax;
	}
	public int getTransactionFromOutputMin() {
		return transactionFromOutputMin;
	}
	public void setTransactionFromOutputMin(int transactionFromOutputMin) {
		this.transactionFromOutputMin = transactionFromOutputMin;
	}
	public int getTransactionFromOutputMax() {
		return transactionFromOutputMax;
	}
	public void setTransactionFromOutputMax(int transactionFromOutputMax) {
		this.transactionFromOutputMax = transactionFromOutputMax;
	}
	public int getTransactionToOutputMin() {
		return transactionToOutputMin;
	}
	public void setTransactionToOutputMin(int transactionToOutputMin) {
		this.transactionToOutputMin = transactionToOutputMin;
	}
	public int getTransactionToOutputMax() {
		return transactionToOutputMax;
	}
	public void setTransactionToOutputMax(int transactionToOutputMax) {
		this.transactionToOutputMax = transactionToOutputMax;
	}
	public LoadTestConfig(){}
	public LoadTestConfig(int transactionAmountMin, int transactionAmountMax, int transactionFromOutputMin,
			int transactionFromOutputMax, int transactionToOutputMin, int transactionToOutputMax) {
		super();
		this.transactionAmountMin = transactionAmountMin;
		this.transactionAmountMax = transactionAmountMax;
		this.transactionFromOutputMin = transactionFromOutputMin;
		this.transactionFromOutputMax = transactionFromOutputMax;
		this.transactionToOutputMin = transactionToOutputMin;
		this.transactionToOutputMax = transactionToOutputMax;
	}
	@Override
	public String toString() {
		return "LoadTestConfig \n[transactionAmountMin=" + transactionAmountMin + ", transactionAmountMax="
				+ transactionAmountMax + ", transactionFromOutputMin=" + transactionFromOutputMin
				+ ", transactionFromOutputMax=" + transactionFromOutputMax + ", transactionToOutputMin="
				+ transactionToOutputMin + ", transactionToOutputMax=" + transactionToOutputMax + "]";
	}
}
