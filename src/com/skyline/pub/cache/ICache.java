package com.skyline.pub.cache;

import com.skyline.pub.services.IService;
import net.sf.ehcache.Cache;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-17
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public interface ICache extends IService{

    public void remove(String key);

    public void removeAll();

    public Cache getCache();

    public String getKeyPrefix();

}
