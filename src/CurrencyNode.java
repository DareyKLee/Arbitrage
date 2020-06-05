import java.util.HashSet;
import java.util.Set;

public class CurrencyNode {

	private String currencyName;
	private Set<CurrencyEdge> currencyEdges = new HashSet<CurrencyEdge>();
	
	public CurrencyNode(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public void addCurrencyEdge(CurrencyNode currencyNode, double exchangeRate) {
		CurrencyEdge currencyEdge = new CurrencyEdge(currencyNode, exchangeRate);
		
		currencyEdges.add(currencyEdge);
	}
	
	public Set<CurrencyEdge> getCurrencyEdges() {
		return currencyEdges;
	}
	
	public CurrencyEdge getSpecificCurrencyEdgeTo(CurrencyNode targetCurrencyNode) {
		for (CurrencyEdge currencyEdge : currencyEdges) {			
			if (currencyEdge.getCurrencyNode().getCurrencyName().equals(targetCurrencyNode.getCurrencyName())) {
				return currencyEdge;
			}
		}
				
		return null;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
}
