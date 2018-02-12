package com.skyline.base.service.impl;

import com.skyline.base.dao.LogDAO;
import com.skyline.base.domain.TdOperationLog;
import com.skyline.base.dto.TdOperationLogDTO;
import com.skyline.base.service.LogService;
import com.skyline.pub.annotation.MethodRemark;
import com.skyline.pub.service.BaseServiceImpl;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.DateUtil;
import com.skyline.pub.utils.enums.ModuleEnum;
import com.skyline.pub.utils.enums.OpTypeEnum;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-20
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public class LogServiceImpl extends BaseServiceImpl implements LogService {
    private LogDAO logDAO;

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    /**
     * 保存操作日志
     * @param className
     * @param methodName
     * @param paramMap
     * @param remark
     */
    public void insertLog(String className,String methodName,Map<String,Object> paramMap,MethodRemark remark) {
        TdOperationLog log = new TdOperationLog();
        log.setModuleId(remark.module().getKey());
        log.setModuleName(remark.module().getValue());
        log.setOpType(remark.opType().getKey());
        log.setOpTypeName(remark.opType().getValue());
        log.setOpClass(className);
        log.setOpMethod(methodName);
        log.setOpParam(paramMap.toString());
        log.setOpIp(AppUtils.getSession().getIp());
        log.setCreateDate(new Date());
        log.setCreateUser(AppUtils.getCurrentUser());
        log.setOpContent(InputOpContent(log));
        saveObject(log);
    }

    /**
     * 查询系统日志列表
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
    public List<TdOperationLogDTO> queryLog(String module, String opType, String opContent, String startDate, String endDate, int start, int limit) {
        List<TdOperationLog> logList = logDAO.queryLog(module,opType,opContent,DateUtil.getStartDate(startDate),DateUtil.getEndDate(endDate),start,limit);
        List<TdOperationLogDTO> finalList = new ArrayList<TdOperationLogDTO> ();
        for(TdOperationLog log:logList){
            TdOperationLogDTO   dto   = new TdOperationLogDTO();
            String[] ignoreProperties = {"createUser","modifyUser"};
            BeanUtils.copyProperties(log, dto, ignoreProperties);
            //设置创建人
            dto.setCreateUser(log.getCreateUser() ==null?null:log.getCreateUser().getUserName());
            //设置修改人
            dto.setModifyUser(log.getModifyUser() == null ? null : log.getModifyUser().getUserName());
            finalList.add(dto);
        }
        return finalList;
    }

    /**
     * 查询系统日志总条数
     *
     * @param module
     * @param opType
     * @param opContent
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer queryLogCount(String module, String opType, String opContent, String startDate, String endDate) {
        Integer count = logDAO.queryLogCount(module,opType,opContent, DateUtil.getStartDate(startDate),DateUtil.getEndDate(endDate));
        return count;
    }

    /**
     * 查询模块列表
     *
     * @return
     */
    public List<Map<String,Object>> queryModule() {
        List<Map<String,Object>> module = ModuleEnum.getModuleList();
        return module;
    }

    /**
     * 查询操作类型列表
     * @return
     */
    public List<Map<String,Object>> queryOpType(String mod) {
        List<Map<String,Object>> module = OpTypeEnum.getOpTypeList(mod);
        return module;
    }

    private String InputOpContent(TdOperationLog log) {
        StringBuilder sb = new StringBuilder();
        sb.append("用户[ ");
        sb.append(log.getCreateUser().getUserName());
        sb.append("]在[");
        sb.append(log.getModuleName());
        sb.append("]模块");
        sb.append("执行[");
        sb.append(log.getOpTypeName());
        sb.append("]操作");
        return sb.toString();
    }
}
