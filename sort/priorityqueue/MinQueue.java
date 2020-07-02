package sort.priorityqueue;


import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 最小优先队列
 *
 * @author 伍立子
 */
public class MinQueue<Key> implements Iterable<Key> {
    /**
     * pq    :  队列存储空间
     * N    :  key数量
     */
    private Key[] pq;
    private int N;
    private Comparator<Key> comparator;

    public MinQueue() {
        this(1);
    }

    public MinQueue(int initCapacity) {
        this.N = 0;
        this.pq = (Key[]) new Object[initCapacity];
    }

    public MinQueue(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq =  (Key[]) new Object[initCapacity];
        N = 0;
    }

    public MinQueue(Iterable<Key> iterable) {
        this(1);
        for (Key key : iterable) {
            insert(key);
        }
    }

    public MinQueue(Comparator<Key> comparator) {
        this(1, comparator);
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Key min() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        return pq[1];
    }


    private void resize(int capacity) {
        assert capacity > N;
        Key[] temp = (Key[]) new Object[capacity];
        if (N >= 0) {
            System.arraycopy(pq, 1, temp, 1, N);
        }
        pq = temp;
    }


    public void insert(Key x) {

        if (N == pq.length - 1) {
            resize(2 * pq.length);
        }

        pq[++N] = x;
        swim(N);
    }


    public Key delMin() {
        if (N == 0) {
            throw new RuntimeException("Priority queue underflow");
        }
        exch(1, N);
        Key min = pq[N--];
        sink(1);
        pq[N + 1] = null;
        if ((N > 0) && (N == (pq.length - 1) / 4)) {
            resize(pq.length / 2);
        }
        return min;
    }

    public void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    public void sink(int k) {
        while (2 * k < N) {
            int j = 2 * k;
            if (j < N && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        } else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }


    private class HeapIterator implements Iterator<Key> {
        private MinQueue<Key> copy;

        public HeapIterator() {
            if (comparator == null) {
                copy = new MinQueue<Key>(size());
            } else {
                copy = new MinQueue<Key>(size(), comparator);
            }

            for (int i = 1; i <= N; i++) {
                copy.insert(pq[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMin();
        }
    }
}
