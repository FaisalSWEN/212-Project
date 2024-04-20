import java.util.Date;

//Interface for analyzing stock dataset.
public interface StockDataSetAnalyzer {

	// Returns dataset.
	StockHistoryDataSet getStockHistoryDataSet();

	// Sets dataset.
	void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet);

	// Returns the list of company codes sorted according to their stock performance
	// between startDate and endDate. It returns an empty list if either dates is
	// null.
	DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate);

	// Returns the list of company codes sorted according to their total volume
	// between startDate and endDate inclusive. If startDate is null, fetches from
	// the earliest date. If endDate is null, fetches to the latest
	// date.
	DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate);

	// Returns the list of company codes sorted by the maximum single day price
	// increase between startDate and endDate inclusive. If startDate is null,
	// fetches from the earliest date. If endDate is null, fetches to the latest
	// date.
	DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate);
}
