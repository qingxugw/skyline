package com.skyline.pub.exception;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * User: skyline
 * Date: 2012-2-17
 * Time: 16:25:51
 */
public class CacheException extends RuntimeException {
    private static final String DEFAULT_ERROR_MSG = "未知异常";
    private Map parameters = new HashMap();
    public CacheException(String msg) {
        super(msg);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }

    public CacheException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public Map getParameters(){
        return this.parameters;
    }

    public void addParameter(String key,Object obj){
        this.parameters.put(key, obj);
    }

    public String getMessage() {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append("\r");
        sb.append("当前异常发生时间为：" + sd.format(new Date()));
        sb.append("\r");
        sb.append("异常消息：");
        sb.append(super.getMessage());
        return sb.toString();
    }
}
