/*
 * Darey Lee
 * CS 4050
 * Assignment 5
 */

public class Main {

	static String startingCurrency = "Dollar";
	static double investmentAmount = 1000;
	
	public static void main(String[] args) {
		CurrencyGraph currencyGraph = new CurrencyGraph();
		ProfitFinder profitFinder = new ProfitFinder(currencyGraph);
		
		profitFinder.findProfitableTrades(startingCurrency, investmentAmount);
	}
	
}
