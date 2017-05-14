/**
 * 
 */
package com.fbd.core.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 内存锁
 * 
 * @author
 * 
 */
public class LockUtil {

    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(LockUtil.class);
    /**
     * 单态
     */
    private static final LockUtil INSTANCE = new LockUtil();

    @SuppressWarnings("rawtypes")
    private static final ConcurrentMap MAP = new ConcurrentHashMap();

    /**
     * 构造
     */
    private LockUtil() {

    }

    public static synchronized LockUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 设置锁对象
     * 
     * @param key
     *            key
     * @param lockObject
     *            锁对象
     */
    @SuppressWarnings("unchecked")
    public void put(Object key, Object lockObject) {
        MAP.put(key, lockObject);
    }

    /**
     * 得到锁对象
     * 
     * @param key
     *            key
     * @return 对象
     */
    public Object get(Object key) {
        return MAP.get(key);
    }

    /**
     * 删除锁对象
     * 
     * @param key
     *            锁key
     */
    public void remove(Object key) {
        MAP.remove(key);
    }

    /**
     * 删除锁对象
     * 
     * @param key
     *            锁key
     * @param lockObject
     *            对象
     */
    public void remove(Object key, Object lockObject) {
        MAP.remove(key, lockObject);
    }

    /**
     * key是否存在
     * 
     * @param key
     *            key
     * @return 是否存在
     */
    public boolean containsKey(Object key) {
        return MAP.containsKey(key);
    }

    /**
     * value是否存在
     * 
     * @param value
     *            value
     * @return 是否存在
     */
    public boolean containsValue(Object value) {
        return MAP.containsValue(value);
    }
}
