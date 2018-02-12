package com.skyline.base.dao;

import com.skyline.base.domain.TdOperationLog;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-26
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public interface LogDAO {
    /**
     * 查询系统日志
     * @param module
     * @param opType
     * @param opContent
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     */
    public List<TdOperationLog> queryLog(String module, String opType, String opContent, Date startDate, Date endDate, int start, int limit);

    /**
     * 查询系统日志总条数
     * @param module
     * @param opType
     * @param opContent
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer queryLogCount(String module, String opType, String opContent, Date startDate, Date endDate);
}
