
public class CurrencyEdge {

	private CurrencyNode currencyNode;
	private double exchangeRate;
	
	public CurrencyEdge(CurrencyNode currencyNode, double exchangeRate) {
		this.currencyNode = currencyNode;
		this.exchangeRate = exchangeRate;
	}
	
	public double getExchangeRate() {
		return exchangeRate;
	}
	
	public CurrencyNode getCurrencyNode() {
		return currencyNode;
	}
}
