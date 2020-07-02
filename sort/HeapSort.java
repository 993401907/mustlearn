package sort;

/**
 * 堆排序
 *
 * @author wulizi
 */
public class HeapSort extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        int n = a.length;
        for (int i = n / 2; i > 0; i--) {
            sink(a, i, n);
        }
        while (n > 1) {
            swap(a, 1, n--);
            sink(a, 1, n);
        }
    }

    private void sink(Comparable[] a, int k, int n) {
        while (k * 2 <= n) {
            int j = k * 2;
            if (j < n && less(a, j, j + 1)) {
                j = j + 1;
            }
            if (!less(k, j)) {
                break;
            }
            swap(a, k, j);
            k = j;
        }
    }

    @Override
    public void swap(Comparable[] a, int i, int j) {
        super.swap(a, i - 1, j - 1);
    }

    public boolean less(Comparable[] a, int i, int j) {
        return super.less(a[i - 1], a[j - 1]);
    }
}
