import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockAnalyzerCLI {
	public static void main(String[] args) {
		// Sorting DLLCompImp
		DLLComp<Integer> l = new DLLCompImp<Integer>();
		l.insert(5);
		l.insert(3);
		l.insert(1);
		l.insert(4);
		l.insert(1);
		System.out.println("Original list:");
		print(l);
		l.sort(true);
		System.out.println("List sorted in increasing order:");
		print(l);
		l.sort(false);
		System.out.println("List sorted in decreasing order:");
		print(l);

		// Date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// Numeric time series
		NumericTimeSeries ts = new NumericTimeSeriesImp();
		Date date;
		try {
			date = dateFormat.parse("2024-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		ts.addDataPoint(new DataPoint<Double>(date, 10.0));
		try {
			date = dateFormat.parse("2024-01-02");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		ts.addDataPoint(new DataPoint<Double>(date, 12.0));
		try {
			date = dateFormat.parse("2024-01-03");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		ts.addDataPoint(new DataPoint<Double>(date, 11.0));
		try {
			date = dateFormat.parse("2024-01-04");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		ts.addDataPoint(new DataPoint<Double>(date, 13.0));
		try {
			date = dateFormat.parse("2024-01-05");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		ts.addDataPoint(new DataPoint<Double>(date, 14.0));

		System.out.println("Time series:");
		print(ts.getAllDataPoints());
		NumericTimeSeries ma = ts.calculateMovingAverage(3);
		System.out.println("Moving average with period 3:");
		print(ma.getAllDataPoints());

		// Initialize StockDataLoader
		StockDataLoader loader = new StockDataLoaderImp();
		// Load stock data for Google
		StockHistory googleStockHistory = loader.loadStockDataFile("data/real/GOOGL.csv");

		// Choose a start date and an end date
		Date startDate;
		Date endDate;
		try {
			startDate = dateFormat.parse("2004-08-23");
			endDate = dateFormat.parse("2023-09-21");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		// Access Google stock data at startDate and endDate
		StockData sd;
		sd = googleStockHistory.getStockData(startDate);
		System.out.println("Google's opening price on " + dateFormat.format(startDate) + ": " + sd.open);
		System.out.println("Google's closing price on " + dateFormat.format(startDate) + ": " + sd.close);
		System.out.println("Google's traded volume on " + dateFormat.format(startDate) + ": " + sd.volume);
		sd = googleStockHistory.getStockData(endDate);
		System.out.println("Google's opening price on " + dateFormat.format(endDate) + ": " + sd.open);
		System.out.println("Google's closing price on " + dateFormat.format(endDate) + ": " + sd.close);
		System.out.println("Google's traded volume on " + dateFormat.format(endDate) + ": " + sd.volume);

		// Load all files from the directory data
		StockHistoryDataSet dataSet = loader.loadStockDataDir("data/real");
		// Initialize StockDataSetAnalyzer
		StockDataSetAnalyzer analyzer = new StockDataSetAnalyzerImp();
		analyzer.setStockHistoryDataSet(dataSet);
		try {
			startDate = dateFormat.parse("2023-01-03");
			endDate = dateFormat.parse("2023-09-21");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		// Analyze the stock data
		System.out.println("Stocks sorted by performance between " + dateFormat.format(startDate) + " and "
				+ dateFormat.format(endDate) + ":");
		DLLComp<CompPair<String, Double>> performances = analyzer.getSortedByPerformance(startDate, endDate);
		print(performances);

		System.out.println("Stocks sorted by traded volume between " + dateFormat.format(startDate) + " and "
				+ dateFormat.format(endDate) + ":");
		DLLComp<CompPair<String, Long>> volumes = analyzer.getSortedByVolume(startDate, endDate);
		print(volumes);

		System.out.println("Stocks sorted by MSDPI between " + dateFormat.format(startDate) + " and "
				+ dateFormat.format(endDate) + ":");
		DLLComp<CompPair<Pair<String, Date>, Double>> msdpis = analyzer.getSortedByMSDPI(startDate, endDate);
		print(msdpis);
	}

	public static <T> void print(DLL<T> l) {
		System.out.println("-------------------");
		if (l == null) {
			System.out.println("null");
		} else {
			if (l.empty()) {
				System.out.println("empty");
			} else {
				l.findFirst();
				while (!l.last()) {
					System.out.println(l.retrieve());
					l.findNext();
				}
				System.out.println(l.retrieve());
			}
		}
		System.out.println("-------------------");
	}
}
