package com.skyline.pub.utils;

import com.skyline.base.acl.Session;
import com.skyline.base.domain.TdUser;
import com.skyline.pub.cache.PublicCache;
import com.skyline.pub.cache.SessionCache;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.utils.enums.LogicEnumException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-8-29
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public class AppUtils {
    public AppUtils() {

    }
    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();
    private static ThreadLocal<ServletContext> servletContextThreadLocal = new ThreadLocal<ServletContext>();
    private static ApplicationContext ctx = null;
    private static SessionCache sessionCache = null;
    private static PublicCache publicCache = null;
    public static void setRequest(HttpServletRequest request){
        requestThreadLocal.set(request);
    }

    public static void setResponse(HttpServletResponse response){
        responseThreadLocal.set(response);
    }

    public static void setServletContext(ServletContext context){
        servletContextThreadLocal.set(context);
    }

    public static void removeThreadLocal(){
        requestThreadLocal.remove();
        responseThreadLocal.remove();
        servletContextThreadLocal.remove();
    }
    /**
     * 获取request
     * @return HttpRequest
     */
    public static HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    /**
     * 获取response
     * @return HttpResponse
     */
    public static HttpServletResponse getResponse() {
        return responseThreadLocal.get();
    }

    /**
     * 获取session
     * @return HttpSession
     */
    public static HttpSession getHttpSession() {
        return requestThreadLocal.get().getSession();
    }

    /**
     * 获取用户信息session 类
     * @return
     */
    public static Session getSession() {
        return getSessionCache().getSession(getHttpSession().getId());
    }

    /**
     * sessionCache 用来存储用户信息的缓存
     * @return
     */
    public static SessionCache getSessionCache(){
        if(sessionCache == null){
            return getBean(SessionCache.class);
        }
        return sessionCache;
    }

    /**
     * publicCache 用来存储用户信息的缓存
     * @return
     */
    public static PublicCache getPublicCache(){
        if(publicCache == null){
            return getBean(PublicCache.class);
        }
        return publicCache;
    }

    /**
     * 把序列化后的数据存入sessionCache
     * @param key
     * @param value
     */
    public static void setPublicCacheAttribute(String key,Object value){
        getPublicCache().put(key,value);
    }

    /**
     * 把序列化后的数据存入sessionCache
     * @param key
     */
    public static Object  getPublicCacheAttribute(String key){
        return getPublicCache().get(key);
    }

    /**
     * 把序列化后的数据存入sessionCache
     * @param key
     * @param value
     */
    public static void setSessionCacheAttribute(String key,Serializable value){
        getSessionCache().put(key,value);
    }
    /**
     * 向session中
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key,Object value){
        requestThreadLocal.get().getSession().setAttribute(key,value);
    }

    /**
     * 获取spring bean的静态方法
     * @param clazz Class
     * @return object bean
     */
    public static <T> T getBean(Class<T> clazz){
        if(ctx == null){
            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextThreadLocal.get());
        }
        return ctx.getBean(clazz);
    }

    /**
     * 获取当前操作用户
     * @return currentOperator
     */
    public static TdUser getCurrentUser(){
        Object o = getSession().getUser();
        if(o == null){
            throw new AppException(LogicEnumException.当前用户会话信息已失效);
        }
        return (TdUser)o;
    }

}
