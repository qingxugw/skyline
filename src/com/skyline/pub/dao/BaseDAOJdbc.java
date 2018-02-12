/*
 * 创建日期 2005-2-15
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.skyline.pub.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.io.Serializable;
import java.util.*;

/**
 * 提供jdbcDAO的公共支持
 * @author 李庆旭
 */
public class BaseDAOJdbc extends JdbcDaoSupport implements DAO {

    /* （非 Javadoc）
      * @see com.blt.efs.dao.DAO#getObjects(java.lang.Class)
      */
    public List getObjects(Class clazz) {
        return null;
    }

    /* （非 Javadoc）
      * @see com.blt.efs.dao.DAO#getObject(java.lang.Class, java.io.Serializable)
      */
    public Object getObject(Class clazz, Serializable id) {
        return null;
    }

    /**
     * 与get的区别在于,load会首先查找一级二级缓存
     * 然后查找数据库,找不到会抛出异常,
     *
     * @param clazz class
     * @param id    id
     * @return object
     */
    public <T> T loadObject(Class<T> clazz, Serializable id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /* （非 Javadoc）
      * @see com.blt.efs.dao.DAO#saveObject(java.lang.Object)
      */
    public void saveObject(Object o) {

    }

    /**
     * 保存或更新一个实体集合
     *
     * @param collection collection
     */
    public void saveCollection(Collection collection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 更新一个实体对象
     *
     * @param o object
     */
    public void updateObject(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 删除一个实体对象
     *
     * @param o o
     */
    public void deleteObject(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 删除一个集合
     *
     * @param collection collection
     */
    public void deleteCollection(Collection collection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 根据一个实体类属性获取列表
     * 不支持封装类型(如 BigDecimal)及外键
     *
     * @param o object example
     * @return list
     */
    public <T> List<T> findByExample(T o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 根据属性组装HQL获取列表
     *
     * @param clazz 实体类class
     * @param param 不定量参数，但是需要为3的整数倍，用法参下
     * @param <T>   泛型
     * @return List
     *         简便的组装条件获取结果集的方法，第一个参数应为实体类class，后面的不定量参数应
     *         参照如下格式：{fieldName},{condition},{parameter}
     *         具体用法应如：
     *         findByHQL(TdSysOperator.class,
     *         "name","like","nameParameter",
     *         "sex","=",Integer.parseInt(sexParameter),
     *         "date",">=",new java.sql.Date(ageParameter),
     *         ....
     *         )
     */
    public <T> List<T> findByHQL(Class<T> clazz, Object... param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param clazz 实体类class
     * @param start 起始位置
     * @param limit 查询数量
     * @param param 条件参数
     * @param <T>   泛型
     * @return list
     * @see #findByHQL(Class, Object...)
     */
    public <T> List<T> findByHQL(Class<T> clazz, Integer start, Integer limit, Object... param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 根据属性组装HQL获取排序列表
     *
     * @param clazz   实体类class
     * @param orderBy 排序参数，map类型，应为{name=desc,sex=asc}类似格式
     * @param param   不定量参数，但是需要为3的整数倍，用法参下
     * @param <T>     泛型
     * @return List #see findByHQL
     */
    public <T> List<T> findByHQL(Class<T> clazz, Map orderBy, Object... param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param clazz   实体类class
     * @param start   起始位置
     * @param limit   查询数量
     * @param orderBy orderBy条件
     * @param param   条件参数
     * @param <T>     泛型
     * @return list
     * @see #findByHQL(Class, java.util.Map, Object...)
     */
    public <T> List<T> findByHQL(Class<T> clazz, Integer start, Integer limit, Map orderBy, Object... param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 获取总条数的方法，参数和类型参见findByHQL
     *
     * @param clazz
     * @param param
     * @return
     * @see #findByHQL(Class, Object...)
     */
    public Integer findCountByHQL(Class clazz, Object... param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void update(Object o){

    }
    /* （非 Javadoc）
      * @see com.blt.efs.dao.DAO#removeObject(java.lang.Class, java.io.Serializable)
      */
    public void removeObject(Class clazz, Serializable id) {

    }

    public void removeObject(Collection collection) {

    }

    public void saveObject(Collection collection) {

    }

    public List getObject(Object object, Class clazz) {
        // TODO 自动生成方法存根
        return null;
    }

    public List getObjectByDate(Object object, String property, Date startdate, Date enddate, Class clazz) {
        // TODO 自动生成方法存根
        return null;
    }

    public List getObjectByOrder(Object object, Class clazz, TreeMap<String, String> treeMap) {
        // TODO 自动生成方法存根
        return null;
    }

    public List getPagesObject(Object object, Class clazz, int start, int limit) {
        // TODO Auto-generated method stub
        return null;
    }


    public List getObject(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
