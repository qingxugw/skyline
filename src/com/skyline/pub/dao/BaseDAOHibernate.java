package com.skyline.pub.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.*;

/**
 *
 *提供对hibernateDAO的公共支持
 * @author skyline
 */
public class BaseDAOHibernate extends HibernateDaoSupport implements DAO {

    protected final Log log = LogFactory.getLog(getClass());

    protected final Logger logger = Logger.getLogger(getClass());

    public <T> T loadObject(Class<T> clazz, Serializable id){
        return getHibernateTemplate().load(clazz,id);
    }

    public <T> T getObject(Class<T> clazz, Serializable id) {
        return getHibernateTemplate().get(clazz,id);
    }

    public <T> T getObject(T o) {
        List<T> list = findByExample(o);
        if(list == null || list.isEmpty() || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    public void saveObject(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    public void saveCollection(Collection collection){
        getHibernateTemplate().saveOrUpdateAll(collection);
    }

    public void updateObject(Object o) {
        getHibernateTemplate().update(o);
    }

    public void deleteObject(Object o) {
        getHibernateTemplate().delete(o);
    }

    public void deleteCollection(Collection collection) {
        getHibernateTemplate().deleteAll(collection);
    }

    public <T> List<T> findByExample(T o) {
        return getHibernateTemplate().findByExample(o);
    }

    public <T> List<T> findByHQL(Class<T> clazz, Object... param) {
        StringBuilder hql = new StringBuilder("from " + clazz.getName() + " t where 1=1 ");
        for(int i = 0 ; i < param.length ; i += 3){
            if(param[i + 2] != null && StringUtils.isNotBlank(param[i + 2].toString())){
                hql.append(" and t.").append(param[i]).append(" ").append(param[i + 1]).append(" ?");
            }
        }
        Session session = getSession();
        Query query = session.createQuery(hql.toString());
        DBHelper.createQuery(query,param);
        return query.list();
    }

    public <T> List<T> findByHQL(Class<T> clazz, Integer start, Integer limit, Object... param) {
        if(start == null || limit == null){
            return findByHQL(clazz,param);
        }
        StringBuilder hql = new StringBuilder("from " + clazz.getName() + " t where 1=1 ");
        for(int i = 0 ; i < param.length ; i += 3){
            if(param[i + 2] != null && StringUtils.isNotBlank(param[i + 2].toString())){
                hql.append(" and t.").append(param[i]).append(" ").append(param[i + 1]).append(" ?");
            }
        }
        Session session = getSession();
        Query query = session.createQuery(hql.toString());
        DBHelper.createQuery(query,param);
        return query.setFirstResult(start).setMaxResults(limit).list();
    }

    public <T> List<T> findByHQL(Class<T> clazz, Map orderBy, Object... param) {
        StringBuilder hql = new StringBuilder("from " + clazz.getName() + " t where 1=1 ");
        for(int i = 0 ; i < param.length ; i += 3){
            if(param[i + 2] != null && StringUtils.isNotBlank(param[i + 2].toString())){
                hql.append(" and t.").append(param[i]).append(" ").append(param[i + 1]).append(" ?");
            }
        }
        int i = 0;
        for(Object o : orderBy.entrySet()){
            Map.Entry entry = (Map.Entry)o;
            if(i == 0){
                hql.append(" order by ");
            }
            hql.append(" t.").append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
            i ++ ;
            if(i == orderBy.entrySet().size()){
                hql = hql.deleteCharAt(hql.lastIndexOf(","));
            }
        }
        Session session = getSession();
        Query query = session.createQuery(hql.toString());
        DBHelper.createQuery(query,param);
        return query.list();
    }

    public <T> List<T> findByHQL(Class<T> clazz, Integer start, Integer limit, Map orderBy, Object... param) {
        if(start == null || limit == null){
            return findByHQL(clazz,orderBy,param);
        }
        StringBuilder hql = new StringBuilder("from " + clazz.getName() + " t where 1=1 ");
        for(int i = 0 ; i < param.length ; i += 3){
            if(param[i + 2] != null && StringUtils.isNotBlank(param[i + 2].toString())){
                hql.append(" and t.").append(param[i]).append(" ").append(param[i + 1]).append(" ?");
            }
        }
        int i = 0;
        for(Object o : orderBy.entrySet()){
            Map.Entry entry = (Map.Entry)o;
            if(i == 0){
                hql.append(" order by ");
            }
            hql.append(" t.").append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
            i ++ ;
            if(i == orderBy.entrySet().size()){
                hql = hql.deleteCharAt(hql.lastIndexOf(","));
            }
        }
        Session session = getSession();
        Query query = session.createQuery(hql.toString());
        DBHelper.createQuery(query,param);
        return query.setFirstResult(start).setMaxResults(limit).list();
    }

    public Integer findCountByHQL(Class clazz, Object... param) {
        StringBuilder hql = new StringBuilder("select count(*) from " + clazz.getName() + " t where 1=1 ");
        for(int i = 0 ; i < param.length ; i += 3){
            if(param[i + 2] != null && StringUtils.isNotBlank(param[i + 2].toString())){
                hql.append(" and t.").append(param[i]).append(" ").append(param[i + 1]).append(" ?");
            }
        }
        Session session = getSession();
        Query query = session.createQuery(hql.toString());
        DBHelper.createQuery(query,param);
        return ((Long)query.uniqueResult()).intValue();
    }

}
