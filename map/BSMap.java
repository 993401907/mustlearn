package map;


import list.ArrayList;
import list.List;


/**
 * @author wulizi
 * 二分查询map
 */
public class BSMap<K extends Comparable<K>, V> implements Map<K, V> {

    private K[] keys;
    private V[] vals;
    private int size;
    private static final int INIT_CAPACITY = 2;

    public BSMap() {
        this(INIT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public BSMap(int capacity) {
        this.keys = (K[]) new Comparable[capacity];
        this.vals = (V[]) new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public void checkAndResize() {
        if (size == keys.length) {
            int newSize = 2 * keys.length;
            K[] newKeys = (K[]) new Comparable[newSize];
            V[] newVals = (V[]) new Object[newSize];
            System.arraycopy(this.keys, 0, newKeys, 0, this.keys.length);
            System.arraycopy(this.vals, 0, newVals, 0, this.vals.length);
            this.keys = newKeys;
            this.vals = newVals;
        }
    }

    public int find(K key) {
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            int cmp = key.compareTo(keys[mid]);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    @Override
    public void put(K key, V val) {
        int i = find(key);
        if (i < size && key.compareTo(keys[i]) == 0) {
            vals[i] = val;
            return;
        }
        checkAndResize();
        for (int j = size; j > i; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        size++;
    }

    @Override
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        int i = find(key);
        if (i < size && key.compareTo(keys[i]) == 0) {
            return vals[i];
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void delete(K key) {
        if (isEmpty()) {
            return;
        }
        int i = find(key);
        if (1 == size || key.compareTo(keys[i]) != 0) {
            return;
        }
        for (int j = i; i < size - 1; j++) {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }
        size--;
        keys[size] = null;
        vals[size] = null;
    }

    @Override
    public Iterable<K> keys() {
        List<K> list = new ArrayList<>();
        for (K key : keys) {
            list.add(key);
        }
        return list;
    }

    public Iterable<Entry<K, V>> entry() {
        List<Entry<K, V>> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Entry<K, V> entry = new Entry<>(keys[i], vals[i]);
            list.add(entry);
        }
        return list;
    }
    private static class Entry<K, V> {
        public K key;
        public V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
}
