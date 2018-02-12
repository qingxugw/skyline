package com.skyline.base.acl;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdRole;
import com.skyline.base.domain.TdUser;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 2012-02-17
 * Time: 17:00:39
 * To change this template use File | Settings | File Templates.
 */
public class Session implements Serializable{
    private static Logger logger = Logger.getLogger(Session.class.getName());
    private TdUser user;             //用户对象
    private long lastAccessTime;  //最后一次登录时间
    private String ip;             //用户登录IP
    private Set<TdRole> roleSet;   //用户所拥有的角色列表
    private Set<TdOperation> operationSet; //用户所拥有的菜单资源列表
    private Set<TdOperation> tabList;//用户拥有的顶级菜单资源列表

    public Set<TdRole> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<TdRole> roleSet) {
        this.roleSet = roleSet;
    }

    public Set<TdOperation> getTabList() {
        return tabList;
    }

    public void setTabList(Set<TdOperation> tabList) {
        this.tabList = tabList;
    }

    public Set<TdOperation> getOperationSet() {
        return operationSet;
    }

    public void setOperationSet(Set<TdOperation> operationSet) {
        this.operationSet = operationSet;
    }

    public Session(){
        this.lastAccessTime = System.currentTimeMillis();
    }

    protected void finalize() {
        //logger.debug("Session 释放11");
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void refresh(){
        this.lastAccessTime = System.currentTimeMillis();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public TdUser getUser() {
        return user;
    }

    public void setUser(TdUser user) {
        this.user = user;
    }
}
