package com.skyline.upload.service;

import com.skyline.pub.exception.AppException;
import com.skyline.pub.service.BaseService;
import com.skyline.upload.domain.TeradataMiddleSale;
import com.skyline.upload.domain.TeradataMonth;
import com.skyline.upload.domain.TeradataProductCount;
import com.skyline.upload.dto.TeradataProductDTO;
import com.skyline.upload.dto.TeradataUserDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午6:42
 */
public interface UploadService extends BaseService {

    /**
     * 保存 用户表
     * @param userNo
     * @param userName
     * @param address
     * @param birthday
     * @param gender
     * @return
     * @throws AppException
     */
    public void saveTeradataUser(String userNo,String userName,String address,Date birthday,String gender) throws AppException;

    /**
     * 保存 产品表
     * @param productNo
     * @param productName
     * @param category
     * @param money
     * @param unit
     * @throws AppException
     */
    public void saveTeradataProduct(String productNo,String productName,String category,BigDecimal money,String unit) throws AppException;

    /**
     * 保存销售历史表
     * @param userNo
     * @param productNo
     * @param money
     * @param number
     * @param saleDate
     * @throws AppException
     */
    public void saveTeradataSale(String userNo,String productNo,BigDecimal money,BigDecimal number,Date saleDate) throws AppException;

    /**
     * 查询客户
     * @param userNo
     * @param userName
     * @param address
     * @param gender
     */
    public List<TeradataUserDTO> queryUserList(String userNo,String userName,String address,String gender) throws AppException;

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
    public List<TeradataUserDTO> queryUserList(String userNo,String userName,String address,String gender,int start,int limit) throws AppException;

    /**
     * 查询用户总数,分页使用
     * @param userNo
     * @param userName
     * @param address
     * @param gender
     * @return
     */
    public Integer queryUserCount(String userNo,String userName,String address,String gender) throws AppException;

    /**
     * 查询产品列表
     * @param userId
     * @param userNo
     * @param productNo
     * @param productName
     * @param category
     */
    public List<TeradataProductDTO> queryUserProductList(String userId,String userNo,String productNo,String productName,String category,String unit) throws AppException;

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
    public List<TeradataProductDTO> queryUserProductList(String userId,String userNo,String productNo,String productName,String category,String unit,int start,int limit) throws AppException;

    /**
     * 查询产品总数,分页使用
     * @param userId
     * @param productNo
     * @param productName
     * @param category
     * @return
     */
    public Integer queryUserProductCount(String userId,String userNo,String productNo,String productName,String category,String unit) throws AppException;

    /**
     * 查询产品清单
     * @param productNo
     * @param productName
     * @param category
     * @param unit
     * @return
     */
    public List<TeradataProductCount> queryProductList(String productNo,String productName,String category,String unit) throws AppException;

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
    public List<TeradataProductCount> queryProductList(String productNo,String productName,String category,String unit,int start,int limit) throws AppException;

    /**
     * 查询产品清单总条数
     * @param productNo
     * @param productName
     * @param category
     * @param unit
     * @return
     */
    public Integer queryProductCount(String productNo,String productName,String category,String unit) throws AppException;

    /**
     * 查询产品的销售历史
     * @param productNo
     * @param userNo
     * @param address
     * @param productName
     * @return
     */
    public List<TeradataMiddleSale> queryProductSaleList(String productNo,String userNo,String address,String productName) throws AppException;

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
    public List<TeradataMiddleSale> queryProductSaleList(String productNo,String userNo,String address,String productName,int start,int limit) throws AppException;

    /**
     * 查询产品销售历史总数
     * @param productNo
     * @param userNo
     * @return
     */
    public Integer queryProductSaleCount(String productNo,String userNo,String address,String productName ) throws AppException;

    /**
     * 查询month 数组 包含最大月份和第二大月份
     * @return
     */
    public TeradataMonth queryMonthArray(String tableName);

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
    public Integer updateProductCountProcedure();

    /**
     * 调用最新月份统计存储过程
     * @return
     */
    public Integer updateMonthProcedure();

    /**
     * 调用历史数据处理存储过程
     * @param start
     * @param limit
     * @return
     */
    public Integer updateMiddleSaleProcedure(int start,int limit);

    /**
     * 调用删除不合法数据存储过程
     * @return
     */
    public Integer deleteInvalidDataProcedure();
}
