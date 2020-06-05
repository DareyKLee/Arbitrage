import java.util.List;

public class Trade {

	private List<CurrencyNode> tradePath;
	private double revenue;
	private double profit;
	
	public Trade(List<CurrencyNode> tradePath, double revenue, double profit) {
		this.tradePath = tradePath;
		this.revenue = revenue;
		this.profit = profit;
	}
	
	public List<CurrencyNode> getTradePath() {
		return tradePath;
	}
	
	public double getRevenue() {
		return revenue;
	}
	
	public double getProfit() {
		return profit;
	}
}
