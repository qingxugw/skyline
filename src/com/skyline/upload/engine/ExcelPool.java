package com.skyline.upload.engine;

import com.skyline.pub.utils.AppUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-17 上午11:13
 */
public class ExcelPool {

    public static final  String MIDDLESALECOUNT = "middleSaleCount";

    private static Map<String,Map<String,Integer>> pool = new ConcurrentHashMap<String, Map<String, Integer>>(16);

    private ExcelPool() {};

    public static Map getInstance(){
        return pool;
    }

}
