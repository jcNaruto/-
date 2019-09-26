package com.jc.laboratory.util;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * jedis Stand-alone client
 * @author jc
 *
 */
public class JedisClientPool implements JedisClient {

	@Autowired
	private JedisPool jedisPool;
	
	
	
	/**
	 * set a new key to redis that you can set a Expired time (second)
	 * if you pass a exit key i will reset the time
	 * @param key
	 * @param value
	 * @param expireSecond
	 */
	public void save(String key, String value, Long expireSecond) {
		Jedis jedis = jedisPool.getResource();
	    boolean keyExist = jedis.exists(key);
	    if (keyExist) {
	    	jedis.del(key);
	    }
	 
	    // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
	    jedis.set(key, value, "NX", "EX", expireSecond);
	    jedis.close();
	}

	

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	@Override
	public Boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

	@Override
	public Long hdel(String key, String... field) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key, field);
		jedis.close();
		return result;
	}

}
