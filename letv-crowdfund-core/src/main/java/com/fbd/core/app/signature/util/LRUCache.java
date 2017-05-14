package com.fbd.core.app.signature.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private int cacheSize;
    private static final float hashTableLoadFactor = 0.75f;
    private LinkedHashMap<K, V> map;

    public LRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        int hashTableCapacity = (int) Math.ceil(cacheSize / hashTableLoadFactor) + 1;
        map = new LinkedHashMap<K, V>(hashTableCapacity, hashTableLoadFactor, true) {
            private static final long serialVersionUID = 1;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.cacheSize;
            }
        };
    }

    public synchronized V remove(K key) {
        return map.remove(key);
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized int usedEntries() {
        return map.size();
    }
}
