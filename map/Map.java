package map;

/**
 * @author wulizi
 */
public interface Map<K extends Comparable<K>, V>{
    /**
     * put
     * @param key .
     * @param val .
     */
    void put(K key, V val);

    /**
     * get
     * @param key .
     * @return V .
     */
    V get(K key);

    /**
     * isEmpty
     * @return .
     */
    boolean isEmpty();

    /**
     * size
     * @return .
     */
    int size();

    /**
     * delete
     * @param key .
     */
    void delete(K key);

    /**
     * 获取key列表
     * @return keys
     */
    Iterable<K> keys();
}
