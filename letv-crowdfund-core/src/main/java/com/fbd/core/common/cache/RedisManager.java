package com.fbd.core.common.cache;

import java.util.Map;
import org.springframework.data.redis.core.RedisTemplate;
import com.fbd.core.util.spring.SpringUtil;

@SuppressWarnings("all")
public class RedisManager {
    /**
     * memcached客户端单例
     * 
     * 
     */
    private static RedisTemplate<String, Map<String, Object>> template = (RedisTemplate<String, Map<String, Object>>) SpringUtil.getBean("redisTemplate");

    public static void add(String key, Map<String, Object> value) {
         template.opsForHash().putAll(key, value);
    }
    
    public static Object get(String key, String hashKey) {
        return template.opsForHash().get(key, hashKey);
   }
}
