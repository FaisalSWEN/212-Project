import java.text.SimpleDateFormat;
import java.util.Date;

// Represents a single data point in a time series. Each data point consists of a date and a corresponding value.
public class DataPoint<T> {

	// The date of the data point.
	public Date date;

	// The value of the data point.
	public T value;

	public DataPoint(Date date, T value) {
		this.date = date;
		this.value = value;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date) + " : " + value.toString();
	}
}
