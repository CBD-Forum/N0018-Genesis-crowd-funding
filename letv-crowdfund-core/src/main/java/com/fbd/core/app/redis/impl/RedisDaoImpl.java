package com.fbd.core.app.redis.impl;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.base.AbstractBaseRedisDao;


@Repository(value="redisDao")
public class RedisDaoImpl extends AbstractBaseRedisDao implements IRedisDao {

	 
	  private static String redisCode = "utf-8";

	    /**
	     * @param key
	     */
	    @SuppressWarnings("unchecked")
		public long del(final String... keys) {
	        return (Long) redisTemplate.execute(new RedisCallback() {
	            public Long doInRedis(RedisConnection connection) throws DataAccessException {
	                long result = 0;
	                for (int i = 0; i < keys.length; i++) {
	                    result = connection.del(keys[i].getBytes());
	                }
	                return result;
	            }
	        });
	    }

		
	    /**
	     * @param key
	     * @param value
	     * @param liveTime
	     */
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
	    public void set(final byte[] key, final byte[] value, final long liveTime) {
	        redisTemplate.execute(new RedisCallback() {
	            public Long doInRedis(RedisConnection connection) throws DataAccessException {
	                connection.set(key, value);
	                if (liveTime > 0) {
	                    connection.expire(key, liveTime);
	                }
	                return 1L;
	            }
	        });
	    }

		@Override
		public void set(String key, String value, long liveTime) {
			this.set(key.getBytes(), value.getBytes(), liveTime);
		}

		@Override
		public void set(String key, String value) {
			 this.set(key, value, 0L);
		}

		@Override
		public void set(byte[] key, byte[] value) {
			 this.set(key, value, 0L);
 		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public String get(final String key) {
			if(this.exists(key)){
				 return (String) redisTemplate.execute(new RedisCallback() {
			            public String doInRedis(RedisConnection connection) throws DataAccessException {
			                try {
			                    return new String(connection.get(key.getBytes()), redisCode);
			                } catch (UnsupportedEncodingException e) {
			                    e.printStackTrace();
			                }
			                return "";
			            }
			        });
			}else{
				return "";
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Set keys(String pattern) {
			 return redisTemplate.keys(pattern);
		}

		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public boolean exists(final String key) {
			 return (Boolean) redisTemplate.execute(new RedisCallback() {
		            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
		                return connection.exists(key.getBytes());
		            }
		        });
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public String flushDB() {
		  return (String) redisTemplate.execute(new RedisCallback() {
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
	                connection.flushDb();
	                return "ok";
	            }
	        });
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public long dbSize() {
		    return (Long) redisTemplate.execute(new RedisCallback() {
	            public Long doInRedis(RedisConnection connection) throws DataAccessException {
	                return connection.dbSize();
	            }
	        });
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public String ping() {
			return (String) redisTemplate.execute(new RedisCallback() {
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
	            	
	                return connection.ping();
	            }
	        });
		}

}
