package map;

/**
 * 二分查找map
 *
 * @author wulizi
 */
public class BinarySearchMap<Key extends Comparable<Key>, Value> implements
        Map<Key, Value>{

    private Key[] keys;
    private Value[] values;
    private int n;

    public BinarySearchMap() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempKeys = (Key[]) new Comparable[capacity];
        Value[] tempVals = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempKeys[i] = keys[i];
            tempVals[i] = values[i];
        }
        values = tempVals;
        keys = tempKeys;
    }

    @SuppressWarnings("unchecked")
    public BinarySearchMap(int capacity) {
        this.keys = (Key[]) new Comparable[capacity];
        this.values = (Value[]) new Object[capacity];
        this.n = 0;
    }

    @Override
    public void put(Key key, Value val) {
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0) {
            values[i] = val;
            return;
        }
        if (n == keys.length) {
            resize(keys.length << 1);
        }
        for (int j = n; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[i] = key;
        values[i] = val;
        n++;
    }

    @Override
    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0) {
            return values[i];
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) != 0) {
            return;
        }
        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        n--;
        keys[n] = null;
        values[n] = null;
        if (n > 0 && n < keys.length >>> 2) {
            resize(keys.length >>> 1);
        }
    }

    @Override
    public boolean contains(Key key) {
        Value value = get(key);
        return value != null;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Key min() {
        if (isEmpty()) {
            return null;
        }
        return keys[0];
    }

    @Override
    public Key max() {
        if (isEmpty()) {
            return null;
        }
        return keys[n - 1];
    }

    @Override
    public Key floor(Key key) {
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0) {
            return keys[i];
        }
        if (i == 0) {
            return null;
        }
        return keys[i-1];
    }

    @Override
    public Key ceiling(Key key) {
        int i = rank(key);
        if (i == n) {
            return null;
        }
        return keys[i];
    }

    @Override
    public int rank(Key key) {
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    @Override
    public Key select(int k) {
        if (k >= n || k < 0) {
            throw new RuntimeException();
        }
        return keys[k];
    }

    public Key[] keys() {
        return keys;
    }
    @Override
    public void deleteMin() {
        delete(min());
    }

    @Override
    public void deleteMax() {
        delete(max());
    }
}
