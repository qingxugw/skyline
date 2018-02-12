package com.skyline.upload.engine;

import com.skyline.pub.utils.SpringContextUtil;
import com.skyline.upload.beans.Row;
import com.skyline.upload.domain.TeradataProduct;
import com.skyline.upload.domain.TeradataSale;
import com.skyline.upload.domain.TeradataUser;
import com.skyline.upload.util.ExcelUtil;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 上午7:26
 */
public class ExcelThread extends Thread {

    private CountDownLatch countDownLatch;
    private String fileName = null;
    private int sheetIndex = -1;
    private String sessionId;

    public ExcelThread(String fileName, int sheetIndex,CountDownLatch countDownLatch,String sessionId) {
        this.fileName = fileName;
        this.sheetIndex = sheetIndex;
        this.countDownLatch = countDownLatch;
        this.sessionId = sessionId;
    }

    @Override
    public void run() {
        if (fileName == null || sheetIndex <1){
            return ;
        }
        ExcelReadHandler excelReadHandler = new ExcelReadHandler(fileName);
        try {
            excelReadHandler.handler(sheetIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<Row> rows = excelReadHandler.getRows();
        if(sheetIndex == 2){
            //保存用户表
            SessionFactory factory = SpringContextUtil.getBean("sessionFactory");
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            int i=0;
            for(Row row:rows){
                if (i == 0) {
                    i++;
                    continue;
                }
                String userNo = ExcelUtil.getRealCellValue(row,1);
                String userName = ExcelUtil.getRealCellValue(row,2);
                String address = ExcelUtil.getRealCellValue(row,3);
                Date birthday = ExcelUtil.getDate(row, 4);
                String gender = ExcelUtil.getRealCellValue(row,5);
                TeradataUser user = new TeradataUser();
                user.setUserNo(userNo);
                user.setUserName(userName);
                user.setGender(gender);
                user.setAddress(address);
                user.setBirthday(birthday);
                user.setVersion(1);
                session.save(user);
                if(i%1000==0 && i/1000 > 1){   //每一千条刷新并写入数据库
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            session.close();
        }
        if(sheetIndex == 3){
            //保存产品表
            SessionFactory factory = SpringContextUtil.getBean("sessionFactory");
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            int i=0;
            for(Row row:rows){
                if (i == 0) {
                    i++;
                    continue;
                }
                String productNo = ExcelUtil.getRealCellValue(row,1);
                String productName = ExcelUtil.getRealCellValue(row,2);
                String category = ExcelUtil.getRealCellValue(row,3);
                BigDecimal money = ExcelUtil.getBigDecimal(row, 4);
                String unit = ExcelUtil.getRealCellValue(row,5);
                TeradataProduct product = new TeradataProduct();
                product.setProductNo(productNo);
                product.setProductName(productName);
                product.setCategory(category);
                product.setMoney(money);
                product.setUnit(unit);
                product.setVersion(1);
                session.save(product);
                if(i%1000==0 && i/1000 > 1){   //每一千条刷新并写入数据库
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            session.close();
        }
        if(sheetIndex == 4){
            //保存 销售历史表
            ExcelPool.getInstance().put(sessionId + ExcelPool.MIDDLESALECOUNT, rows.size());
            SessionFactory factory = SpringContextUtil.getBean("sessionFactory");
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            int i=0;
            for(Row row:rows){
                if (i == 0) {
                    i++;
                    continue;
                }
                String userNo = ExcelUtil.getRealCellValue(row, 2);
                String productNo = ExcelUtil.getRealCellValue(row,3);
                BigDecimal money = ExcelUtil.getBigDecimal(row, 4);
                BigDecimal number = ExcelUtil.getBigDecimal(row, 5);
                Date saleDate = ExcelUtil.getDate(row, 1);
                TeradataSale sale = new TeradataSale();
                sale.setUserNo(userNo);
                sale.setProductNo(productNo);
                sale.setMoney(money);
                sale.setNumber(number);
                sale.setSaleDate(saleDate);
                sale.setVersion(1);
                session.save(sale);
                if(i%8000==0 && i/8000 > 1){   //每8000条刷新并写入数据库
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            session.close();
        }
        countDownLatch.countDown();

    }
}
