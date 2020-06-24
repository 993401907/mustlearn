package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author wulizi
 * 双链表
 */
public class LinkedList<E> implements List<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIter();
    }

    private class LinkedListIter implements Iterator<E> {
        Node<E> curr = first;

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
        Node<E> prev;
        Node<E> next;
        E val;

        public Node(Node<E> prev, Node<E> next, E val) {
            this.next = next;
            this.val = val;
            this.prev = prev;
        }
    }

    /**
     * 添加元素到头节点
     */
    private void linkFirst(E val) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, f, val);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    /**
     * 添加元素到尾节点
     */
    private void linkLast(E val) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, null, val);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void check(int idx) {
        if (idx > size || idx < 0) {
            throw new IndexOutOfBoundsException("越界了大哥!");
        }
    }

    /**
     * 在其中一个节点前连接
     */
    private void linkBefore(E val, Node<E> node) {
        Node<E> prev = node.prev;
        Node<E> newNode = new Node<>(prev, node, val);
        node.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    /**
     * 取消头节点连接
     */
    private E unlinkFirst(Node<E> f) {
        E val = f.val;
        Node<E> next = f.next;
        f.next = null;
        f.val = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return val;
    }

    /**
     * 取消尾节点连接
     */
    private E unlinkLast(Node<E> l) {
        E val = l.val;
        Node<E> prev = l.prev;
        l.val = null;
        l.prev = null;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return val;
    }

    /**
     * 解除一个节点的连接
     */
    E unlink(Node<E> node) {
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        E val = node.val;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.val = null;
        size--;
        return val;
    }

    @Override
    public E get(int idx) {
        return null;
    }

    @Override
    public void add(E value) {
        linkLast(value);
    }

    public E getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.val;
    }

    public E getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.val;
    }

    public E removeFirst() {
        return unlinkFirst(first);
    }

    public E removeLast() {
        return unlinkLast(last);
    }

    @Override
    public void add(int idx, E value) {
        if (idx == size) {
            linkLast(value);
        }
        linkBefore(value, getNode(idx));
        size++;
    }

    private Node<E> getNode(int idx) {
        check(idx);
        if (idx > (size >> 1)) {
            Node<E> temp = first;
            for (int i = 0; i < idx; i++) {
                temp = temp.next;
            }
            return temp;
        } else {
            Node<E> temp = last;
            for (int i = size - 1; i > idx; i--) {
                temp = temp.prev;
            }
            return temp;
        }
    }

    @Override
    public void set(int idx, E value) {
        Node<E> node = getNode(idx);
        node.val = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int idx) {
        Node<E> node = getNode(idx);
        return unlink(node);
    }

    @Override
    public void remove(E val) {
        if (val == null) {
            for (Node<E> temp = first; temp != null; temp = temp.next) {
                if (temp.val == null) {
                    unlink(temp);
                    return;
                }
            }
        } else {
            for (Node<E> temp = first; temp != null; temp = temp.next) {
                if (val.equals(temp.val)) {
                    unlink(temp);
                    return;
                }
            }
        }
    }

    @Override
    public int indexOf(E val) {
        int index = 0;
        if (val == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.val == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (val.equals(x.val)) {
                    return index;
                }
                index++;
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
        return indexOf(val) != -1;
    }
}
