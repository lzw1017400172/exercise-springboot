package com.lzw.config.cache;

import java.io.Serializable;
import java.util.Set;

public interface CacheManager {
	public Object get(final String key);
	
	public int getSetKeyOne(final String key);
	
	public Object getFinal(final String key);

	public Set<Object> getAll(final String pattern);
	
	public void getSetKeyAll(final String pattern);

	public void set(final String key, final Serializable value, int seconds);

	public void set(final String key, final Serializable value);
	
	public void setForEver(final String key, final Serializable value);

	public Boolean exists(final String key);

	public void del(final String key);

	public void delAll(final String pattern);

	public String type(final String key);

	public Boolean expire(final String key, final int seconds);

	public Boolean expireAt(final String key, final long unixTime);

	public Long ttl(final String key);
	
	public boolean clear(final String key);

	public Object getSet(final String key, final Serializable value);
	
	public boolean setnx(final String key, final Serializable value);

	public void unlock(String key);

	public void hset(String key, String field, String value);

	public Object hget(String key, String field);

	public void hdel(String key, String field);

	public void setZero(String string, String string2);
}
