package com.skyline.upload.action;

import com.skyline.pub.BaseAction;
import com.skyline.pub.utils.AppUtils;
import com.skyline.upload.domain.TeradataMiddleSale;
import com.skyline.upload.domain.TeradataMonth;
import com.skyline.upload.domain.TeradataProductCount;
import com.skyline.upload.dto.TeradataProductDTO;
import com.skyline.upload.dto.TeradataUserDTO;
import com.skyline.upload.engine.ExcelPool;
import com.skyline.upload.engine.ExcelProcedureThread;
import com.skyline.upload.engine.ExcelThread;
import com.skyline.upload.engine.ThreadPools;
import com.skyline.upload.service.UploadService;
import com.skyline.upload.util.ExcelUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-13 下午4:16
 */
public class UplaodQueryUserProductAction extends BaseAction {

    private String userId;
    private String userNo;
    private String userName;
    private String address;
    private String unit;
    private String gender;
    private String productNo;
    private String productName;
    private String category;
    private int start;
    private int limit;
    private String maxMonth;
    private String secondMonth;

    // 注入上传模块servcie
    private UploadService uploadService;

    public String getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(String maxMonth) {
        this.maxMonth = maxMonth;
    }

    public String getSecondMonth() {
        return secondMonth;
    }

    public void setSecondMonth(String secondMonth) {
        this.secondMonth = secondMonth;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 初始化客户清单查询页面
     * @return
     */
    public String initQueryUserProductPage(){
        return SUCCESS;
    }
    /**
     * 初始化产品清单查询页面
     * @return
     */
    public String initQueryProductPage(){
        return SUCCESS;
    }

    /**
     * 初始化图表展示界面
     * @return
     */
    public String initChartsPage(){
        return SUCCESS;
    }

    /**
     * 查询客户列表
     * @return
     */
    public String queryUserList(){
        List<TeradataUserDTO> userList = uploadService.queryUserList(userNo,userName,address,gender,start,limit);
        int totalPorperty = uploadService.queryUserCount(userNo,userName,address,gender);
        resultMap.put("root",userList);
        resultMap.put("totalProperty",totalPorperty);
        return "resultMap";
    }

    /**
     * 查询产品列表
     * @return
     */
    public String queryUserProductList(){
        List<TeradataProductDTO> productList = uploadService.queryUserProductList(userId,userNo,productNo,productName,category,unit,start,limit);
        int totalPorperty = uploadService.queryUserProductCount(userId,userNo,productNo,productName,category,unit);
        resultMap.put("root",productList);
        resultMap.put("totalProperty",totalPorperty);
        return "resultMap";
    }
    /**
     * 查询产品列表
     * @return
     */
    public String queryProductList(){
        List<TeradataProductCount> productList = uploadService.queryProductList(productNo,productName,category,unit,start,limit);
        int totalPorperty = uploadService.queryProductCount(productNo, productName, category, unit);
        resultMap.put("root",productList);
        resultMap.put("totalProperty",totalPorperty);
        return "resultMap";
    }

    /**
     * 查询销售历史
     * @return
     */
    public String queryProductSaleList(){
        List<TeradataMiddleSale> saleList = uploadService.queryProductSaleList(productNo, userNo, address, productName, start, limit);
        int totalPorperty = uploadService.queryProductSaleCount(productNo, userNo, address, productName);
        resultMap.put("root",saleList);
        resultMap.put("totalProperty",totalPorperty);
        return "resultMap";
    }
    /**
     * 首选查询month 组合
     * @return
     */
    public String queryMonthArray(){
        String tableName = "teradata_middle_sale";
        TeradataMonth month = uploadService.queryMonthArray(tableName);
        if(month != null){
            resultMap.put("root",month);
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
        }
        return "resultMap";
    }
    /**
     * 首选查询数据列表
     * @return
     */
    public String queryChartData(){
        List<Map>  resultlist = uploadService.queryChartData(maxMonth,secondMonth);
        resultMap.put("root",resultlist);
        resultMap.put("success",true);
        return "resultMap";
    }

    /**
     * 执行数据处理存储过程
     * @return
     */
    public String executeProcedure(){
        long start = System.currentTimeMillis();
        int total = (Integer)ExcelPool.getInstance().get(AppUtils.getHttpSession().getId()+ExcelPool.MIDDLESALECOUNT);
        uploadService.deleteInvalidDataProcedure();
        /**
         * 这里多线程调用存储过程，发现性能没有多少提升，于是放弃多线程，以后有时间再尝试高性能方式
         */
        uploadService.updateMiddleSaleProcedure(0,total);
        uploadService.updateProductCountProcedure();
        uploadService.updateMonthProcedure();
        long end = System.currentTimeMillis();
        long time = (end - start)/1000;
        String msg = "数据处理完毕,处理数据用时 ["+time+"] 秒";
        logger.debug(msg);
        resultMap.put("success",true);
        resultMap.put("time",time);
        resultMap.put("msg",msg);
        return "resultMap";
    }
}
