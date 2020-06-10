package list;

/**
 * @author wulizi
 * list接口
 */
public interface List<E>{
    /**
     * 获取元素
     * @param idx 指定位置
     * @return 节点
     */
    E get(int idx);

    /**
     * 从最后添加一个元素
     * @param value 值
     */
    void add(E value);

    /**
     * 指定位置添加一个元素
     * @param idx 位置
     * @param value 元素
     */
    void add(int idx, E value);

    /**
     * 设置指定位置的元素值
     * @param idx 位置
     * @param value 元素
     */
    void set(int idx, E value);
    /**
     * 获取list长度
     * @return 长度
     */
    int size();

    /**
     * 删除指定位置的元素
     * @param idx 位置
     * @return 旧元素
     */
    E remove(int idx);
    /**
     * 删除指定值相等的元素
     * @param val 值
     */
    void remove(E val);

    /**
     * 返回元素第一次出现的位置
     * @param val 元素
     * @return 位置
     */
    int indexOf(E val);

    /**
     * 是否为空
     * @return .
     */
    boolean isEmpty();

    /**
     * 该元素是否存在list中
     * @param val 元素
     * @return .
     */
    boolean contains(E val);


}
