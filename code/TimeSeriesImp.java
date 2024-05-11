import java.util.Date;

public class TimeSeriesImp<T>  implements TimeSeries<T>{

	DLL<DataPoint<T>> all_points;
	
	public TimeSeriesImp(){
		all_points = new DLLImp();
	}
	
	@Override
	public int size(){
		return all_points.size();
	}
	@Override
	public boolean empty() {
		return all_points.empty();
	}
	
	@Override
	public T getDataPoint(Date date) { 
		if(all_points.empty()) return null;
		else {
		all_points.findFirst();
		while(!all_points.last()) {
			if(all_points.retrieve().date.compareTo(date) == 0) {
				return all_points.retrieve().value;}
			all_points.findNext();
		}
		if(all_points.retrieve().date.compareTo(date) == 0) { // check the last element
			return all_points.retrieve().value;
			}
		}
			return null;
	}

	public DLL<Date> getAllDates() {
		DLLCompImp<Date> allDates = new DLLCompImp<Date>(); // to use sorting method without casting
		if (all_points.empty()) return allDates;

		all_points.findFirst();
		while (!all_points.last()) {
				// System.out.println("Inserting date: " + all_points.retrieve().date); // checking line
				allDates.insert(all_points.retrieve().date);
				all_points.findNext();
		}
		allDates.insert(all_points.retrieve().date); // This is for the last element
		//System.out.println("Inserting date: " + all_points.retrieve().date); // checking line
		allDates.sort(true);
		return allDates;
	}
	@Override
	public Date getMinDate () {
		DLLComp<Date> dates; // to call getMin method 
		dates= (DLLComp<Date>)getAllDates();
		if(dates.empty()) return null;
		
		return dates.getMin();
		
	}
	@Override
	public Date getMaxDate () {
		DLLComp<Date> dates;
		dates =(DLLComp<Date>) getAllDates();
		if(dates.empty()) return null;
		return dates.getMax();
	}
	@Override
	public boolean addDataPoint ( DataPoint <T > dataPoint ) {
		T da = getDataPoint(dataPoint.date);
		if(da == null) {
			all_points.insert(dataPoint);
			return true;}
		else
			return false;
	}
	@Override
	public boolean updateDataPoint ( DataPoint <T > dataPoint ) {
		T da = getDataPoint(dataPoint.date);
		if(da == null) return false;
		else {
			all_points.update(dataPoint);
		return true;}
	}
	@Override
	public boolean removeDataPoint ( Date date ) {
		T da = getDataPoint(date);
		if(da != null) {
			all_points.remove();
			return true;
		}
		else
			return false;
	}
	@Override
	public DLL < DataPoint <T >> getAllDataPoints (){
		DLLCompImp<CompPair<DataPoint<T> , Date>> newDll = new DLLCompImp<CompPair<DataPoint<T> , Date>>();
		if(all_points.empty()) return new DLLCompImp();
		
		all_points.findFirst();
		while(!all_points.last()) {
		newDll.insert(new CompPair(all_points.retrieve(), all_points.retrieve().date));
		all_points.findNext();
		}
		newDll.insert(new CompPair(all_points.retrieve(), all_points.retrieve().date));
		
		newDll.sort(true);
		
		DLL<DataPoint<T>> result = new DLLImp<DataPoint<T>>();
		
		newDll.findFirst();
		while(!newDll.last()) {
			result.insert(newDll.retrieve().first);
			newDll.findNext();
		}
		result.insert(newDll.retrieve().first);
		
		return result;
		
	}
	@Override
	public DLL < DataPoint <T >> getDataPointsInRange ( Date startDate , Date endDate ){
			DLL<DataPoint<T>> res = new DLLImp<>();
		if(startDate != null && endDate != null) { // invalid date input
			if(startDate.compareTo(endDate) > 0)
			return res;
		}
		DLL<DataPoint<T>> sortedPoints = getAllDataPoints();
		if(sortedPoints.empty())
			return res;
		
		sortedPoints.findFirst();
		while(startDate != null && !sortedPoints.last() // to reach start date Node
				&& sortedPoints.retrieve().date.compareTo(startDate) < 0) {
			sortedPoints.findNext();
		}
		while(!sortedPoints.last() && (endDate == null || sortedPoints.retrieve().date.compareTo(endDate) <= 0)) 
			// retrieve all dataPoints in range
		{
			res.insert(sortedPoints.retrieve());
			sortedPoints.findNext();
		}
		if(endDate == null || sortedPoints.last() &&
				sortedPoints.retrieve().date.compareTo(endDate) <= 0) {
			res.insert(sortedPoints.retrieve());
		}
		return res;
	}
}
