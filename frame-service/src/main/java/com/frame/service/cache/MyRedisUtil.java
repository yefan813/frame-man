package com.frame.service.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.dao.util.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class MyRedisUtil extends RedisUtil {
	private static final Logger log = LoggerFactory.getLogger(MyRedisUtil.class);
	
	private ShardedJedisPool theShardedJedisPool;
	 /**
     * CMS系统redis前缀
     */
    public static final String REDIS_PREFIX_LANQIU="lanqiu:";
    
	public void setTheShardedJedisPool(ShardedJedisPool theShardedJedisPool) {
		this.theShardedJedisPool = theShardedJedisPool;
	}
	
	public void deleteByKey(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = theShardedJedisPool.getResource();
			shardedJedis.del(key);
		} catch (Exception e) {
			log.error("redis操作发生异常" , e);
		} finally {
			if (shardedJedis != null)
				theShardedJedisPool.returnResource(shardedJedis);
		}
	}
	
	public void batchDeleteByKey(String start) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = theShardedJedisPool.getResource();
			Collection<Jedis> coll = shardedJedis.getAllShards();
			for (Jedis jedis : coll) {
				Set<String> keys = jedis.keys(start + "*");
				if (keys.size() > 0) {
					for (String redisKey : keys) {
						shardedJedis.del(redisKey);
					}
				}
			}
		} catch (Exception e) {
			log.error("redis操作发生异常" , e);
		} finally {
			if (shardedJedis != null)
				theShardedJedisPool.returnResource(shardedJedis);
		}
	}
	
	private List<RedisObject> keys(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = theShardedJedisPool.getResource();
			List<RedisObject> results = new ArrayList<RedisObject>();
			Collection<Jedis> coll = shardedJedis.getAllShards();
			for (Jedis jedis : coll) {
				Set<String> keys = jedis.keys("*" + key + "*");
				if (keys.size() > 0) {
					for (String redisKey : keys) {
						try {
							String value = shardedJedis.get(redisKey);
							String server = shardedJedis.getShardInfo(redisKey).toString();
							RedisObject o = new RedisObject(null, redisKey, null, value, server);
							results.add(o);
						} catch (Exception e) {
							
							Set<String> hkeys = jedis.hkeys(redisKey);
							if (hkeys.size() > 0) {
								for (String hkey : hkeys) {
									String value = shardedJedis.hget(redisKey, hkey);
									String server = shardedJedis.getShardInfo(redisKey).toString();
									RedisObject o = new RedisObject(null, redisKey, hkey, value, server);
									results.add(o);
								}
							}
						}
					}
				}
			}
			return results;
		} catch (Exception e) {
			log.error("redis操作发生异常" , e);
			return null;
		} finally {
			if (shardedJedis != null)
				theShardedJedisPool.returnResource(shardedJedis);
		}
	}

	public String get(String key) {
		return super.get(key);
	}
	public List<RedisObject> getCacheWithLike(String key) {
		return keys(key);
	}
	
	public List<RedisObject> getMyCacheList(String key) {
		return keys(key);
	}
	
	
	
}
