package com.kingyee.common.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisUtil {
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	/**
	 * 保存key和value，默认缓存时间86400秒（一天）
	 * @param key
	 * @param value
	 */
	public <T extends Serializable> void save(final String key, final T value) {
		redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				ValueOperations<Serializable, Serializable> tvo = redisTemplate.opsForValue();
				tvo.set(key, value, 86400, TimeUnit.SECONDS);
//				BoundValueOperations<Serializable, Serializable> bvo = redisTemplate.boundValueOps(key);
//				bvo.set(t);
//				connection.set(
//						redisTemplate.getStringSerializer().serialize(key),
//						redisTemplate.getStringSerializer().serialize(t));
				return null;
			}
		});
	}
	
	/**
	 * 保存key和value
	 * @param key
	 * @param value
	 * @param timeout 缓存时间，单位为秒
	 */
	public <T extends Serializable> void save(final String key, final T value, final long timeout) {
		redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				ValueOperations<Serializable, Serializable> tvo = redisTemplate.opsForValue();
				tvo.set(key, value, timeout, TimeUnit.SECONDS);
//				BoundValueOperations<Serializable, Serializable> bvo = redisTemplate.boundValueOps(key);
//				bvo.set(t);
//				connection.set(
//						redisTemplate.getStringSerializer().serialize(key),
//						redisTemplate.getStringSerializer().serialize(t));
				return null;
			}
		});
	}

	/**
	 * 通过key获取 <br>
	 * ------------------------------<br>
	 * 
	 * @param keyid
	 * @return
	 */
	public <T extends Serializable> T get(final String keyid) {
		T result = (T) redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				
				ValueOperations<Serializable, Serializable> tvo = redisTemplate.opsForValue();
				T t = (T) tvo.get(keyid);
				
//				RedisSerializer<String> serializer = getRedisSerializer();
//				byte[] key = serializer.serialize(keyid);
//				byte[] value = connection.get(key);
//				if (value == null) {
//					return null;
//				}
//				T t = (T) serializer.deserialize(value);
				return t;
			}
		});
		return result;
	}


    /**
     * 删除key的缓存
     * @param key
     */
    public <T extends Serializable> void del(final String key) {
        redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
            redisTemplate.delete(key);
            return null;
            }
        });
    }

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取 RedisSerializer <br>
	 * ------------------------------<br>
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}
}
