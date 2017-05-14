package com.fbd.core.app.redis;

import java.util.Set;

public interface IRedisDao {
	
	
	 /**
     * 通过key删除
     * 
     * @param key
     */
    public long del(String... keys);
    
    /**
     * 添加key value 并且设置存活时间(byte)
     * 
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     * 
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    public void set(String key, String value, long liveTime);

    /**
     * 添加key value
     * 
     * @param key
     * @param value
     */
    public void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     * 
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     * 
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * 通过正则匹配keys
     * 
     * @param pattern
     * @return 
     * @return
     */
    public Set keys(String pattern);

    /**
     * 检查key是否已经存在
     * 
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     * 清空redis 所有数据
     * 
     * @return
     */
    public String flushDB();

    /**
     * 查看redis里有多少数据
     */
    public long dbSize();

    /**
     * 检查是否连接成功
     * 
     * @return
     */
    public String ping();

}
