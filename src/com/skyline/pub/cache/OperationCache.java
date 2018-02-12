package com.skyline.pub.cache;

import com.skyline.pub.exception.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-17
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class OperationCache extends BaseCache{
    private static Logger logger = Logger.getLogger(OperationCache.class.getName());
    private CacheManager cacheManager = null;
    public String getKeyPrefix() {
        return IPrefix.Operation;
    }
    public Date getCreateTime(String sessionId){

        Element e = null;
        String key = getKeyPrefix();

        try {
            e = getCache().get(key);
        } catch (Exception e1) {
            throw new CacheException(e1);
        }

        if (e!=null){
            return new Date(e.getCreationTime());
        }
        else
            throw new CacheException("session("+sessionId+") is null.");
    }

    public void put(String sessionId,Serializable object){
        Element e = new Element(getKeyPrefix(),object);
        getCache().put(e);
    }

    public Serializable get(String sessionId){
        return getCache().get(getKeyPrefix());
    }

    public void create() {

    }

    /**
     * 利用构造函数注入CacheManager
     */
    public OperationCache(CacheManager manager) {
        setCache(manager.getCache(ICacheName.CACHE_OPERATION));
        logger.info("[OperationCache 服务启动]");
    }

    public int getSessionCount() {
        List keys = null;
        try {
            keys = this.getCache().getKeys();
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
        if (keys == null) {
            return 0;
        }
        return keys.size();
    }
}
