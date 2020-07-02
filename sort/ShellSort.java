package sort;

/**
 * 希尔排序
 *
 * @author wulizi
 */
public class ShellSort extends AbstractSort {

    @Override
    public void sort(Comparable[] a) {
        if (!isSorted(a)) {
            int step = 3;
            int h = 1;
            int n = a.length;
            while (h < n / step) {
                h = step * h;
            }
            while (h >= 1) {
                for (int i = h; i < n; i++) {
                    Comparable temp = a[i];
                    int j = i;
                    for (; j >= h && less(temp, a[j - h]); j -= h) {
                        a[j] = a[j - h];
                    }
                    a[j] = temp;
                }
                h /= step;
            }
        }
    }
}
