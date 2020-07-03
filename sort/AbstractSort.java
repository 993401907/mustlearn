package sort;

import java.util.Arrays;

/**
 * 排序的抽象类
 *
 * @author wulizi
 */
public abstract class AbstractSort {
    /**
     * 排序
     *
     * @param a 带排序数组
     */
    public abstract void sort(Comparable<?>[] a);

    public void swap(Comparable<?>[] a, int i, int j) {
        Comparable<?> temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    @SuppressWarnings("unchecked")
    public boolean less(Comparable v, Comparable<?> w) {
        return v.compareTo(w) < 0;
    }

    public boolean isSorted(Comparable<?>[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public void show(Comparable<?>[] a){
        System.out.println(Arrays.toString(a));
    }
}
