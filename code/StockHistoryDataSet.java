// Interface representing stock history of several companies.
public interface StockHistoryDataSet {
	// Returns the number of companies for which data is stored.
	int size();

	// Returns true if there are no records, false otherwise.
	boolean empty();

	// Clears all data from the storage.
	void clear();

	// Returns the map of stock histories, where the key is the company code.
	Map<String, StockHistory> getStockHistoryMap();

	// Returns the list of all company codes stored in the dataset sorted in
	// increasing order.
	DLLComp<String> getAllCompanyCodes();

	// Retrieves the stock history for a specific company code. This method returns
	// null if no data is found.
	StockHistory getStockHistory(String companyCode);

	// Adds the stock history of a specific company. This method returns true if the
	// operation is successful, false otherwise (company code already exists).
	boolean addStockHistory(StockHistory StockHistory);

	// Removes the stock history of a specific company from the storage. This method
	// returns true if the operation is successful and false if the company code
	// does not exist.
	boolean removeStockHistory(String companyCode);
}
