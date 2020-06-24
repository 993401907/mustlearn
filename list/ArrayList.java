package list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author wulizi
 * 数组实现list
 */
public class ArrayList<E> implements List<E> {
    private transient Object[] data;
    private int size;
    private static final Object[] EMPTY_DATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 初始长度为10
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initCapacity) {
        if (initCapacity == 0) {
            this.data = EMPTY_DATA;
        } else if (initCapacity > 0) {
            this.data = new Object[initCapacity];
        } else {
            throw new IllegalArgumentException("初始长度参数不能小与0");
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public E get(int idx) {
        check(idx);
        return (E) data[idx];
    }

    @Override
    public void add(E value) {
        ensureCapacity(size + 1);
        data[size++] = value;
    }


    @Override
    public void add(int idx, E value) {
        check(idx);
        ensureCapacity(size + 1);
        System.arraycopy(data, idx, data, idx + 1, size - idx);
        data[idx] = value;
        size++;
    }

    private void check(int idx) {
        if (idx > size || idx < 0) {
            throw new IndexOutOfBoundsException("数组越界了大哥!");
        }
    }

    /**
     * 确保容量能够装下
     *
     * @param minCapacity 所需最小容量
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity - data.length > 0) {
            this.reSize(minCapacity);
        }
    }

    /**
     * 调整数组大小 为原来的1.5倍
     *
     * @param minCapacity 所需最小容量
     */
    private void reSize(int minCapacity) {
        int oldCapacity = this.data.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        if (newCapacity - MAX_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        data = Arrays.copyOf(data, newCapacity);
    }

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_SIZE) ?
                Integer.MAX_VALUE :
                MAX_SIZE;
    }

    @Override
    public void set(int idx, E value) {
        check(idx);
        data[idx] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int idx) {
        check(idx);
        E oldVal = (E) data[idx];
        int moveNum = size - idx - 1;
        if (moveNum > 0) {
            System.arraycopy(data, idx + 1, data, idx, moveNum);
        }
        data[--size] = null;
        return oldVal;
    }

    @Override
    public void remove(E val) {
        int i = indexOf(val);
        while (i != -1) {
            remove(i);
            i = indexOf(val);
        }
    }

    @Override
    public int indexOf(Object val) {
        if (val == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (val.equals(data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E val) {
        return indexOf(val) > 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIter();
    }

    private class ListIter implements Iterator<E> {
        int curr = 0;

        @Override
        public boolean hasNext() {
            return curr < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = (E) data[curr];
            curr++;
            return e;
        }
    }

}
