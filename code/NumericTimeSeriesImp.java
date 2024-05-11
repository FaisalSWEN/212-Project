public class NumericTimeSeriesImp extends TimeSeriesImp<Double> implements NumericTimeSeries  {
	@Override
	public NumericTimeSeries calculateMovingAverage(int period) {
	    if (period <= 0)
	        throw new IllegalArgumentException("Period must be positive");

	    NumericTimeSeries series = new NumericTimeSeriesImp();
	    DLL<DataPoint<Double>> allDataPoints = getAllDataPoints();
	    DataPoint<Double>[] dataPoints = new DataPoint[allDataPoints.size()];

	    allDataPoints.findFirst();
	    for (int i = 0; i < dataPoints.length; i++) {
	        dataPoints[i] = allDataPoints.retrieve(); // Transferring Elements from DLL to an array
	        allDataPoints.remove();                   // deleting elements from DLL to save memory
	    }

	    double sum = 0;
	    for (int i = 0; i < dataPoints.length; i++) {
	        sum += dataPoints[i].value;
	        if (i >= period - 1) {
	            if (i >= period)
	                sum -= dataPoints[i - period].value;

	            series.addDataPoint(new DataPoint<>(dataPoints[i].date, sum / period));
	        }
	    }

	    return series;
	}

	@Override
	public DataPoint < Double > getMax () {
		
		DLL<DataPoint<Double>> d = getAllDataPoints(); 
		DataPoint<Double> max = d.retrieve();
		d.findFirst();
		while(!d.last()) {
			if(d.retrieve().value > max.value) 
				max = d.retrieve();
			d.findNext();
		}
		if(d.retrieve().value> max.value) 
			max = d.retrieve();
		return max;
	}

	@Override
	public DataPoint < Double > getMin (){
		DLL<DataPoint<Double>> d = getAllDataPoints(); 
		DataPoint<Double> max = d.retrieve();
		d.findFirst();
		while(!d.last()) {
			if(d.retrieve().value < max.value) 
				max = d.retrieve();
			d.findNext();
		}
		if(d.retrieve().value < max.value) 
			max = d.retrieve();
		return max;
	}
}
