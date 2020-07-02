package sort;

/**
 * 选择排序
 *
 * @author wulizi
 */
public class SelectionSort extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        if (!isSorted(a)) {
            for (int i = 0; i < a.length; i++) {
                int min = i;
                for (int j = i; j < a.length; j++) {
                    if (less(a[j], a[min])) {
                        min = j;
                    }
                }
                swap(a, i, min);
            }
        }
    }
}
