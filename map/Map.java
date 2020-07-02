package map;

/**
 * @author wulizi
 */
public interface Map<Key extends Comparable<Key>, Value> {

    /**
     * 将键值队存入表中
     * @param key 键
     * @param val 值
     */
    void put(Key key, Value val);

    /**
     * 通过键获取值
     * @param key 键
     * @return 值
     */
    Value get(Key key);

    /**
     * 从表中删除key和对应的值
     * @param key 键
     */
    void delete(Key key);

    /**
     * 是否包含某个值
     * @param key 键
     * @return 是
     */
    boolean contains(Key key);

    /**
     * 符号表是否为空
     * @return .
     */
    boolean isEmpty();

    /**
     * 符号表的数量
     * @return .
     */
    int size();
    /**
     * 最小的键
     * @return .
     */
    Key min();

    /**
     * 最大的键
     * @return .
     */
    Key max();

    /**
     * 小于等于该键的键
     * @param key 键
     * @return key
     */
    Key floor(Key key);

    /**
     * 大于等于该键的键
     * @param key 键
     * @return key
     */
    Key ceiling(Key key);

    /**
     * 小于key的键的数量
     * @param key 键
     * @return .
     */
    int rank(Key key);

    /**
     * 排名为k的键
     * @param k 排名
     * @return 键
     */
    Key select(int k);

    /**
     * 删除最小的键
     */
    void deleteMin();

    /**
     * 删除最大的键
     */
    void deleteMax();
}
