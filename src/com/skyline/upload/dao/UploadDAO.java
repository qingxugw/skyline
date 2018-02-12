package com.skyline.upload.dao;

import com.skyline.upload.domain.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-13 下午4:34
 */
public interface UploadDAO {

    /**
     * 查询客户
     * @param userNo
     * @param userName
     * @param address
     * @param gender
     */
    public List<TeradataUser> queryUserList(String userNo,String userName,String address,String gender);

    /**
     * 返回用户分页列表
     * @param userNo
     * @param userName
     * @param address
     * @param gender
     * @param start
     * @param limit
     * @return
     */
    public List<TeradataUser> queryUserList(String userNo,String userName,String address,String gender,int start,int limit);

    /**
     * 查询用户总数,分页使用
     * @param userNo
     * @param userName
     * @param address
     * @param gender
     * @return
     */
    public Integer queryUserCount(String userNo,String userName,String address,String gender);

    /**
     * 查询产品列表
     * @param userId
     * @param userNo
     * @param productNo
     * @param productName
     * @param category
     */
    public List<TeradataProduct> queryUserProductList(String userId,String userNo,String productNo,String productName,String category,String unit);

    /**
     * 查询产品分页列表
     * @param userId
     * @param userNo
     * @param productNo
     * @param productName
     * @param category
     * @param start
     * @param limit
     * @return
     */
    public List<TeradataProduct> queryUserProductList(String userId,String userNo,String productNo,String productName,String category,String unit,int start,int limit);

    /**
     * 查询产品总数,分页使用
     * @param userId
     * @param productNo
     * @param productName
     * @param category
     * @return
     */
    public Integer queryUserProductCount(String userId,String userNo,String productNo,String productName,String category,String unit);

    /**
     * 查询产品清单
     * @param productNo
     * @param productName
     * @param category
     * @param unit
     * @return
     */
    public List<TeradataProductCount> queryProductList(String productNo,String productName,String category,String unit);

    /**
     * 查询产品分页清单
     * @param productNo
     * @param productName
     * @param category
     * @param unit
     * @param start
     * @param limit
     * @return
     */
    public List<TeradataProductCount> queryProductList(String productNo,String productName,String category,String unit,int start,int limit);

    /**
     * 查询产品清单总条数
     * @param productNo
     * @param productName
     * @param category
     * @param unit
     * @return
     */
    public Integer queryProductCount(String productNo,String productName,String category,String unit);

    /**
     * 查询产品的销售历史
     * @param productNo
     * @param userNo
     * @param address
     * @param productName
     * @return
     */
    public List<TeradataMiddleSale> queryProductSaleList(String productNo,String userNo,String address,String productName);

    /**
     * 查询产品销售历史分页列表
     * @param productNo
     * @param userNo
     * @param address
     * @param productName
     * @param start
     * @param limit
     * @return
     */
    public List<TeradataMiddleSale> queryProductSaleList(String productNo,String userNo,String address,String productName,int start,int limit);

    /**
     * 查询产品销售历史总数
     * @param productNo
     * @param userNo
     * @return
     */
    public Integer queryProductSaleCount(String productNo,String userNo,String address,String productName );

    /**
     * 查询month 数组 包含最大月份和第二大月份
     * @return
     */
    public List<TeradataMonth> queryMonthArray(String tableName);

    /**
     * 根据第一和第二大月份来查询图表需要的展示数据
     * @param maxMonth
     * @param secondMonth
     * @return
     */
    public List<Map> queryChartData(String maxMonth,String secondMonth);

    /**
     * 调用产品统计存储过程
     * @return
     */
    public Integer updateProductCountProcedure() throws SQLException;

    /**
     * 调用最新月份统计存储过程
     * @return
     */
    public Integer updateMonthProcedure() throws SQLException;

    /**
     * 调用历史数据处理存储过程
     * @param start
     * @param limit
     * @return
     */
    public Integer updateMiddleSaleProcedure(int start,int limit) throws SQLException;

    /**
     * 调用删除不合法数据存储过程
     * @return
     */
    public Integer deleteInvalidDataProcedure() throws SQLException;
}
