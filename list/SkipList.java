package list;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wulizi
 * 跳跃链表
 */
public class SkipList<E extends Comparable<? super E>> {


    /**
     * 最大层数
     */
    private int level;
    /**
     * 当前最大层
     */
    private int currentLevel;
    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private Node<E> root;
    private int[] powers;

    public SkipList() {
        this(4);
    }

    public SkipList(int level) {
        this.level = level;
        this.powers = new int[level];
        calculatePowers();
        init();
    }

    private static class Node<E> {
        private Node<E> next;
        private Node<E> down;
        private E val;

        public Node(E val) {
            this.val = val;
        }

        public Node() {

        }

    }


    /**
     * 计算每一层需要摇到随机数的大小
     */

    public void calculatePowers() {
        powers[level - 1] = (2 << (level - 1)) - 1;
        for (int i = level - 2, j = 0; i >= 0; i--, j++) {
            powers[i] = powers[i + 1] - (2 << j);
        }

    }

    public int chooseLevel() {
        int i, r = Math.abs(random.nextInt()) % powers[level - 1] + 1;
        for (i = 1; i < level; i++) {
            if (r < powers[i]) {
                return i - 1;
            }
        }
        return i - 1;
    }

    /**
     * 构造两个空节点 代表链表起始和结束
     */
    private void init() {
        root = new Node<>();
        Node<E> minNode = root;
        Node<E> maxNode = new Node<>();
        root.next = maxNode;
        for (int i = 0; i < level - 1; i++) {
            minNode.down = new Node<>();
            minNode = minNode.down;
            maxNode.down = new Node<>();
            maxNode = maxNode.down;
            minNode.next = maxNode;
        }
    }

    public boolean contains(E val) {
        Node<E> prev = root;
        Node<E> curr = root.next;
        while (true) {
            // 判断链表是否走到了最后
            if (curr.val == null) {
                prev = prev.down;
                //判断链表是否走到了最底
                if (prev == null) {
                    return false;
                }
                curr = prev.next;
            } else if (val.compareTo(curr.val) == 0) {
                return true;
            } else if (val.compareTo(curr.val) < 0) {
                prev = prev.down;
                curr = prev.next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
    }

    public void insert(E val) {
        int k = chooseLevel();
        currentLevel = Math.max(k + 1, currentLevel);

        Node<E> up = null;
        Node<E> prev = root;
        int sub = level - currentLevel;
        while (sub-- != 0) {
            prev = prev.down;
        }
        Node<E> curr = prev.next;
        for (int i = currentLevel - 1; i >= 0; i--) {
            while (true) {
                assert curr != null;
                // 判断链表是否走到了最后
                if (curr.val == null) {
                    if (i <= k) {
                        if (up == null) {
                            up = new Node<>(val);
                        } else {
                            up.down = new Node<>(val);
                            up = up.down;
                        }
                        prev.next = up;
                        up.next = curr;
                    }
                    prev = prev.down;
                    //判断空
                    curr = prev == null ? null : prev.next;
                    break;
                } else if (val.compareTo(curr.val) < 0) {
                    if (i <= k) {
                        if (up == null) {
                            up = new Node<>(val);
                        } else {
                            up.down = new Node<>(val);
                            up = up.down;
                        }
                        prev.next = up;
                        up.next = curr;
                    }
                    curr = prev.down;
                    prev = prev.down;
                    break;
                } else if (val.compareTo(curr.val) > 0) {
                    prev = curr;
                    curr = curr.next;
                } else {
                    return;
                }
            }
        }
    }
}
