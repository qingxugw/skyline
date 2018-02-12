package com.skyline.upload.engine;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午4:07
 */
public class ReadUtil {

    public static void main(String[] args) {
        String fileName = "D:/Teradata Central Team-笔试题目-v13.04.xlsx";
        long start = System.currentTimeMillis();
//        ThreadPools.daemonThreadPool().submit(new ExcelThread(fileName,2));
//        ThreadPools.daemonThreadPool().submit(new ExcelThread(fileName,3));
//        ThreadPools.daemonThreadPool().submit(new ExcelThread(fileName,4));
        int pool = ThreadPools.daemonThreadPool().getPoolSize();
        while (pool !=0){
            pool = ThreadPools.daemonThreadPool().getActiveCount();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ThreadPools.daemonThreadPool().shutdownNow();
        long end = System.currentTimeMillis();
    }
}
