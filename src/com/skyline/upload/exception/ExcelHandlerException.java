package com.skyline.upload.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Excel处理异常类
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 上午8:40
 */
public class ExcelHandlerException extends RuntimeException {

    public ExcelHandlerException() {
    }

    public ExcelHandlerException(String message) {
        super(message);
    }

    public ExcelHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelHandlerException(Throwable cause) {
        super(cause);
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

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
