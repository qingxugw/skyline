package com.skyline.upload.engine;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-4-28 上午10:30
 */

import org.apache.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 提供线程池工具类
 */
public class ThreadPools {
    private static final Logger logger = Logger.getLogger(ThreadPools.class);

    private static ThreadPoolExecutor daemonPool = null;//固定线程池
    private static ThreadPoolExecutor insertPool = null;//数据保存线程池

    /**
     *
     * new ThreadPoolExecutor(
     int corePoolSize 核心保留的线程池大小
     10,
     int maximumPoolSize 线程池的最大大小
     15,
     int keepAliveTime 空闲线程结束的超时时间
     30,
     unit 线程结束超时时间的单位  如 日、时、分、秒等
     TimeUnit.SECONDS,
     使用有界队列,防止耗尽系统资源
     new ArrayBlockingQueue<Runnable>(10),
     线程池满后拒绝提交,抛出异常,在提交线程中捕获异常即可
     new ThreadPoolExecutor.AbortPolicy()
     );
     * @return
     */
    public synchronized static ThreadPoolExecutor daemonThreadPool(){
        if(daemonPool == null){
            logger.debug("固定线程池启动......");
            daemonPool = new ThreadPoolExecutor(3, 9, 10,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(9),
                    new ThreadPoolExecutor.AbortPolicy()
            );
        }
        return daemonPool;
    }

    public synchronized static ThreadPoolExecutor insertThreadPool(){
        if(insertPool == null){
            logger.debug("数据处理线程池启动......");
            insertPool = new ThreadPoolExecutor(15, 20, 10,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(20),
                    new ThreadPoolExecutor.AbortPolicy()
            );
        }
        return insertPool;
    }
}
