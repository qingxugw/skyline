package com.skyline.upload.dao;

import com.skyline.pub.dao.BaseDAOHibernate;
import com.skyline.upload.domain.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.hql.antlr.SqlTokenTypes;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.asm.Type;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-13 下午4:36
 */
public class UploadHibernateDAO extends BaseDAOHibernate implements UploadDAO {
    public List<TeradataUser> queryUserList(String userNo, String userName, String address, String gender) {
        return queryUserList(userNo, userName, address, gender,-1,-1);
    }

    public List<TeradataUser> queryUserList(String userNo, String userName, String address, String gender, int start, int limit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from TeradataUser u where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and u.userNo = :userNo ");
        }
        if (StringUtils.isNotBlank(gender)){
            sb.append(" and u.gender = :gender ");
        }
        if (StringUtils.isNotBlank(userName)){
            sb.append(" and u.userName like :userName ");
        }
        if (StringUtils.isNotBlank(address)){
            sb.append(" and u.address like :address ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(gender)){
            query.setString("gender", gender);
        }
        if (StringUtils.isNotBlank(userName)){
            query.setString("userName", "%" + userName + "%");
        }
        if (StringUtils.isNotBlank(address)){
            query.setString("address", "%" + address + "%");
        }
        if(start !=-1 && limit != -1){
            return query.setFirstResult(start).setMaxResults(limit).list();
        }
        return query.list();
    }

