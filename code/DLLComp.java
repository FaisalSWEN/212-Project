// A DLL where data is comparable.
public interface DLLComp<T extends Comparable<T>> extends DLL<T> {
	// [Requires Chapter on Sorting] Sorts the list. The parameter "increasing"
	// indicates whether the sort is done in increasing or decreasing order.
	void sort(boolean increasing);

	// Returns the maximum element. The list must not be empty.
	T getMax();

	// Returns the maximum element. The list must not be empty.
	T getMin();
}
