package list.cache;

import list.LinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wulizi
 */
public class LRUCache<K, V> {
    private final LinkedList<K> keyList = new LinkedList<>();
    private final Map<K, V> cache;
    private final CacheLoader<K, V> cacheLoader;
    private final int maxCapacity;

    public LRUCache(int maxCapacity, CacheLoader<K, V> cacheLoader) {
        this.cacheLoader = cacheLoader;
        this.maxCapacity = maxCapacity;
        this.cache = new HashMap<>(maxCapacity);
    }

    public void put(K key, V val) {
        if (keyList.size() >= maxCapacity) {
            K oldKey = keyList.removeFirst();
            cache.remove(oldKey);
        }
        if (keyList.contains(key)) {
            keyList.remove(key);
        }
        keyList.add(key);
        cache.put(key, val);
    }

    public V get(K key) {
        V value;
        if (keyList.contains(key)) {
            value = cache.get(key);
            keyList.remove(key);
            keyList.add(key);
        }else{
            value = cacheLoader.laod(key);
            this.put(key,value);
        }
        return value;
    }

    @Override
    public String toString() {
        return this.keyList.toString();
    }
}
