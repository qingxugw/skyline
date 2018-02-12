package com.skyline.pub.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.skyline.pub.exception.AppException;

/**
 * Created with IntelliJ IDEA.
 * User: skyline
 * Date: 12-10-31
 * Time: 下午1:28
 * To change this template use File | Settings | File Templates.
 */
public class AjaxExceptionInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        try{
            return actionInvocation.invoke();
        }catch (AppException app){
            app.printStackTrace();
            return "ajaxError";
        }catch (Exception e){
            e.printStackTrace();
            return "ajaxError";
        }finally {

        }
    }
}
