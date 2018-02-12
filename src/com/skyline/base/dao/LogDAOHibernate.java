package com.skyline.base.dao;

import com.skyline.base.domain.TdOperationLog;
import com.skyline.pub.dao.BaseDAOHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-26
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public class LogDAOHibernate extends BaseDAOHibernate implements LogDAO{
    /**
     * 查询系统日志
     *
     * @param module
     * @param opType
     * @param opContent
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     */
    public List<TdOperationLog> queryLog(String module, String opType, String opContent, Date startDate, Date endDate, int start, int limit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdOperationLog op where 1=1 ");
        if (StringUtils.isNotBlank(module)){
            sb.append(" and op.moduleId = :module ");
        }
        if (StringUtils.isNotBlank(opType)){
            sb.append(" and op.opType = :opType ");
        }
        if (StringUtils.isNotBlank(opContent)){
            sb.append(" and op.opContent like :opContent ");
        }
        if ( startDate != null){
            sb.append(" and op.createDate >= :startDate ");
        }
        if ( endDate != null){
            sb.append(" and op.createDate <= :endDate ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(module)){
            query.setString("module", module);
        }
        if (StringUtils.isNotBlank(opType)){
            query.setString("opType", opType);
        }
        if (StringUtils.isNotBlank(opContent)){
            query.setString("opContent", "%" + opContent + "%");
        }
        if ( startDate != null){
            query.setTimestamp("startDate", startDate);
        }
        if ( endDate != null){
            query.setTimestamp("endDate", endDate);
        }
        System.out.println(query.getQueryString());
        return  query.setFirstResult(start).setMaxResults(limit).list();
    }

    /**
     * 查询系统日志总条数
     * @param module
     * @param opType
     * @param opContent
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer queryLogCount(String module, String opType, String opContent, Date startDate, Date endDate) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(op) from TdOperationLog op where 1=1 ");
        if (StringUtils.isNotBlank(module)){
            sb.append(" and op.moduleId = :module ");
        }
        if (StringUtils.isNotBlank(opType)){
            sb.append(" and op.opType = :opType ");
        }
        if (StringUtils.isNotBlank(opContent)){
            sb.append(" and op.opContent like :opContent ");
        }
        if ( startDate != null){
            sb.append(" and op.createDate >= :startDate ");
        }
        if ( endDate != null){
            sb.append(" and op.createDate <= :endDate ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(module)){
            query.setString("module", module);
        }
        if (StringUtils.isNotBlank(opType)){
            query.setString("opType", opType);
        }
        if (StringUtils.isNotBlank(opContent)){
            query.setString("opContent", "%" + opContent + "%");
        }
        if ( startDate != null){
            query.setTimestamp("startDate", startDate);
        }
        if ( endDate != null){
            query.setTimestamp("endDate", endDate);
        }
        return  ((Long)query.uniqueResult()).intValue();
    }
}
