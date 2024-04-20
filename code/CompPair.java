// Class used to store a pair of elements that is comparable on the second element.
public class CompPair<U, V extends Comparable<V>> extends Pair<U, V> implements Comparable<CompPair<U, V>> {
	public CompPair(U first, V second) {
		super(first, second);
	}

	@Override
	public int compareTo(CompPair<U, V> other) {
		return this.second.compareTo(other.second);
	}
}