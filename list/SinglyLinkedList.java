package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author wulizi
 * 单链表
 */
public class SinglyLinkedList<E> implements List<E> {
    private int size;
    private Node<E> root = null;

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIter();
    }
    private class LinkedListIter implements Iterator<E> {
        Node<E> curr = root;

        @Override
        public boolean hasNext() {
            return curr.next != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = curr.val;
            curr = curr.next;
            return e;
        }
    }
    private static class Node<E> {
        Node<E> next;
        E val;

        public Node(Node<E> next, E val) {
            this.next = next;
            this.val = val;
        }
    }

    @Override

    public E get(int idx) {
        check(idx);
        Node<E> node = null;
        Node<E> curr = root;
        int i = 0;
        while (i < idx) {
            curr = curr.next;
            i++;
        }
        node = curr;
        return node.val;
    }

    private void check(int idx) {
        if (idx > size || idx < 0) {
            throw new IndexOutOfBoundsException("越界了大哥!");
        }
    }

    @Override
    public void add(E value) {
        if (root == null) {
            root = new Node<>(null, value);
        } else {
            Node<E> node = new Node<>(null, value);
            Node<E> curr = root;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = node;
        }
        size++;
    }

    @Override
    public void add(int idx, E value) {
        if (idx == 0) {
            Node<E> temp = root;
            root = new Node<>(temp, value);
        } else {
            check(idx);
            Node<E> curr = root;
            for (int i = 0; i < idx; i++) {
                curr = curr.next;
            }
            Node<E> temp = curr.next;
            curr.next = new Node<>(temp, value);
        }
        size++;
    }

    @Override
    public void set(int idx, E value) {
        check(idx);
        Node<E> curr = root;
        for (int i = 0; i < idx; i++) {
            curr = curr.next;
        }
        curr.val = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int idx) {
        check(idx);
        Node<E> prev = null;
        Node<E> curr = root;
        for (int i = 0; i < idx; i++) {
            prev = curr;
            curr = curr.next;
        }
        if (prev != null) {
            prev.next = curr.next;
        }else {
            root = curr.next;
        }
        size--;
        return curr.val;
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
    public int indexOf(E val) {
        Node<E> curr = root;
        for (int i = 0; i < size; i++) {
            if (val.equals(curr.val)) {
                return i;
            }
            curr = curr.next;
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
}
