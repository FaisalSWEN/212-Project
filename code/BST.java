public class BST<K extends Comparable<K>, T> implements Map<K, T> {
    private class Node {
        K key;
        T data;
        Node left, right;

        public Node(K key, T data) {
            this.key = key;
            this.data = data;
            left = right = null;
        }
    }

    private Node root;
    private Node current; 

    public BST() {
        root = null;
        current = null;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    @Override
    public boolean empty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;
        current = null;
    }

    @Override
    public T retrieve() {
        return current != null ? current.data : null;
    }

    @Override
    public void update(T e) {
        if (current != null) {
            current.data = e;
        }
    }

    @Override
    public boolean find(K key) {
        current = find(root, key);
        return current != null;
    }

    private Node find(Node node, K key) {
        if (node == null || node.key.compareTo(key) == 0) {
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            return find(node.left, key);
        } else {
            return find(node.right, key);
        }
    }

    @Override
    public int nbKeyComp(K key) {
        return nbKeyComp(root, key);
    }

    private int nbKeyComp(Node node, K key) {
        if (node == null || node.key.compareTo(key) == 0) {
            return 1;
        }
        if (key.compareTo(node.key) < 0) {
            return 1 + nbKeyComp(node.left, key);
        } else {
            return 1 + nbKeyComp(node.right, key);
        }
    }

    @Override
    public boolean insert(K key, T data) {
        if (find(key)) {
            return false; 
        }
        root = insert(root, key, data);
        return true;
    }

    private Node insert(Node node, K key, T data) {
        if (node == null) {
            return new Node(key, data);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, data);
        } else {
            node.right = insert(node.right, key, data);
        }
        return node;
    }

    @Override
    public boolean remove(K key) {
        root = remove(root, key);
        return current != null;
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            current = null;
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                current = node;
                return node.right;
            } else if (node.right == null) {
                current = node;
                return node.left;
            } else {
                Node successor = findMin(node.right);
                node.key = successor.key;
                node.data = successor.data;
                node.right = remove(node.right, successor.key);
            }
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public DLLComp<K> getKeys() {
        DLLComp<K> keys = new DLLComp<>();
        inorderTraversal(root, keys);
        return keys;
    }

    private void inorderTraversal(Node node, DLLComp<K> keys) {
        if (node != null) {
            inorderTraversal(node.left, keys);
            keys.add(node.key);
            inorderTraversal(node.right, keys);
        }
    }
}
