package com.skyline.pub.dao;

import java.io.Serializable;
import java.util.*;


/**
 *公共DAO接口
 * @author 李庆旭
 */
public interface DAO {

    /**
     * 与get的区别在于,load会首先查找一级二级缓存
     * 然后查找数据库,找不到会抛出异常,
     * @param clazz class
     * @param id id
     * @return object
     */
    public <T> T loadObject(Class<T> clazz, Serializable id) throws RuntimeException;

    /**
     * 只查找一级缓存,不查找二级缓存,查找数据库找不到返回null,
     * @param clazz class
     * @param id id
     * @return object
     */
    public <T> T getObject(Class<T> clazz, Serializable id) throws RuntimeException;

    /**
     * 根据查询条件查询对对应的对象
     * @param o
     * @param <T>
     * @return
     */
    public <T> T getObject(T o) throws RuntimeException;

    /**
     * 保存或更新一个实体对象
     * @param o object
     */
    public void saveObject(Object o) throws RuntimeException;

    /**
     * 保存或更新一个实体集合
     * @param collection collection
     */
    public void saveCollection(Collection collection) throws RuntimeException;

    /**
     * 更新一个实体对象
     * @param o object
     */
    public void updateObject(Object o);

    /**
     * 删除一个实体对象
     * @param o o
     */
    public void deleteObject(Object o) throws RuntimeException;

    /**
     * 删除一个集合
     * @param collection collection
     */
    public void deleteCollection(Collection collection) throws RuntimeException;

    /**
     * 根据一个实体类属性获取列表
     * 不支持封装类型(如 BigDecimal)及外键
     * @param o object example
     * @return list
     */
    public <T> List<T> findByExample(T o) throws RuntimeException;

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
    public <T> List<T> findByHQL(Class<T> clazz,Object... param) throws RuntimeException;

    /**
     * @see #findByHQL(Class, Object...)
     * @param clazz 实体类class
     * @param start 起始位置
     * @param limit 查询数量
     * @param param 条件参数
     * @param <T>  泛型
     * @return list
     */
    public <T> List<T> findByHQL(Class<T> clazz,Integer start,Integer limit,Object... param) throws RuntimeException;

    /**
     * 根据属性组装HQL获取排序列表
     * @param clazz 实体类class
     * @param orderBy 排序参数，map类型，应为{name=desc,sex=asc}类似格式
     * @param param 不定量参数，但是需要为3的整数倍，用法参下
     * @param <T> 泛型
     * @return List #see findByHQL
     */
    public <T> List<T> findByHQL(Class<T> clazz,Map orderBy,Object... param) throws RuntimeException;

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
    public <T> List<T> findByHQL(Class<T> clazz,Integer start,Integer limit,Map orderBy,Object... param) throws RuntimeException;

    /**
     * 获取总条数的方法，参数和类型参见findByHQL
     * @see #findByHQL(Class, Object...)
     * @param clazz
     * @param param
     * @return
     */
    public Integer findCountByHQL(Class clazz,Object... param) throws RuntimeException;
}