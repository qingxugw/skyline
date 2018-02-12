package com.skyline.pub.exception;

import com.skyline.pub.utils.enums.BaseEnum;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * User: skyline
 * Date: 2012-08-28
 * Time: 14:59:51
 */
public class AppException extends RuntimeException {
    private static final String DEFAULT_ERROR_MSG = "未知异常";
    private Map parameters = new HashMap();
    public AppException(String msg) {
        super(msg);
    }

    public AppException(BaseEnum enu) {
        super(enu.getValue());
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public Map getParameters(){
        return this.parameters;
    }

    public void addParameter(String key,Object obj){
        this.parameters.put(key, obj);
    }

    public String getFormatMessage() {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append("\n");
        sb.append("当前异常发生时间为：" + sd.format(new Date()));
        sb.append("\n");
        sb.append("异常消息：");
        sb.append(super.getMessage());
        return sb.toString();
    }
    public String getMessage() {
        return super.getMessage();
    }
}
