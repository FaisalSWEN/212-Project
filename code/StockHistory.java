import java.util.Date;

// Interface representing the stock history of a given company.
public interface StockHistory {

	// Returns the number of elements in the history.
	int size();

	// Returns true if the history is empty, false otherwise.
	boolean empty();

	// Clears all data from the storage.
	void clear();

	// Returns company code.
	String getCompanyCode();

	// Sets company code
	void SetCompanyCode(String companyCode);

	// Returns stock history as a time series.
	TimeSeries<StockData> getTimeSeries();

	// Retrieves StockData for a specific date, or null if no data is found.
	StockData getStockData(Date date);

	// Adds a new StockData and returns true if the operation is successful, false
	// otherwise.
	boolean addStockData(Date date, StockData stockData);

	// Remove the StockData of a given date, and returns true if the operation is
	// successful, false otherwise.
	boolean removeStockData(Date date);
}