    public Integer queryUserCount(String userNo, String userName, String address, String gender) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(u) from TeradataUser u where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and u.userNo = :userNo ");
        }
        if (StringUtils.isNotBlank(gender)){
            sb.append(" and u.gender = :gender ");
        }
        if (StringUtils.isNotBlank(userName)){
            sb.append(" and u.userName like :userName ");
        }
        if (StringUtils.isNotBlank(address)){
            sb.append(" and u.address like :address ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(gender)){
            query.setString("gender", gender);
        }
        if (StringUtils.isNotBlank(userName)){
            query.setString("userName", "%" + userName + "%");
        }
        if (StringUtils.isNotBlank(address)){
            query.setString("address", "%" + address + "%");
        }
        return  ((Long)query.uniqueResult()).intValue();
    }

    public List<TeradataProduct> queryUserProductList(String userId,String userNo, String productNo, String productName, String category,String unit) {
        return queryUserProductList(userId, userNo, productNo, productName, category, unit, -1, -1);
    }

    public List<TeradataProduct> queryUserProductList(String userId,String userNo, String productNo, String productName, String category,String unit, int start, int limit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT DISTINCT p.`PRODUCTID`,p.`PRODUCTNO`,p.`PRODUCTNAME`,p.`CATEGORY`,p.`MONEY`,p.`UNIT`,");
        sb.append("  p.`CREATEDATE`,p.`CREATEUSERID`,p.`MODIFYDATE`,p.`MODIFYUSERID`,p.`VERSION` FROM teradata_product p");
        sb.append("  JOIN  teradata_sale s ON s.`PRODUCTNO` = p.`PRODUCTNO`  where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and s.`USERNO` = :userNo ");
        }
        if (StringUtils.isNotBlank(productNo)){
            sb.append(" and p.`PRODUCTNO` = :productNo ");
        }
        if (StringUtils.isNotBlank(unit)){
            sb.append(" and p.`UNIT` = :unit ");
        }
        if (StringUtils.isNotBlank(productName)){
            sb.append(" and p.`PRODUCTNAME` like :productName ");
        }
        if (StringUtils.isNotBlank(category)){
            sb.append(" and p.`CATEGORY` like :category ");
        }
        Query query=session.createSQLQuery(sb.toString()).addEntity(TeradataProduct.class);
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(productNo)){
            query.setString("productNo", productNo);
        }
        if (StringUtils.isNotBlank(productName)){
            query.setString("productName", "%" + productName + "%");
        }
        if (StringUtils.isNotBlank(category)){
            query.setString("category", "%" + category + "%");
        }
        if (StringUtils.isNotBlank(unit)){
            query.setString("unit",unit);
        }
        if(start !=-1 && limit != -1){
            return query.setFirstResult(start).setMaxResults(limit).list();
        }
        return query.list();
    }

    public Integer queryUserProductCount(String userId,String userNo, String productNo, String productName, String category,String unit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(DISTINCT(p.`PRODUCTID`)) number FROM teradata_product p");
        sb.append(" JOIN  teradata_sale s ON s.`PRODUCTNO` = p.`PRODUCTNO`  where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and s.USERNO = :userNo ");
        }
        if (StringUtils.isNotBlank(productNo)){
            sb.append(" and p.PRODUCTNO = :productNo ");
        }
        if (StringUtils.isNotBlank(productName)){
            sb.append(" and p.PRODUCTNAME like :productName ");
        }
        if (StringUtils.isNotBlank(category)){
            sb.append(" and p.CATEGORY like :category ");
        }
        if (StringUtils.isNotBlank(unit)){
            sb.append(" and p.`UNIT` = :unit ");
        }
        Query query=session.createSQLQuery(sb.toString()).addScalar("number",Hibernate.LONG);
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(productNo)){
            query.setString("productNo", productNo);
        }
        if (StringUtils.isNotBlank(productName)){
            query.setString("productName", "%" + productName + "%");
        }
        if (StringUtils.isNotBlank(category)){
            query.setString("category", "%" + category + "%");
        }
        if (StringUtils.isNotBlank(unit)){
            query.setString("unit", unit);
        }
        return  ((Long)query.uniqueResult()).intValue();
    }

    public List<TeradataProductCount> queryProductList(String productNo, String productName, String category, String unit) {
        return queryProductList(productNo, productName, category, unit,-1,-1);
    }

    public List<TeradataProductCount> queryProductList(String productNo, String productName, String category, String unit, int start, int limit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT pc.`COUNTID`,pc.`PRODUCTNO`,pc.`PRODUCTNAME`,p.`CATEGORY`,pc.`number`,pc.`MONEY`,p.`UNIT`,pc.`PROFIT` ");
        sb.append("  FROM teradata_product_count pc");
        sb.append("  JOIN  teradata_product p ON p.`PRODUCTNO` = pc.`PRODUCTNO`  where 1=1 ");
        if (StringUtils.isNotBlank(productNo)){
            sb.append(" and pc.`PRODUCTNO` = :productNo ");
        }
        if (StringUtils.isNotBlank(unit)){
            sb.append(" and p.`UNIT` = :unit ");
        }
        if (StringUtils.isNotBlank(productName)){
            sb.append(" and pc.`PRODUCTNAME` like :productName ");
        }
        if (StringUtils.isNotBlank(category)){
            sb.append(" and p.`CATEGORY` like :category ");
        }
        Query query=session.createSQLQuery(sb.toString()).addEntity(TeradataProductCount.class);

        if (StringUtils.isNotBlank(productNo)){
            query.setString("productNo", productNo);
        }
        if (StringUtils.isNotBlank(productName)){
            query.setString("productName", "%" + productName + "%");
        }
        if (StringUtils.isNotBlank(category)){
            query.setString("category", "%" + category + "%");
        }
        if (StringUtils.isNotBlank(unit)){
            query.setString("unit",unit);
        }
        if(start !=-1 && limit != -1){
            return query.setFirstResult(start).setMaxResults(limit).list();
        }
        return query.list();
    }

    public Integer queryProductCount(String productNo, String productName, String category, String unit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT count(pc.`COUNTID`) number FROM teradata_product_count pc");
        sb.append("  JOIN  teradata_product p ON p.`PRODUCTNO` = pc.`PRODUCTNO`  where 1=1 ");
        if (StringUtils.isNotBlank(productNo)){
            sb.append(" and pc.`PRODUCTNO` = :productNo ");
        }
        if (StringUtils.isNotBlank(unit)){
            sb.append(" and p.`UNIT` = :unit ");
        }
        if (StringUtils.isNotBlank(productName)){
            sb.append(" and pc.`PRODUCTNAME` like :productName ");
        }
        if (StringUtils.isNotBlank(category)){
            sb.append(" and p.`CATEGORY` like :category ");
        }
        Query query=session.createSQLQuery(sb.toString()).addScalar("number",Hibernate.LONG);

        if (StringUtils.isNotBlank(productNo)){
            query.setString("productNo", productNo);
        }
        if (StringUtils.isNotBlank(productName)){
            query.setString("productName", "%" + productName + "%");
        }
        if (StringUtils.isNotBlank(category)){
            query.setString("category", "%" + category + "%");
        }
        if (StringUtils.isNotBlank(unit)){
            query.setString("unit",unit);
        }
        return  ((Long)query.uniqueResult()).intValue();
    }

    public List<TeradataMiddleSale> queryProductSaleList(String productNo, String userNo, String address, String productName) {
        return queryProductSaleList(productNo, userNo, address, productName,-1,-1);
    }

    public List<TeradataMiddleSale> queryProductSaleList(String productNo, String userNo, String address, String productName, int start, int limit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from TeradataMiddleSale u where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and u.userNo = :userNo ");
        }
        if (StringUtils.isNotBlank(productNo)){
            sb.append(" and u.productNo = :productNo ");
        }
        if (StringUtils.isNotBlank(productName)){
            sb.append(" and u.productName like :productName ");
        }
        if (StringUtils.isNotBlank(address)){
            sb.append(" and u.address like :address ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(productNo)){
            query.setString("productNo", productNo);
        }
        if (StringUtils.isNotBlank(productName)){
            query.setString("productName", "%" + productName + "%");
        }
        if (StringUtils.isNotBlank(address)){
            query.setString("address", "%" + address + "%");
        }
        if(start !=-1 && limit != -1){
            return query.setFirstResult(start).setMaxResults(limit).list();
        }
        return query.list();
    }

    public Integer queryProductSaleCount(String productNo, String userNo, String address, String productName) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(u) from TeradataMiddleSale u where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and u.userNo = :userNo ");
        }
        if (StringUtils.isNotBlank(productNo)){
            sb.append(" and u.productNo = :productNo ");
        }
        if (StringUtils.isNotBlank(productName)){
            sb.append(" and u.productName like :productName ");
        }
        if (StringUtils.isNotBlank(address)){
            sb.append(" and u.address like :address ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(productNo)){
            query.setString("productNo", productNo);
        }
        if (StringUtils.isNotBlank(productName)){
            query.setString("productName", "%" + productName + "%");
        }
        if (StringUtils.isNotBlank(address)){
            query.setString("address", "%" + address + "%");
        }
        return  ((Long)query.uniqueResult()).intValue();
    }

    public List<TeradataMonth> queryMonthArray(String tableName) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from TeradataMonth u where 1=1 ");
        if (StringUtils.isNotBlank(tableName)){
            sb.append(" and u.tableName = :tableName ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(tableName)){
            query.setString("tableName", tableName);
        }
        return query.list();
    }

    public List<Map> queryChartData(String maxMonth, String secondMonth) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT cur.address,cur.totalprofit  ");
        sb.append(" '");
        sb.append(maxMonth);
        sb.append("'");
        sb.append(" ,IFNULL(sec.totalprofit,0)  ");
        sb.append(" '");
        sb.append(secondMonth);
        sb.append("'");
        sb.append(" FROM ( SELECT ms.`ADDRESS`,SUM(ms.`TOTALPROFIT`) totalprofit FROM teradata_middle_sale ms  ");
        sb.append(" WHERE ms.`MONTH` = :maxMonth  ");
        sb.append(" GROUP BY ms.`ADDRESS`  ");
        sb.append(" ORDER BY SUM(ms.`TOTALPROFIT`) DESC LIMIT 0,10  ");
        sb.append(" ) cur LEFT JOIN ( ");
        sb.append(" SELECT ms.`ADDRESS`,SUM(ms.`TOTALPROFIT`) totalprofit FROM teradata_middle_sale ms ");
        sb.append(" WHERE ms.`MONTH` = :secondMonth  ");
        sb.append(" GROUP BY ms.`ADDRESS` ");
        sb.append(" ) sec ON sec.address = cur.address ");
        Query query=session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setString("maxMonth",maxMonth);
        query.setString("secondMonth",secondMonth);
        return query.list();
    }

    public Integer updateProductCountProcedure() throws SQLException {
        Integer val = 0;
        Session session =getSession();
        Connection conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        ResultSet rs =null;
        CallableStatement call = conn.prepareCall("{Call teradata_product_count_procedure(?)}");
        call.registerOutParameter(1, Types.TINYINT);
        rs = call.executeQuery();
        val = call.getInt(1);
        rs.close();
        return val;
    }

    public Integer updateMonthProcedure() throws SQLException {
        Integer val = 0;
        Session session =getSession();
        Connection conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        ResultSet rs =null;
        CallableStatement call = conn.prepareCall("{Call teradata_month_procedure(?)}");
        call.registerOutParameter(1, Types.TINYINT);
        rs = call.executeQuery();
        val = call.getInt(1);
        rs.close();
        return val;
    }

    public Integer updateMiddleSaleProcedure(int start, int limit) throws SQLException {
        Integer val = 0;
        Session session =getSession();
        Connection conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        ResultSet rs =null;
        CallableStatement call = conn.prepareCall("{Call teradata_middle_sale_procedure(?,?,?)}");
        call.setInt(1,start);
        call.setInt(2,limit);
        call.registerOutParameter(3, Types.TINYINT);
        rs = call.executeQuery();
        val = call.getInt(3);
        rs.close();
        return val;
    }

    public Integer deleteInvalidDataProcedure() throws SQLException {
        Integer val = 0;
        Session session =getSession();
        Connection conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        ResultSet rs =null;
        CallableStatement call = conn.prepareCall("{Call teradata_delete_invlid_data_procedure(?)}");
        call.registerOutParameter(1, Types.TINYINT);
        rs = call.executeQuery();
        val = call.getInt(1);
        rs.close();
        return val;
    }
}
