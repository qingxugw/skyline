package com.skyline.base.service;

import com.skyline.base.dto.TdOperationLogDTO;
import com.skyline.pub.annotation.MethodRemark;
import com.skyline.pub.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-20
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public interface LogService extends BaseService {
    /**
     * 保存操作日志
     * @param className
     * @param methodName
     * @param paramMap
     * @param remark
     */
    public void insertLog(String className,String methodName,Map<String,Object> paramMap,MethodRemark remark);

    /**
     * 查询系统日志列表
     * @param module
     * @param opType
     * @param opContent
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     */
    public List<TdOperationLogDTO> queryLog(String module,String opType,String opContent,String startDate,String endDate,int start,int limit);

    /**
     * 查询系统日志总条数
     * @param module
     * @param opType
     * @param opContent
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer queryLogCount(String module,String opType,String opContent,String startDate,String endDate);

    /**
     * 查询模块列表
     * @return
     */
    public List<Map<String,Object>> queryModule();

    /**
     * 查询操作类型列表
     * @return
     */
    public List<Map<String,Object>> queryOpType(String module);
}
