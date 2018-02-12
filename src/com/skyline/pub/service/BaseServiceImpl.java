package com.skyline.pub.service;

import com.skyline.pub.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-7-24
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class BaseServiceImpl implements BaseService {
    protected final Log log = LogFactory.getLog(getClass());

    protected final Logger logger = Logger.getLogger(getClass());

    protected DAO baseDAO = null;

    public void setBaseDAO(DAO dao) {
        this.baseDAO = dao;
    }

    public <T> T loadObject(Class<T> clazz, Serializable id) {
        return baseDAO.loadObject(clazz,id);
    }

    public <T> T getObject(Class<T> clazz, Serializable id) {
        return baseDAO.getObject(clazz,id);
    }

    public <T> T getObject(T o) {
        return baseDAO.getObject(o);
    }

    public void saveObject(Object o) {
        baseDAO.saveObject(o);
    }

    public void saveCollection(Collection collection) {
        baseDAO.saveCollection(collection);
    }

    public void updateObject(Object o) {
        baseDAO.updateObject(o);
    }

    public void deleteObject(Object o) {
        baseDAO.deleteObject(o);
    }

    public void deleteCollection(Collection collection) {
        baseDAO.deleteCollection(collection);
    }

    public <T> List<T> findByExample(T o) {
        return baseDAO.findByExample(o);
    }

    public <T> List<T> findByHQL(Class<T> clazz, Object... param) {
        return baseDAO.findByHQL(clazz,param);
    }

    public <T> List<T> findByHQL(Class<T> clazz, Integer start, Integer limit, Object... param) {
        return baseDAO.findByHQL(clazz,start,limit,param);
    }

    public <T> List<T> findByHQL(Class<T> clazz, Map orderBy, Object... param) {
        return baseDAO.findByHQL(clazz,orderBy,param);
    }

    public <T> List<T> findByHQL(Class<T> clazz, Integer start, Integer limit, Map orderBy, Object... param) {
        return baseDAO.findByHQL(clazz,start,limit,orderBy,param);
    }

    public Integer findCountByHQL(Class clazz, Object... param){
        return baseDAO.findCountByHQL(clazz,param);
    }

    //组装map 对应 key,value,key,value,key,value...类型
    protected HashMap<String,Object> assemblyMap(Object... param){
        HashMap<String,Object> map = new HashMap<String,Object>();
        for(int i = 0 ; i < param.length ; i += 2){
            map.put(param[i].toString(),param[ i + 1 ]);
        }
        return map;
    }
}
