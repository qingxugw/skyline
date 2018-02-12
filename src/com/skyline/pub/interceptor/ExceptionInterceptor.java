package com.skyline.pub.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.exception.CacheException;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.SysUtilities;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: skyline
 * Date: 12-10-31
 * Time: 下午12:59
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionInterceptor extends AbstractInterceptor {
    static Logger logger = Logger.getLogger(ExceptionInterceptor.class);
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        String action = actionInvocation.getAction().getClass().getName();
        if(!SysUtilities.getInstance().getACL_MAP().containsKey(action)){
            //不包含在ACL访问列表里面的才去判断是否登录
            try{
                AppUtils.getSession();
            }catch (CacheException cache){
                cache.printStackTrace();
                return Action.LOGIN;
            }
        }
        try{
            return actionInvocation.invoke();
        }catch (AppException app){
            app.printStackTrace();
            return "test-error";
        }catch (Exception e){
            e.printStackTrace();
            return "test-error";
        }finally {

        }
    }
}
