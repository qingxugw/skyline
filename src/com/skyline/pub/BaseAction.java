package com.skyline.pub;

import javax.servlet.http.HttpServletRequest;

import com.skyline.base.acl.Session;
import com.skyline.pub.cache.SessionCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import java.util.HashMap;
import java.util.Map;

public class BaseAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;

    public static final String LIST ="list";
    public static final String RELIST = "relist";
    public static final String SHOWMSG = "msg";
    public static final String ENCODING = "UTF-8";

    protected HttpServletRequest request;

    private String msg;
    private String url;
    public Map<String,Object> resultMap = new HashMap<String,Object>();
    /**
     * 获取SessionCache
     */
    protected SessionCache sessionCache;
    /**
     * 获取Session类
     */
//    protected Session session;

    public void setSessionCache(SessionCache sessionCache) {
        this.sessionCache = sessionCache;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    protected final Log logger = LogFactory.getLog(getClass());

//    public void setSession(Session session) {
//        this.session = session;
//    }

    public Session getSession() {
        return sessionCache.getSession(request.getSession().getId());
    }
}
