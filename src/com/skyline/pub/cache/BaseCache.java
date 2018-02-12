package com.skyline.pub.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;

/**
 *
 * User: skyine
 * Date: 2012-2-17
 * Time: 15:47:00
 */
public abstract class BaseCache implements ICache{

    private Cache cache = null;

    public void remove(String key) {
        getCache().remove(getKeyPrefix() + key);
    }

    public void removeAll() {
        try {
            for (Object o : getCache().getKeys()) {
                String key = (String) o;
                if (key.indexOf(getKeyPrefix()) == 0) {
                    getCache().remove(key);
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    protected void setCache(Cache cache) {
        this.cache = cache;
    }

    public String getKeyPrefix() {
        return null;
    }

    public Cache getCache() {
        return cache;
    }
}
