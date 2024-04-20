// Time series of numeric (double) values.
public interface NumericTimeSeries extends TimeSeries<Double> {
	// Calculates and returns the moving average of the data points over a specified
	// period. The value period must be strictly positive. If the time series is
	// empty, the output must be empty.
	NumericTimeSeries calculateMovingAverage(int period);

	// Returns the maximum value in the time series. Time series must not be empty.
	DataPoint<Double> getMax();

	// Returns the minimum value in the time series. Time series must not be empty.
	DataPoint<Double> getMin();
}