package com.frame.service.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.frame.service.cache.MyRedisUtil;
import com.frame.service.cache.RedisObject;
import com.google.common.collect.Maps;

public class MyCacheUtil implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyCacheUtil.class);

	private static MyRedisUtil myRedisUtil;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		myRedisUtil = applicationContext.getBean("myRedisUtil", MyRedisUtil.class);
	}

	public static void setLimit(String key, Integer count, Integer limitTime) {
		if(StringUtils.isNotBlank(key)){
			key = key.replace(":", "#");
		}
		String cachekey = myRedisUtil.REDIS_PREFIX_LANQIU + key;
		try {
			if (count != null) {
				// 第3个参数，false，表示不设置缓存过期时间，永久有效
				myRedisUtil.setex(cachekey, limitTime,count + "");
				LOGGER.info("ActivityLuaVO对象缓存成功。");
			}
		} catch (Exception e) {
			LOGGER.error("setLimit写入缓存异常,", e);
		}
	}

	public static void incrLimit(String key, Integer step) {
		if(StringUtils.isNotBlank(key)){
			key = key.replace(":", "#");
		}
		String cachekey = myRedisUtil.REDIS_PREFIX_LANQIU + key;
		try {
			String value = myRedisUtil.get(cachekey);
			int count = 0;
			if (StringUtils.isNotBlank(value)) {
				count = Integer.parseInt(value);
			}
			count += step;
			myRedisUtil.incr(cachekey);

		} catch (Exception e) {
			LOGGER.error("incrLimit写入缓存异常", e);
		}
	}

	public static Map<String, String> getLimitValue(List<String> keyCollections) {
		Map<String, String> maps = Maps.newHashMap();
		if (CollectionUtils.isEmpty(keyCollections)) {
			return maps;
		}
		for (String string : keyCollections) {
			String newKey = null;
			if(StringUtils.isNotBlank(string)){
				newKey = string.replace(":", "#");
			}
			String key = myRedisUtil.REDIS_PREFIX_LANQIU + newKey;
			String value = myRedisUtil.get(key);
			if (StringUtils.isNotBlank(value)) {
				maps.put(string, value);
			}
		}
		return maps;
	}

	/**
	 * 将活动LUA对象写入缓存中
	 * 
	 * @param urlPath
	 * @param actLuaVO
	 */
	public static void setMatchStaticsHTML(Long matchId, String data) {
		String key = myRedisUtil.REDIS_PREFIX_LANQIU + matchId;
		try {
			if (data != null) {
				// 第3个参数，false，表示不设置缓存过期时间，永久有效
				myRedisUtil.set(key, data, false);
				LOGGER.info("ActivityLuaVO对象缓存成功。");
			}
		} catch (Exception e) {
			LOGGER.error("ActivityLuaVO写入缓存异常, urlPath=" + data, e);
		}
	}

	public static void clearMatchStaticsHTML(Long matchId) {
		try {
			String key = myRedisUtil.REDIS_PREFIX_LANQIU + matchId;
			LOGGER.info("清空活动【" + matchId + "】的统计数据。");
			myRedisUtil.deleteByKey(key);
		} catch (Exception e) {
			LOGGER.error("清空活动【" + matchId + "】的ActivityLuaVO数据-失败。", e);
		}
	}

	/**
	 * 获取模块原型缓存
	 * 
	 * @param activityId
	 * @return
	 */
	public static String getMatchStaticsHTML(Long matchId) {
		try {
			String key = myRedisUtil.REDIS_PREFIX_LANQIU + matchId;
			String value = myRedisUtil.get(key);
			if (StringUtils.isNotBlank(value)) {
				return value;
			}
		} catch (Exception e) {
			LOGGER.error("获取比赛统计缓存异常, matchId=" + matchId, e);
		}
		return null;
	}

	public static List<RedisObject> getCMSCacheList(String key) {
		List<RedisObject> list = null;
		try {
			list = myRedisUtil.getMyCacheList(key);
		} catch (Exception e) {
			LOGGER.error("得到cms 缓存key-失败。", e);
			return list;
		}
		return list;
	}

	public static boolean deleteByKey(String key) {
		boolean res = false;
		if (StringUtils.isEmpty(key)) {
			LOGGER.warn("调用deleteByKey传入数据错误");
			return false;
		}
		try {
			myRedisUtil.deleteByKey(key);
			res = true;
		} catch (Exception e) {
			LOGGER.error("清除redis缓存异常, key=" + key, e);
			return res;
		}
		return res;
	}
}
