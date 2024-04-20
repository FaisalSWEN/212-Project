public interface StockDataLoader {

	// Loads and adds stock history from the specified CSV file. The code of the
	// company is the basename of the file. This method returns null if the
	// operation is not successful. Errors include, non-existing file, incorrect
	// format, repeated dates, dates not sorted in increasing order, etc.
	StockHistory loadStockDataFile(String fileName);

	// Loads and returns stock history data from all CSV files in the specified
	// directory. This method returns null if the operation is not successful (see
	// possible errors in the method loadStockDataFile).
	StockHistoryDataSet loadStockDataDir(String directoryPath);
}