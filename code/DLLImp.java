

class Node<T>{
	public T data;
	public Node<T> next;
	public Node<T> previous;

	public Node () {
		data = null;
		next = null;
		previous = null;
	}

	public Node (T val) {
		data = val;
		next = null;
		previous= null;
	}

}
public class DLLImp<T> implements DLL<T>{
	private Node<T> head;
	private Node<T> current;
	private int size;
	
	public DLLImp() {
		head = current = null;
	}
	@Override
	public boolean empty() {
		return head == null;
	}

	@Override
	public boolean last() {
		return current.next == null;
	}
	@Override
	public boolean first() {
		return current.previous == null;
	}
	@Override
	public void findFirst() {
		current = head;
	}
	@Override
	public void findNext() {
		current = current.next;
	}
	@Override
	public void findPrevious() {
		current = current.previous;
	}
	@Override
	public T retrieve() {
		return current.data;
	}
	@Override
	public void update(T val) {
		current.data = val;
	}
	@Override
	public void insert(T val) {
		Node<T> tmp = new Node<T>(val);
		if(empty()) {
			current = head = tmp;
		}
		else {
			tmp.next = current.next;
			tmp.previous = current;
			if(current.next != null)
				current.next.previous = tmp;
			current.next = tmp;
			current = tmp;
		}
	size++;
	}
	@Override
	public int size() {
		return size;
	}
	@Override
	public void remove() {
		if(current == head) {
			head = head.next;
			if(head != null)
			   head.previous = null;
		}
		else {
			current.previous.next = current.next;
			if(current.next != null)
			   current.next.previous = current.previous;
		}

		if(current.next == null)
			current = head;
		else
			current = current.next;
		size--;
	
	}
}