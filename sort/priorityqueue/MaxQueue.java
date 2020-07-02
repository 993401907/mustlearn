package sort.priorityqueue;

/**
 * @author wulizi
 */
public class MaxQueue<Item extends Comparable<Item>> {

    private Comparable[] pq;
    private int n;

    public MaxQueue(int maxSize) {
        pq = new Comparable[maxSize + 1];
        n = 0;
    }


    public void insert(Item item) {
        pq[++n] = item;
        swim(n);
    }

    @SuppressWarnings("unchecked")
    public Item deleteMax() {
        Item max = (Item) pq[1];
        swap(n--, 1);
        pq[n + 1] = null;
        sink(1);
        return max;
    }

    private void swim(int k) {
        while (k > 1 && less(k, k / 2)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= n) {
            int j = k * 2;
            if (j < n && less(pq[j], pq[j + 1])) {
                j = j + 1;
            }
            if (!less(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    @SuppressWarnings("unchecked")
    private boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private void swap(int i, int j) {
        Comparable t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
}
