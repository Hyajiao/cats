package com.kingyee.common.cache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.kingyee.common.util.PropertyConst;

/**
 * 缓存包装类。封装redis和cache
 * @author peihong
 *
 */
public class CacheFacade {
	
	private RedisUtil redis;
	//EHCache缓存
	private Cache cache;
	
	/**
	 * 给缓存赋值
	 * @param key
	 * @param value
	 */
	public <T extends Serializable> void setValue(String key, T value){
		if(PropertyConst.CACHE_TYPE.equals("redis")){
			redis.save(key, value);
		}else if(PropertyConst.CACHE_TYPE.equals("ehcache")){
			Element element = new Element(key, value);
			cache.put(element);
		}
	}
	
	/**
	 * 从缓存系统中取值
	 * @param key
	 */
	public <T extends Serializable> T getValue(String key){
        if(PropertyConst.CACHE_TYPE.equals("redis")){
            return redis.get(key);
        }else if(PropertyConst.CACHE_TYPE.equals("ehcache")){
            Element element = cache.get(key);
            if(null != element){
                return (T)element.getValue();
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 删除key的缓存
     * @param key
     */
    public <T extends Serializable> void del(String key){
        if(PropertyConst.CACHE_TYPE.equals("redis")){
            redis.del(key);
        }else if(PropertyConst.CACHE_TYPE.equals("ehcache")){
            cache.remove(key);
        }
    }


	public RedisUtil getRedis() {
		return redis;
	}
	public void setRedis(RedisUtil redis) {
		this.redis = redis;
	}
	public Cache getCache() {
		return cache;
	}
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
}
