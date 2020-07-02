package map;


//

import list.Queue;

/**
 * 拉链法HashMap
 *
 * @author wulizi
 */

public class SeparateChainingHashST<Key, Value> {
    /**
     * 初始容量
     */
    private final static int INIT_CAPACITY = 4;
    /**
     *   key-value数量
     */

    private int M;
    /**
     * hashtable容量
     */
    private int N;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    //自定义容量
    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<Key, Value>();
        }
    }

    //扩容
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp =
                new SeparateChainingHashST<>(chains);
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }

    //计算key的hash
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int size() {
        return N;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(Key key) {
        int i = this.hash(key);
        return st[i].get(key);
    }

    public void put(Key key, Value value) {
        //如果值为空，删除该键
        if (value == null) {
            this.delete(key);
            return;
        }
        //扩容
        if (N >= M * 10) {
            resize(2 * M);
        }
        int i = this.hash(key);
        if (!st[i].contains(key)) {
            N++;
        }
        st[i].put(key, value);
    }

    //删除
    public void delete(Key key) {
        int i = this.hash(key);
        if (st[i].contains(key)) {
            N--;
        }
        st[i].delete(key);

        if (M > INIT_CAPACITY && N <= 2 * M) {
            resize(M / 2);
        }
    }


    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

}
