public class DLLCompImp<T extends Comparable<T>> implements DLLComp<T>{
	private Node<T> head;
	private Node<T> current;
	private int size;
	
	public DLLCompImp() {
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
	@Override
	public T getMin(){
		if(head == null) 
			return null;
		Node<T> p = head;
		T min = p.data;
		while(p != null) {
			if(p.data.compareTo(min) < 0)
				min = p.data;
			p = p.next; 
		}
		return min;
		
	}
	@Override
	public T getMax() {
		if(head == null) return null;
		Node<T> p = head;
		T max = p.data;
		while(p != null) {
			if(p.data.compareTo(max) > 0)
				max = p.data;
			p = p.next;
		}
		return max;	
	}

 public void sort(boolean increasing) {
		head = sortLL(head, increasing);
	}

	private Node<T> sortLL(Node<T> head, boolean increasing) {
		if (head == null || head.next == null) {
				return head;
		}
		Node<T> middle = findMiddle(head);
		Node<T> right = middle.next;
		middle.next = null;
		if (right != null) {
				right.previous = null;
		}

		Node<T> sortedLeft = sortLL(head, increasing);
		Node<T> sortedRight = sortLL(right, increasing);
		return mergeTwoLists(sortedLeft, sortedRight, increasing);
	}

	private Node<T> findMiddle(Node<T> head) {
		Node<T> slow = head;
		Node<T> fast = head;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	private Node<T> mergeTwoLists(Node<T> list1, Node<T> list2, boolean increasing) {
		Node<T> dummyNode = new Node<>(null);
		Node<T> temp = dummyNode;
		while (list1 != null && list2 != null) {
			if ((increasing && list1.data.compareTo(list2.data) < 0) ||
					(!increasing && list1.data.compareTo(list2.data) > 0)) {
				temp.next = list1;
				list1.previous = temp;
				list1 = list1.next;
			} else {
				temp.next = list2;
				list2.previous = temp;
				list2 = list2.next;
			}
			temp = temp.next;
		}

		if (list1 != null) {
			temp.next = list1;
			list1.previous = temp;
		} else if (list2 != null) {
			temp.next = list2;
			list2.previous = temp;
		}
		if (dummyNode.next != null) {
			dummyNode.next.previous = null;  
		}
		return dummyNode.next;
	}
}
