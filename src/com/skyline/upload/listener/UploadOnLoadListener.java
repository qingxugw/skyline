package com.skyline.upload.listener;

import com.skyline.upload.engine.ThreadPools;
import org.springframework.beans.factory.InitializingBean;

/**
 * 这个类可以注入一些 dao 工具类
 * 在 afterPropertiesSet 方法中执行数据库连接级别的操作
 * 不过 该类目前用于启动线程池或者队列
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-12 下午7:41
 */
public class UploadOnLoadListener implements InitializingBean {

    public void close(){
        ThreadPools.daemonThreadPool().shutdownNow();
        ThreadPools.insertThreadPool().shutdownNow();
    }
    public void afterPropertiesSet() throws Exception {
        ThreadPools.daemonThreadPool();
        ThreadPools.insertThreadPool();
    }
}
