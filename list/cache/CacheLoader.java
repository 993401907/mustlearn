package list.cache;

@FunctionalInterface
public interface CacheLoader<K,V> {
    /**
     * 自定义加载数据方法
     */
    V laod(K k);
}
