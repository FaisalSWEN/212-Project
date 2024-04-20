// Interface representing a doubly linked list.
public interface DLL<T> {

	// Returns the number of elements in the list.
	int size();

	// Returns true if the list is empty, false otherwise.
	boolean empty();

	// Returns true if the current position is at the last element, false otherwise.
	boolean last();

	// Returns true if the current position is at the first element, false
	// otherwise.
	boolean first();

	// Sets the current position to the first element of the list (detailed
	// specification seen in class).
	void findFirst();

	// Advances the current position to the next element in the list (detailed
	// specification seen in class).
	void findNext();

	// Moves the current position to the previous element in the list (detailed
	// specification seen in class).
	void findPrevious();

	// Retrieves the element at the current position.
	T retrieve();

	// Updates the element at the current position with the provided value.
	void update(T val);

	// Inserts the provided element at the current position in the list (detailed
	// specification seen in class).
	void insert(T val);

	// Removes the element at the current position from the list (detailed
	// specification seen in class).
	void remove();
}
