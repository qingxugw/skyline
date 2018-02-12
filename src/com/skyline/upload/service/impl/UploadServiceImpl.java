package com.skyline.upload.service.impl;

import com.skyline.pub.exception.AppException;
import com.skyline.pub.service.BaseServiceImpl;
import com.skyline.upload.dao.UploadDAO;
import com.skyline.upload.domain.*;
import com.skyline.upload.dto.TeradataProductDTO;
import com.skyline.upload.dto.TeradataUserDTO;
import com.skyline.upload.service.UploadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午6:49
 */
public class UploadServiceImpl extends BaseServiceImpl implements UploadService{

    private UploadDAO uploadDAO;

    public void setUploadDAO(UploadDAO uploadDAO) {
        this.uploadDAO = uploadDAO;
    }

    public void saveTeradataUser(String userNo, String userName, String address, Date birthday, String gender) throws AppException {
        TeradataUser user = new TeradataUser();
        user.setUserNo(userNo);
        user.setUserName(userName);
        user.setGender(gender);
        user.setAddress(address);
        user.setBirthday(birthday);
        user.setVersion(1);
        user.setCreateDate(new Date());
        saveObject(user);
    }

    public void saveTeradataProduct(String productNo, String productName, String category, BigDecimal money, String unit) throws AppException {
        TeradataProduct product = new TeradataProduct();
        product.setProductNo(productNo);
        product.setProductName(productName);
        product.setCategory(category);
        product.setMoney(money);
        product.setUnit(unit);
        product.setVersion(1);
        product.setCreateDate(new Date());
        saveObject(product);
    }

    public void saveTeradataSale(String userNo, String productNo, BigDecimal money, BigDecimal number, Date saleDate) throws AppException {
        TeradataSale sale = new TeradataSale();
        sale.setUserNo(userNo);
        sale.setProductNo(productNo);
        sale.setMoney(money);
        sale.setNumber(number);
        sale.setSaleDate(saleDate);
        sale.setVersion(1);
        sale.setCreateDate(new Date());
        saveObject(sale);
    }

    public List<TeradataUserDTO> queryUserList(String userNo, String userName, String address, String gender) throws AppException{
        return queryUserList(userNo, userName, address, gender,-1,-1);
    }

    public List<TeradataUserDTO> queryUserList(String userNo, String userName, String address, String gender, int start, int limit) throws AppException{
        List<TeradataUser> userList = uploadDAO.queryUserList(userNo,userName, address, gender, start, limit);
        List<TeradataUserDTO> finalList = new ArrayList<TeradataUserDTO>();
        for(TeradataUser user:userList){
            TeradataUserDTO   dto   = new TeradataUserDTO();
            String[] ignoreProperties = {"createUser","modifyUser"};
            try {
                BeanUtils.copyProperties(user, dto, ignoreProperties);
            } catch (BeansException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            finalList.add(dto);
        }
        return finalList;
    }

    public Integer queryUserCount(String userNo, String userName, String address, String gender) throws AppException{
        Integer count = uploadDAO.queryUserCount(userNo,userName,address,gender);
        return count;
    }

    public List<TeradataProductDTO> queryUserProductList(String userId,String userNo, String productNo, String productName, String category,String unit) throws AppException{
        return queryUserProductList(userId, userNo, productNo, productName, category, unit, -1, -1);
    }

    public List<TeradataProductDTO> queryUserProductList(String userId,String userNo, String productNo, String productName, String category,String unit, int start, int limit) throws AppException{
        List<TeradataProduct> productList = uploadDAO.queryUserProductList(userId, userNo, productNo, productName, category, unit, start, limit);
        List<TeradataProductDTO> finalList = new ArrayList<TeradataProductDTO>();
        for(TeradataProduct product:productList){
            TeradataProductDTO   dto   = new TeradataProductDTO();
            String[] ignoreProperties = {"createUser","modifyUser","user"};
            BeanUtils.copyProperties(product, dto, ignoreProperties);
            finalList.add(dto);
        }
        return finalList;
    }

    public Integer queryUserProductCount(String userId,String userNo, String productNo, String productName, String category,String unit) throws AppException{
        Integer count = uploadDAO.queryUserProductCount(userId, userNo, productNo, productName, category, unit);
        return count;
    }

    public List<TeradataProductCount> queryProductList(String productNo, String productName, String category, String unit) throws AppException {
        return uploadDAO.queryProductList(productNo, productName, category, unit);
    }

    public List<TeradataProductCount> queryProductList(String productNo, String productName, String category, String unit, int start, int limit) throws AppException {
        return uploadDAO.queryProductList(productNo, productName, category, unit,start,limit);
    }

    public Integer queryProductCount(String productNo, String productName, String category, String unit) throws AppException {
        return uploadDAO.queryProductCount(productNo, productName, category, unit);
    }

    public List<TeradataMiddleSale> queryProductSaleList(String productNo, String userNo, String address, String productName) throws AppException {
        return uploadDAO.queryProductSaleList(productNo, userNo, address, productName);
    }

    public List<TeradataMiddleSale> queryProductSaleList(String productNo, String userNo, String address, String productName, int start, int limit) throws AppException {
        return uploadDAO.queryProductSaleList(productNo, userNo, address, productName,start,limit);
    }

    public Integer queryProductSaleCount(String productNo, String userNo, String address, String productName) throws AppException {
        return uploadDAO.queryProductSaleCount(productNo, userNo, address, productName);
    }

    public TeradataMonth queryMonthArray(String tableName) {
        List<TeradataMonth> teradataMonths = uploadDAO.queryMonthArray(tableName);
        if(teradataMonths != null){
            return teradataMonths.get(0);
        }
        return  null;
    }

    public List<Map> queryChartData(String maxMonth, String secondMonth) {
        return uploadDAO.queryChartData(maxMonth,secondMonth);
    }

    public Integer updateProductCountProcedure() {
        try {
            return uploadDAO.updateProductCountProcedure();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Integer updateMonthProcedure() {
        try {
            return uploadDAO.updateMonthProcedure();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Integer updateMiddleSaleProcedure(int start,int limit) {
        try {
            return uploadDAO.updateMiddleSaleProcedure(start, limit);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Integer deleteInvalidDataProcedure() {
        try {
            return uploadDAO.deleteInvalidDataProcedure();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
