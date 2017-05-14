package com.fbd.core.base;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractBaseRedisDao<K extends Serializable, V extends Serializable> {

	@Resource  
    protected RedisTemplate redisTemplate;  
  
    public void setRedisTemplate(RedisTemplate redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    }  
}
