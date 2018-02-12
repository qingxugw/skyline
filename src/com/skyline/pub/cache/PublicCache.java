package com.skyline.pub.cache;

import com.skyline.base.acl.Session;
import com.skyline.pub.exception.CacheException;
import com.skyline.pub.utils.AppUtils;
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
public class PublicCache extends BaseCache{
    private static Logger logger = Logger.getLogger(PublicCache.class.getName());
    private CacheManager cacheManager = null;
    public String getKeyPrefix() {
        return IPrefix.Public + getSessionId();
    }

    public String getSessionId(){
        return AppUtils.getHttpSession().getId()+"-";
    }

    private String getKey(String elementKey){
        return getKeyPrefix() + elementKey;
    }

    private boolean isPublicCacheKey(String key,Serializable elementKey){
        String s = (String) elementKey;
        return key.startsWith(getKeyPrefix() + s);
    }

    /**
     * 注意缓存中可能存在多个elementKey的，
     * 所以删除的时候利用ehcache search 功能
     * 查询到所有session,然后删除掉
     * @param elementKey
     */
    public void removeIt(String elementKey){
        List keys = null;
        try {
            keys = getCache().getKeys();
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }

        if (keys == null) return;

        for (Iterator it = keys.iterator();it.hasNext();){
            String key = (String) it.next();

            if (isPublicCacheKey(key, elementKey)) {
                getCache().remove(key);
            }
        }

    }


    public Session getSession(String elementKey){
        Element e = null;
        String key = getKey(elementKey);

        try {
            e = getCache().get(key);
        } catch (Exception e1) {
            throw new CacheException(e1);
        }

        if (e!=null)
            return (Session) e.getValue();
        else
            throw new CacheException("PubliCache("+elementKey+") is null.");
    }

    public Date getCreateTime(String elementKey){

        Element e = null;
        String key = getKey(elementKey);

        try {
            e = getCache().get(key);
        } catch (Exception e1) {
            throw new CacheException(e1);
        }

        if (e!=null){
            return new Date(e.getCreationTime());
        }
        else
            throw new CacheException("PublicCache ("+elementKey+") is null.");
    }

    public void put(String elementKey,Object value){
        Element e = new Element(getKey(elementKey),value);
        getCache().put(e);
    }

    public Object get(String elementKey){
        return getCache().get(getKey(elementKey));
    }

    public void create() {

    }

    /**
     * 利用构造函数注入CacheManager
     */
    public PublicCache(CacheManager manager) {
        setCache(manager.getCache(ICacheName.CACHE_PUBLIC));
        logger.info("[PublicCache 服务启动]");
    }

    public int getPublicCacheCount() {
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
