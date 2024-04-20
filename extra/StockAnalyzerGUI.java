import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

enum SortMethod {
	Alpha, Performance, Volume, MSDPI;
}

public class StockAnalyzerGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private StockDataLoader loader;
	private StockHistoryDataSet stockHistoryDataSet;
	private StockHistory stockHistory;
	private JList<String> stockList;
	private JPanel chartPanel;
	private Date startDate;
	private Date endDate;
	private int period = 0;
	private SortMethod sortMethod = SortMethod.Alpha;
	private boolean alphaIncreasing = true;
	private boolean performanceIncreasing = true;
	private boolean volumeIncreasing = true;
	private boolean msdpiIncreasing = true;
	private boolean increasing = true;

	private String[] toArray(DLL<String> l) {
		if (l == null) {
			return null;
		}
		String[] array = new String[l.size()];
		if (l.empty()) {
			return array;
		}
		int i = 0;
		l.findFirst();
		while (true) {
			array[i] = l.retrieve();
			if (l.last()) {
				break;
			}
			l.findNext();
			i++;
		}
		return array;
	}

	private void sortStockList() {
		if (stockHistoryDataSet != null && !stockHistoryDataSet.empty()) {
			DLLComp<String> l = new DLLCompImp<String>();
			switch (sortMethod) {
			case Alpha: {
				l = stockHistoryDataSet.getAllCompanyCodes();
				l.sort(increasing);
				break;
			}

			case Performance: {
				StockDataSetAnalyzer analyzer = new StockDataSetAnalyzerImp();
				analyzer.setStockHistoryDataSet(stockHistoryDataSet);
				DLLComp<CompPair<String, Double>> results = analyzer.getSortedByPerformance(startDate, endDate);
				if (results != null && !results.empty()) {
					results.sort(increasing);
					results.findFirst();
					while (true) {
						String companyCode = results.retrieve().first;
						double perf = results.retrieve().second;
						l.insert(companyCode + "," + String.format("%.4f", perf));
						if (results.last()) {
							break;
						}
						results.findNext();
					}
				}
				break;
			}

			case Volume: {
				StockDataSetAnalyzer analyzer = new StockDataSetAnalyzerImp();
				analyzer.setStockHistoryDataSet(stockHistoryDataSet);
				DLLComp<CompPair<String, Long>> results = analyzer.getSortedByVolume(startDate, endDate);
				if (results != null && !results.empty()) {
					results.sort(increasing);
					results.findFirst();
					while (true) {
						l.insert(results.retrieve().toString());
						if (results.last()) {
							break;
						}
						results.findNext();
					}
				}
				break;
			}

			case MSDPI: {
				StockDataSetAnalyzer analyzer = new StockDataSetAnalyzerImp();
				analyzer.setStockHistoryDataSet(stockHistoryDataSet);
				DLLComp<CompPair<Pair<String, Date>, Double>> results = analyzer.getSortedByMSDPI(startDate, endDate);
				if (results != null && !results.empty()) {
					results.sort(increasing);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					results.findFirst();
					while (true) {
						String companyCode = results.retrieve().first.first;
						Date date = results.retrieve().first.second;
						double msdpi = results.retrieve().second;
						l.insert(companyCode + "," + dateFormat.format(date) + "," + String.format("%.4f", msdpi));
						if (results.last()) {
							break;
						}
						results.findNext();
					}
				}
				break;
			}
			default: {
				System.err.println("Unknown sort method");
			}
			}
			if (l == null || l.empty()) {
				String listData[] = { "No data on selected dates" };
				stockList.setListData(listData);
			} else {
				stockList.setListData(toArray(l));
			}
			revalidate();
			repaint();
		}
	}

	public StockAnalyzerGUI() {
		setTitle("Stock Analyzer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);

		// Stock list
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		JLabel stockListLabel = new JLabel("Stock List");
		listPanel.add(stockListLabel);
		stockList = new JList<String>();
		stockList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String selectedValue = stockList.getSelectedValue();
					String[] parts = selectedValue.split(",");
					if (parts != null && parts.length > 0) {
						String companyCode = parts[0];
						stockHistory = stockHistoryDataSet.getStockHistory(companyCode);
						plotStockData();
					}
				}
			}
		});
		listPanel.add(stockList);
		JScrollPane scrollPane = new JScrollPane(stockList);
		listPanel.add(scrollPane);

		// Buttons
		JPanel buttonPanel = new JPanel();

		// Load button
		JButton stockLoadButton = new JButton("Load Stock");
		stockLoadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loader == null) {
					loader = new StockDataLoaderImp();
				}

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showOpenDialog(StockAnalyzerGUI.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					stockHistoryDataSet = loader.loadStockDataDir(selectedDirectory.getAbsolutePath());
					sortMethod = SortMethod.Alpha;
					sortStockList();
					increasing = alphaIncreasing = true;
				}
			}
		});
		buttonPanel.add(stockLoadButton);

		// Date selectors
		JLabel startDateLabel = new JLabel("Start: ");
		buttonPanel.add(startDateLabel);
		JSpinner startDateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy/MM/dd");
		startDateSpinner.setEditor(startDateEditor);
		startDateSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				startDate = (Date) s.getValue();
				sortStockList();
				plotStockData();
			}
		});
		buttonPanel.add(startDateSpinner);

		JLabel endDateLabel = new JLabel("End: ");
		buttonPanel.add(endDateLabel);
		JSpinner endDateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy/MM/dd");
		endDateSpinner.setEditor(endDateEditor);
		endDateSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				endDate = (Date) s.getValue();
				sortStockList();
				plotStockData();
			}
		});
		buttonPanel.add(endDateSpinner);

		// Sort by code button
		JButton sortAlphaButton = new JButton("Sort Alphabetically");
		sortAlphaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortMethod = SortMethod.Alpha;
				increasing = alphaIncreasing;
				sortStockList();
				alphaIncreasing = !alphaIncreasing;
			}
		});
		buttonPanel.add(sortAlphaButton);

		// Sort by performance
		JButton sortByPerformanceButton = new JButton("Sort by Performance");
		sortByPerformanceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortMethod = SortMethod.Performance;
				increasing = performanceIncreasing;
				sortStockList();
				performanceIncreasing = !performanceIncreasing;
			}
		});
		buttonPanel.add(sortByPerformanceButton);

		// Sort by Volume
		JButton sortByVolumeButton = new JButton("Sort by Volume");
		sortByVolumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortMethod = SortMethod.Volume;
				increasing = volumeIncreasing;
				sortStockList();
				volumeIncreasing = !volumeIncreasing;
			}
		});
		buttonPanel.add(sortByVolumeButton);

		// Sort by MSDPI
		JButton sortByMSDPIButton = new JButton("Sort by MSDPI");
		sortByMSDPIButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sortMethod = SortMethod.MSDPI;
				increasing = msdpiIncreasing;
				sortStockList();
				msdpiIncreasing = !msdpiIncreasing;
			}
		});
		buttonPanel.add(sortByMSDPIButton);

		// Moving average
		JLabel maPeriodLabel = new JLabel("Moving Average Period:");
		buttonPanel.add(maPeriodLabel);
		JSpinner maPeriodSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		maPeriodSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				period = (int) s.getValue();
				plotStockData();
			}
		});
		buttonPanel.add(maPeriodSpinner);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(listPanel, BorderLayout.WEST);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private void plotStockData() {
		if (stockHistory != null) {
			DLL<DataPoint<StockData>> dataPoints = stockHistory.getTimeSeries().getDataPointsInRange(startDate,
					endDate);
			org.jfree.data.time.TimeSeriesCollection dataset = new org.jfree.data.time.TimeSeriesCollection();
			org.jfree.data.time.TimeSeries closingPrices = new org.jfree.data.time.TimeSeries("Closing Prices");
			if (!dataPoints.empty()) {
				dataPoints.findFirst();
				while (!dataPoints.last()) {
					DataPoint<StockData> dp = dataPoints.retrieve();
					closingPrices.addOrUpdate(new Day(dp.date), dp.value.close);
					dataPoints.findNext();
				}
				DataPoint<StockData> dp = dataPoints.retrieve();
				closingPrices.addOrUpdate(new Day(dp.date), dp.value.close);
			}
			dataset.addSeries(closingPrices);

			if (period != 0) {
				org.jfree.data.time.TimeSeries ma = new org.jfree.data.time.TimeSeries("Moving Average");
				NumericTimeSeries cp = new NumericTimeSeriesImp();
				DLL<DataPoint<StockData>> allDataPoints = stockHistory.getTimeSeries().getAllDataPoints();
				if (!allDataPoints.empty()) {
					allDataPoints.findFirst();
					while (!allDataPoints.last()) {
						DataPoint<StockData> dp = allDataPoints.retrieve();
						DataPoint<Double> ndp = new DataPoint<Double>(dp.date, dp.value.close);
						cp.addDataPoint(ndp);
						allDataPoints.findNext();
					}
					DataPoint<StockData> dp = allDataPoints.retrieve();
					DataPoint<Double> ndp = new DataPoint<Double>(dp.date, dp.value.close);
					cp.addDataPoint(ndp);
				}

				NumericTimeSeries macpts = cp.calculateMovingAverage(period);
				DLL<DataPoint<Double>> macp = macpts.getDataPointsInRange(startDate, endDate);
				if (!macp.empty()) {
					macp.findFirst();
					while (!macp.last()) {
						DataPoint<Double> dp = macp.retrieve();
						ma.addOrUpdate(new Day(dp.date), dp.value);
						macp.findNext();
					}
					DataPoint<Double> dp = macp.retrieve();
					ma.addOrUpdate(new Day(dp.date), dp.value);
				}
				dataset.addSeries(ma);
			}

			JFreeChart chart = ChartFactory.createTimeSeriesChart(
					stockHistory.getCompanyCode() + " Stock Closing Prices", "Date", "Closing Price", dataset, true,
					true, false);
			if (chartPanel != null) {
				getContentPane().remove(chartPanel);
			}
			chartPanel = new ChartPanel(chart);
			getContentPane().add(chartPanel, BorderLayout.CENTER);
			revalidate();
			repaint();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StockAnalyzerGUI gui = new StockAnalyzerGUI();
				gui.setVisible(true);
			}
		});
	}
}
