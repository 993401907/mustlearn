package map;


import list.Queue;


/**
 * 线性探测法
 *
 * @author wulizi
 */
public class LinearProbingHashST<Key, Value> {
    private final static int INIT_CAPACITY = 4;
    private int N;
    private int M;

    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int capacity) {
        this.M = capacity;
        keys = (Key[]) new Object[capacity];
        values = (Value[]) new Object[capacity];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    //扩容
    private void resize(int chains) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<>(chains);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], values[i]);
            }
        }
        this.keys = temp.keys;
        this.values = temp.values;
        this.M = temp.M;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    //添加
    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
        }
        //扩容
        if (N >= M >> 1) {
            resize(M << 1);
        }
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                values[i] = value;
            }
        }
        this.keys[i] = key;
        this.values[i] = value;
        this.N++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }
        return null;
    }

    public void delete(Key key) {
        if (!contains(key)) {
            return;
        }
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }

        keys[i] = null;
        values[i] = null;
        i = (i + 1) % M;

        while (keys[i] != null) {
            Key keyToReHash = keys[i];
            Value valToReHash = values[i];
            keys[i] = null;
            values[i] = null;
            N--;
            this.put(keyToReHash, valToReHash);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N <= M / 8) {
            resize(M / 2);
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
            }
        }
        return queue;
    }

}
