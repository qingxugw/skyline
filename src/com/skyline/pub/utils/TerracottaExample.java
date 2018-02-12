package com.skyline.pub.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-8-23
 * Time: 下午10:00
 * To change this template use File | Settings | File Templates.
 */
public class TerracottaExample {

    CacheManager cacheManager = new CacheManager();
    public TerracottaExample() {
        Cache cache = cacheManager.getCache("SessionCache");
        int cacheSize = cache.getKeys().size();
        for (Object key : cache.getKeys()) {
            System.out.println("Key:" + key);
        }
    }
    public static void main(String[] args) throws Exception {
        new TerracottaExample();
    }
}
