package sort;

/**
 * 快排
 *
 * @author wulizi
 */
public class QuickSort extends AbstractSort {

    @Override
    public void sort(Comparable[] a) {
        int rad = Math.random() > 0.5 ? 0 : 1;
        if (rad == 0) {
            quick3Way(a, 0, a.length - 1);
        } else {
            publicSort(a, 0, a.length - 1);
        }
    }

    private void publicSort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int j = partition(a, lo, hi);
        publicSort(a, lo, j - 1);
        publicSort(a, j + 1, hi);
    }

    private int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, lo, j);
        return j;
    }

    /**
     * 三项切分
     */
    @SuppressWarnings("unchecked")
    private void quick3Way(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int lt = lo, gt = hi, i = lo + 1;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                swap(a, i++, lt++);
            } else if (cmp > 0) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        quick3Way(a, lo, lt - 1);
        quick3Way(a, gt + 1, hi);
    }
}
