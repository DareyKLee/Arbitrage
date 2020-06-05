import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyGraph {

	private Map<String, CurrencyNode> currencyNodes = new HashMap<String, CurrencyNode>();
	private int numberOfCurrencies;
	
	public CurrencyGraph() {
		createCurrencyGraphFromInputFile();
	}
	
	public Map<String, CurrencyNode> getCurrencyNodes() {
		return currencyNodes;
	}
	
	public int getNumberOfCurrencies() {
		return numberOfCurrencies;
	}
	
	private void createCurrencyGraphFromInputFile() {
		try {
			File exchangeRatesFile = new File("exchange_rates.txt");
			Scanner lineReader = new Scanner(exchangeRatesFile);
			
			//LOCATES THE FIRST LINE WITH A STAND ALONE INTEGER WHICH IS WHERE THE DATA BEGINS
			while (lineReader.hasNextLine()) {
				try {
					numberOfCurrencies = Integer.parseInt(lineReader.nextLine());
					break;
				} catch (NumberFormatException e) {
					continue;
				}
			}
			
			System.out.println("EXAMINING " + numberOfCurrencies + " CURRENCIES\n");
			
			while (lineReader.hasNextLine()) {
				String data = lineReader.nextLine();
				String[] dataArray = data.split("\\s+");
				
				CurrencyNode currencyNode_1;
				CurrencyNode currencyNode_2;
				
				String currencyName_1 = dataArray[0];
				String currencyName_2 = dataArray[1];
				
				double exchangeRateFrom_1_2 = Double.parseDouble(dataArray[2]);
				double exchangeRateFrom_2_1 = Math.pow(exchangeRateFrom_1_2, -1);
				
				if (!currencyNodes.containsKey(currencyName_1)) {
					currencyNode_1 = new CurrencyNode(currencyName_1);
					currencyNodes.put(currencyName_1, currencyNode_1);
				}
				
				if (!currencyNodes.containsKey(currencyName_2)) {
					currencyNode_2 = new CurrencyNode(currencyName_2);
					currencyNodes.put(currencyName_2, currencyNode_2);
				}
				
				currencyNodes.get(currencyName_1).addCurrencyEdge(currencyNodes.get(currencyName_2), exchangeRateFrom_1_2);
				currencyNodes.get(currencyName_2).addCurrencyEdge(currencyNodes.get(currencyName_1), exchangeRateFrom_2_1);
			}
			
			lineReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
