package com.lzw.util;

import com.lzw.config.Constant;
import com.lzw.config.cache.CacheManager;
import com.lzw.config.cache.RedisHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheUtil {
	private static Logger logger = LogManager.getLogger(CacheUtil.class);
	private static CacheManager cacheManager;
	//这里cacheManager既用spring管理又用静态管理，用spring可以获取到，用静态变量也可以获取到
	//如果只是spring管理，需要注入才能获取到对象； 用静态管理，用类获取到，用静态了就要避免多线程用双重检查锁
	@Bean
	public CacheManager setRedisHelper() {
		cacheManager = getRedisHelper();
		return cacheManager;
	}

	public static CacheManager getRedisHelper() {
		if (cacheManager == null) {
			synchronized (CacheUtil.class) {
				if (cacheManager == null) {
					cacheManager = new RedisHelper();
				}
			}
		}
		return cacheManager;
	}

	/** 获取锁 */
	public static boolean getLock(String key) {
		try {
			if (!getRedisHelper().exists(key)) {
				synchronized (CacheUtil.class) {
					if (!getRedisHelper().exists(key)) {
						if (getRedisHelper().setnx(key, String.valueOf(System.currentTimeMillis()))) {
							return true;
						}
					}
				}
			}
			int expires = 1000 * 60 * 3;
			String currentValue = (String) getRedisHelper().get(key);
			if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis() - expires) {
				unlock(key);
				return getLock(key);
			}
			return false;
		} catch (Exception e) {
			logger.error(Constant.Exception_Head, e);
			return true;
		}
	}

	public static void unlock(String key) {
		getRedisHelper().unlock(key);
	}
}
