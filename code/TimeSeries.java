import java.util.Date;

// Interface representing a time series of data points.
public interface TimeSeries<T> {

	// Returns the number of elements in the time series.
	int size();

	// Returns true if the time series is empty, false otherwise.
	boolean empty();

	// Retrieves the data corresponding to a specific date. This method returns the
	// data point for the specified date, or null if no such data point exists.
	T getDataPoint(Date date);

	// Return all dates in increasing order.
	DLL<Date> getAllDates();

	// Returns min date. Time series must not be empty.
	Date getMinDate();

	// Returns max date. Time series must not be empty.
	Date getMaxDate();

	// Adds a new data point to the time series. If successful, the method returns
	// true. If date already exists, the method returns false.
	boolean addDataPoint(DataPoint<T> dataPoint);

	// Updates a data point. This method returns true if the date exists and the
	// update was successful, false otherwise.
	boolean updateDataPoint(DataPoint<T> dataPoint);

	// Removes a data point with given date from the time series. This method
	// returns true if the data point was successfully removed, false otherwise.
	boolean removeDataPoint(Date date);

	// Retrieves all data points in the time series as a DLL that is sorted in
	// increasing order of date.
	DLL<DataPoint<T>> getAllDataPoints();

	// Gets data points from startDate to endDate inclusive. If startDate is null,
	// fetches from the earliest date. If endDate is null, fetches to the latest
	// date. Returns sorted list in increasing date order.
	DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate);
}
