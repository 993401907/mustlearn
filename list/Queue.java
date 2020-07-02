package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 队列
 *
 * @author wulizi
 */
public class Queue<Item> implements Iterable<Item> {
    private Node root;
    private Node tail;
    private int N;

    public Queue() {
        root = null;
        tail = null;
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    /**
     * 进队
     */
    public void enqueue(Item item) {
        Node x = new Node(item);
        if (isEmpty()) {
            root = x;
            tail = x;
        } else {
            tail.next = x;
            tail = tail.next;
        }
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node oldRoot = root;
        Item item = oldRoot.item;
        root = root.next;
        oldRoot.next = null;
        if (isEmpty()) {
            tail = null;
        }
        return item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new Iter();
    }

    private class Node {
        Node next;
        Item item;

        Node(Item item) {
            this.item = item;
        }
    }

    private class Iter implements Iterator<Item> {
        private Node x = root;

        @Override
        public boolean hasNext() {
            return x != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = x.item;
            x = x.next;
            return item;
        }
    }
}
