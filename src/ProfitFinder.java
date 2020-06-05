import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfitFinder {

	private CurrencyGraph currencyGraph;
	private CurrencyNode startingCurrencyNode;
	private double startingInvestmentAmount;
	private List<Trade> trades = new ArrayList<Trade>();
		
	public ProfitFinder(CurrencyGraph currencyGraph) {
		this.currencyGraph = currencyGraph;
	}
	
	public void findProfitableTrades(String currencyName, double startingInvestmentAmount) {
		startingCurrencyNode = currencyGraph.getCurrencyNodes().get(currencyName);
		this.startingInvestmentAmount = startingInvestmentAmount;
		
		List<CurrencyNode> visitedCurrencyNodes = new ArrayList<CurrencyNode>();
		visitedCurrencyNodes.add(startingCurrencyNode);
				
		for (CurrencyEdge currencyEdge : startingCurrencyNode.getCurrencyEdges()) {			
			currencyNodeTraversal(currencyEdge, visitedCurrencyNodes, startingInvestmentAmount);
		}
		
		sortTradesByProfit(0, trades.size() - 1);
		displayTopTenTrades();
		writeTradesToFile();
	}

	private void currencyNodeTraversal(CurrencyEdge currencyEdge,
			List<CurrencyNode> previouslyVisitedCurrencyNodes,
			double investmentAmount) {
		CurrencyNode currentNode = currencyEdge.getCurrencyNode();
				
		List<CurrencyNode> visitedCurrencyNodes = new ArrayList<CurrencyNode>();
		visitedCurrencyNodes.addAll(previouslyVisitedCurrencyNodes);
		visitedCurrencyNodes.add(currentNode);
		
		investmentAmount = investmentAmount * currencyEdge.getExchangeRate();
		
		calculateTrade(visitedCurrencyNodes, currentNode, investmentAmount);
				
		if (visitedCurrencyNodes.size() != currencyGraph.getNumberOfCurrencies()) {
			for (CurrencyEdge nextCurrencyEdge : currentNode.getCurrencyEdges()) {
				if (!visitedCurrencyNodes.contains(nextCurrencyEdge.getCurrencyNode())) {
					currencyNodeTraversal(nextCurrencyEdge, visitedCurrencyNodes, investmentAmount);
				}
			}
		}
	}
	
	private void calculateTrade(List<CurrencyNode> visitedCurrencyNodes,
			CurrencyNode currentNode,
			double investmentAmount) {
		CurrencyEdge currencyEdgeToStartingNode = currentNode.getSpecificCurrencyEdgeTo(startingCurrencyNode);
				
		List<CurrencyNode> tradePath = new ArrayList<CurrencyNode>();
		tradePath.addAll(visitedCurrencyNodes);
		tradePath.add(startingCurrencyNode);
		
		double revenue = investmentAmount * currencyEdgeToStartingNode.getExchangeRate();
		double profit = revenue - startingInvestmentAmount;
		
		trades.add(new Trade(tradePath, revenue, profit));
		
	}
	
	private void sortTradesByProfit(int low, int high) {
		if (low < high) {
			
			double pivot = trades.get(low).getProfit();
			int i = low - 1;
			int j = high + 1;
			
			while (true) {
				do {
					i++;
				} while (trades.get(i).getProfit() > pivot);
				
				do {
					j--;
				} while (trades.get(j).getProfit() < pivot);
				
				if (i < j) {
					Collections.swap(trades, i, j);
				} else {
					break;
				}
			}
			
			sortTradesByProfit(low, j);
			sortTradesByProfit(j + 1, high);
		}
	}
	
	private void displayTopTenTrades() {
		System.out.println("STARTING CURRENCY: " + startingCurrencyNode.getCurrencyName());
		System.out.println("INVESTMENT AMOUNT: " + String.format("%.2f", startingInvestmentAmount) + "\n");
		System.out.println("TOP TEN TRADES LISTED IN ORDER BELOW");
		System.out.println("================================================================================\n");

		for (int i = 0; i < 10; i++) {
			System.out.println("PROFIT: " + String.format("%.2f", trades.get(i).getProfit()));
			
			for (CurrencyNode currencyNode : trades.get(i).getTradePath()) {
				System.out.printf(currencyNode.getCurrencyName() + " -> ");
			}
			
			System.out.print("REVENUE: " + String.format("%.2f", trades.get(i).getRevenue()) + "\n\n");
			System.out.println("================================================================================\n");
		}
	}
	
	private void writeTradesToFile() {
		try {
			FileWriter tradesFileWriter = new FileWriter("trades_and_revenue.txt");
			
			for (Trade trade : trades) {
				for (CurrencyNode currencyNode : trade.getTradePath()) {
					tradesFileWriter.write(currencyNode.getCurrencyName() + " - > ");
				}
				
				tradesFileWriter.write(String.format("%.2f", trade.getRevenue()) + "\n\n");
			}
			
			tradesFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
