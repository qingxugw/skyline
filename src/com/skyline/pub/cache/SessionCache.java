package com.skyline.pub.cache;

import com.skyline.base.acl.Session;
import com.skyline.pub.exception.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-17
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class SessionCache extends BaseCache{
    private static Logger logger = Logger.getLogger(SessionCache.class.getName());
    private CacheManager cacheManager = null;
    public String getKeyPrefix() {
        return IPrefix.Session;
    }

    private String getKey(String sessionId){
        return getKeyPrefix() + sessionId;
    }

    private boolean isSessionKey(String key,Serializable sessionId){
        String s = (String) sessionId;
        return key.startsWith(getKeyPrefix() + s);
    }

    /**
     * 注意缓存中可能存在多个sessionId的，
     * 所以删除的时候利用ehcache search 功能
     * 查询到所有session,然后删除掉
     * @param sessionId
     */
    public void removeIt(String sessionId){
        List keys = null;
        try {
            keys = getCache().getKeys();
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }

        if (keys == null) return;

        for (Iterator it = keys.iterator();it.hasNext();){
            String key = (String) it.next();

            if (isSessionKey(key,sessionId)) {
                getCache().remove(key);
            }
        }

    }


    public Session getSession(String sessionId){
        Element e = null;
        String key = getKey(sessionId);

        try {
            e = getCache().get(key);
        } catch (Exception e1) {
            throw new CacheException(e1);
        }

        if (e!=null)
            return (Session) e.getValue();
        else
            throw new CacheException("session("+sessionId+") is null.");
    }

    public Date getCreateTime(String sessionId){

        Element e = null;
        String key = getKey(sessionId);

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
        Element e = new Element(getKey(sessionId),object);
        getCache().put(e);
    }

    public Serializable get(String sessionId){
        return getCache().get(getKey(sessionId));
    }

    public void create() {

    }

    /**
     * 利用构造函数注入CacheManager
     */
    public SessionCache(CacheManager manager) {
        setCache(manager.getCache(ICacheName.CACHE_SESSION));
        logger.info("[SessionCache 服务启动]");
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
