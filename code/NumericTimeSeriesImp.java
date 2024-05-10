import java.util.Date;

public class NumericTimeSeriesImp extends TimeSeriesImp<Double> implements NumericTimeSeries  {
	@Override
	public NumericTimeSeries calculateMovingAverage ( int period ) {
		NumericTimeSeries series = new NumericTimeSeriesImp();
		DLL<DataPoint<Double>> alldataPoints = getAllDataPoints();
		
		if(alldataPoints.empty() || period > size())
			return series;
		
		
		double sum =0;
		Date date = null;
		
		alldataPoints.findFirst();
		for(int i = 0;i<period;i++) {
			alldataPoints.findNext();
		}
		while(!alldataPoints.last()) {
			
			date = alldataPoints.retrieve().date;
			for(int i =0;i<period;i++) {
			sum += alldataPoints.retrieve().value;
			
			alldataPoints.findPrevious();}
		
		series.addDataPoint(new DataPoint(date, sum / period));
		for(int i = 0;i<period +1 ; i++) {
			alldataPoints.findNext();
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