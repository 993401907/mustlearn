package map;


import list.Queue;

//链表结构
public class SequentialSearchST<Key, Value> {
    private int N;
    private Node first;

    private class Node {
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }


    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key){
        return get(key)!=null;
    }
    public void put(Key key,Value value){
        if (value==null){
            delete(key);
            return;
        }
        for (Node x = first;x!=null;x= x.next){
            if (key.equals(x.key)){
                x.value = value;
                return;
            }
        }
        first = new Node(key,value,first);
        N++;
    }
    public Value get(Key key){
        for (Node x = first;x!=null;x=x.next){
            if (key.equals(x.key)){
                return x.value;
            }
        }
        return null;
    }
    public void delete(Key key) {
        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            N--;
            return x.next;
        }
        delete(x.next, key);
        return x;
    }

    public Iterable<Key> keys()  {
        Queue<Key> queue = new Queue<>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }
}
