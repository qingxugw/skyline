package com.skyline.pub.service;

import com.skyline.pub.dao.DAO;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-7-24
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public interface BaseService {
    /**
     * 与get的区别在于,load会首先查找一级二级缓存
     * 然后查找数据库,找不到会抛出异常,返回代理对象
     * @param clazz class
     * @param id id
     * @return object
     */
    public <T> T loadObject(Class<T> clazz, Serializable id);

    /**
     * 只查找一级缓存,不查找二级缓存,查找数据库找不到返回null,返回实体对象
     * @param clazz class
     * @param id id
     * @return object
     */
    public <T> T getObject(Class<T> clazz, Serializable id);

    /**
     * 根据对象查询条件 查询对象
     * @param o
     * @param <T>
     * @return
     */
    public <T> T getObject(T o);
    /**
     * 保存或更新一个实体对象
     * @param o object
     */
    public void saveObject(Object o);

    /**
     * 保存或更新一个实体集合
     * @param collection collection
     */
    public void saveCollection(Collection collection);

    /**
     * 更新一个实体对象
     * @param o object
     */
    public void updateObject(Object o);

    /**
     * 删除一个实体对象
     * @param o o
     */
    public void deleteObject(Object o);

    /**
     * 删除一个集合
     * @param collection collection
     */
    public void deleteCollection(Collection collection);

    /**
     * 根据一个实体类属性获取列表
     * 不支持封装类型(如 BigDecimal)及外键
     * @param o object example
     * @return list
     */
    public <T> List<T> findByExample(T o);

    /**
     * 根据属性组装HQL获取列表
     * @param clazz 实体类class
     * @param param 不定量参数，但是需要为3的整数倍，用法参下
     * @param <T> 泛型
     * @return List
     * 简便的组装条件获取结果集的方法，第一个参数应为实体类class，后面的不定量参数应
     * 参照如下格式：{fieldName},{condition},{parameter}
     * 具体用法应如：
     * findByHQL(TdSysOperator.class,
     *          "name","like","nameParameter",
     *         "sex","=",Integer.parseInt(sexParameter),
     *          "date",">=",new java.sql.Date(ageParameter),
     *          ....
     * )
     */
    public <T> List<T> findByHQL(Class<T> clazz,Object... param);

    /**
     * @see #findByHQL(Class, Object...)
     * @param clazz 实体类class
     * @param start 起始位置
     * @param limit 查询数量
     * @param param 条件参数
     * @param <T>  泛型
     * @return list
     */
    public <T> List<T> findByHQL(Class<T> clazz,Integer start,Integer limit,Object... param);

    /**
     * 根据属性组装HQL获取排序列表
     * @param clazz 实体类class
     * @param orderBy 排序参数，map类型，应为{name=desc,sex=asc}类似格式
     * @param param 不定量参数，但是需要为3的整数倍，用法参下
     * @param <T> 泛型
     * @return List #see findByHQL
     */
    public <T> List<T> findByHQL(Class<T> clazz,Map orderBy,Object... param);

    /**
     * @see #findByHQL(Class, java.util.Map, Object...)
     * @param clazz 实体类class
     * @param start 起始位置
     * @param limit 查询数量
     * @param orderBy orderBy条件
     * @param param 条件参数
     * @param <T> 泛型
     * @return list
     */
    public <T> List<T> findByHQL(Class<T> clazz,Integer start,Integer limit,Map orderBy,Object... param);
}
