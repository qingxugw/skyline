package com.skyline.pub.cache;

import net.sf.ehcache.CacheManager;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-17
 * Time: 下午3:49
 * To change this template use File | Settings | File Templates.
 */
public class TerracottaCacheManager {
    private static Logger logger = Logger.getLogger(TerracottaCacheManager.class.getName());

    private CacheManager cacheManager = null;

    public TerracottaCacheManager(CacheManager cacheManager) {
        this.cacheManager =  cacheManager;
        logger.info("[ Cache 服务开始创建. ]");
    }

    public String getSeprateKey(String orgkey,String prefix){
        return orgkey.substring(prefix.length());
    }
}
