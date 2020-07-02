package sort;

/**
 * 插入排序
 *
 * @author wulizi
 */
public class InsertSort extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        if (!isSorted(a)) {
            for (int i = 0; i < a.length; i++) {
                int j = i;
                Comparable temp = a[i];
                for (; j > 0 && less(temp, a[j - 1]); j--) {
                    a[j] = a[j - 1];
                }
                a[j] = temp;
            }
        }
    }
}
