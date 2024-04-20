// Represent stock date at a given date.
public class StockData {
	public double open; // The opening price of the stock for that day
	public double close; // The closing price of the stock for that day
	public double high; // The highest price the stock reached during that day
	public double low; // The lowest price the stock reached during that day
	public long volume; // The number of shares that were traded during that day

	public StockData(double open, double close, double high, double low, long volume) {
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}
}