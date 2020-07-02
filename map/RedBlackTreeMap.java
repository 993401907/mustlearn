package map;

/**
 * 红黑树
 *
 * @author wulizi
 */
public class RedBlackTreeMap<Key extends Comparable<Key>, Value> {
    private final boolean RED = true;
    private final boolean BLACK = false;

    private Node root;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        boolean color;
        int n;

        public Node(Key key, Value val, boolean color, int n) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.n = n;
        }
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, RED, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        if (isRed(x.right) && !isRed(x.left)) {
            x = rotateLeft(x);
        }
        if (isRed(x.left) && isRed(x.left.left)) {
            x = rotateRight(x);
        }
        if (isRed(x.left) && isRed(x.right)) {
            flipColor(x);
        }
        return x;
    }

    public Value get(Key key) {
        return get(root, key);
    }


    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x.val;
            }
        }
        return null;
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    public void delete(Key key) {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node delete(Node x, Key key) {
        if (key.compareTo(x.key) < 0) {
            if (!isRed(x.left) && !isRed(x.left.left)) {
                x = moveLeft(x);
            }
            x.left = delete(x.left, key);
        } else {
            if (isRed(x.left)) {
                x = rotateLeft(x);
            }
            if (key.compareTo(x.key) == 0 && x.right == null) {
                return null;
            }
            if (!isRed(x.right) && !isRed(x.right.left)) {
                x = moveRight(x);
            }
            if (key.compareTo(x.key) == 0) {
                x.val = get(x.right, min(x.right).key);
                x.key = min(x).key;
                x.right = deleteMin(x.right);
            } else {
                x.right = delete(x.right, key);
            }
        }
        return balance(x);

    }

    private Node deleteMin(Node x) {
        if (x == null) {
            return null;
        }

        if (!isRed(x.left) && !isRed(x.left.left)) {
            x = moveLeft(x);
        }
        x.left = deleteMin(x.left);
        return balance(x);
    }

    private Node deleteMax(Node x) {
        if (x == null) {
            return null;
        }
        if (!isRed(x.right) && !isRed(x.right.left)) {
            x = moveRight(x);
        }
        x.right = deleteMax(x.right);
        return balance(x);
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private void flipColor(Node x) {
        boolean rootRed = x.color == RED && x.left.color == BLACK && x.right.color == BLACK;
        boolean rootBlack = x.color == BLACK && x.left.color == RED && x.right.color == RED;
        if (rootBlack || rootRed) {
            x.color = !x.color;
            x.left.color = !x.left.color;
            x.right.color = !x.right.color;
        }

    }

    private Node rotateLeft(Node x) {
        Node h = x.right;
        x.right = h.left;
        h.left = x;
        h.color = x.color;
        x.color = RED;
        h.n = x.n;
        x.n = size(h.left) + size(h.right) + 1;
        return h;
    }

    private Node rotateRight(Node x) {
        Node h = x.left;
        x.left = h.right;
        h.right = x;
        h.color = x.color;
        x.color = RED;
        h.n = x.n;
        x.n = size(h.left) + size(h.right) + 1;
        return h;
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    private Node moveLeft(Node x) {
        flipColor(x);
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
        }
        return balance(x);
    }

    private Node moveRight(Node x) {
        flipColor(x);
        if (isRed(x.left.left)) {
            x = rotateRight(x);
        }
        return balance(x);
    }

    private Node balance(Node h) {

        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColor(h);
        }

        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }
}
