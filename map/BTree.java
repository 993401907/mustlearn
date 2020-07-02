package map;

import java.util.Optional;

/**
 * B数
 *
 * @author wulizi
 */
public class BTree<Key extends Comparable<Key>, Value> {
    /**
     * 每个节点存放最大值
     */
    private static final int M = 4;

    private int N;
    private Node root;

    /**
     * 树的高度
     */
    private int height;

    /**
     * 树的节点
     */
    public BTree() {
        root = new Node(0);
    }

    private static class Node {
        private int m;
        private Entry[] children = new Entry[M];

        private Node(int m) {
            this.m = m;
        }
    }

    /**
     * 节点的内部值
     */
    private static class Entry {
        private Comparable key;
        private Object val;
        private Node next;

        public Entry(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public Value get(Key key) {
        return search(root, key, height);
    }

    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) {
                    return (Value) children[j].val;
                }
            }
        } else {
            for (int j = 0; j < x.m; j++) {
                if (j + 1 == x.m || less(key, children[j + 1].key)) {
                    return search(children[j].next, key, ht - 1);
                }
            }
        }
        return null;
    }

    public void put(Key key, Value value) {
        Node u = insert(root, key, value, height);
        N++;
        Optional.ofNullable(u).ifPresent(node -> {
            Node t = new Node(2);
            t.children[0] = new Entry(root.children[0].key, null, root);
            t.children[1] = new Entry(u.children[0].key, null, u);
            root = t;
            height++;
        });
    }

    private Node insert(Node h, Key key, Value val, int height) {
        int j;
        Entry t = new Entry(key, val, null);
        if (height == 0) {
            for (j = 0; j < M; j++) {
                if (less(key, h.children[j].key)) {
                    break;
                }
            }
        } else {
            for (j = 0; j < M; j++) {
                if (j + 1 == h.m || less(key, h.children[j++].key)) {
                    Node u = insert(h.children[j++].next, key, val, height - 1);
                    if (u == null) {
                        return null;
                    }
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        if (h.m - j >= 0) {
            System.arraycopy(h.children, j, h.children, j + 1, h.m - j);
        }
        h.children[j] = t;
        h.m++;
        if (h.m < M) {
            return null;
        } else {
            return spilt(h);
        }
    }

    /**
     * 切分节点
     */
    private Node spilt(Node node) {
        Node t = new Node(M / 2);
        node.m = M / 2;
        System.arraycopy(node.children, 2, t.children, 0, M / 2);
        return t;
    }

    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
}
