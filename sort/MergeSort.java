package sort;

/**
 * 归并排序
 *
 * @author wulizi
 */
public class MergeSort extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        if (!isSorted(a)) {
            int n = a.length;
            Comparable[] temp = new Comparable[n];
            int rd = Math.random() > 0.5 ? 0 : 1;
            if (rd == 0) {
                topToDown(a, temp, 0, n - 1);
            } else {
                downToTop(a, temp);
            }
        }
    }

    private void topToDown(Comparable[] a, Comparable[] temp, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        topToDown(a, temp, lo, mid);
        topToDown(a, temp, mid + 1, hi);
        merge(a, temp, lo, mid, hi);
    }

    private void downToTop(Comparable[] a, Comparable[] temp) {
        int n = a.length;
        for (int sz = 1; sz < n; sz = sz * 2) {
            for (int lo = 0; lo < n - sz; lo += sz * 2) {
                merge(a, temp, lo, lo + sz - 1, Math.min(lo + 2 * sz - 1, n - 1));
            }
        }
    }

    private void merge(Comparable[] a, Comparable[] temp, int lo, int mid, int hi) {
        System.arraycopy(a, lo, temp, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (j > hi) {
                a[k] = temp[i++];
            } else if (i > mid) {
                a[k] = temp[j++];
            } else if (less(temp[i], temp[j])) {
                a[k] = temp[i++];
            } else {
                a[k] = temp[j++];
            }
        }
    }
}
