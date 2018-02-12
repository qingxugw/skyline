package com.skyline.base.action;

import com.skyline.base.dto.TdOperationLogDTO;
import com.skyline.base.service.LogService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.BaseAction;
import com.skyline.pub.utils.enums.ValidatorEnumException;
import org.apache.commons.lang.xwork.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-24
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class LogAction extends BaseAction{
    private String opType;  //操作类型
    private String module;  //模块类型
    private String startDate; //开始时间
    private String endDate;  //结束时间
    private String opContent;//操作内容
    private int start;
    private int limit;
    private LogService logService;
    private List<Map<String,Object>> modules;
    private List<Map<String,Object>> opTypes;

    public String getOpType() {
        return opType;
    }

    public String getModule() {
        return module;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOpContent() {
        return opContent;
    }

    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }

    public LogService getLogService() {
        return logService;
    }

    public List<Map<String, Object>> getOpTypes() {
        return opTypes;
    }

    public void setOpTypes(List<Map<String, Object>> opTypes) {
        this.opTypes = opTypes;
    }

    public List<Map<String, Object>> getModules() {
        return modules;
    }

    public void setModules(List<Map<String, Object>> modules) {
        this.modules = modules;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setOpContent(String opContent) {
        try {
            opContent = URLDecoder.decode(opContent,ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.opContent = opContent;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 初始化日志页面
     * @return
     */
    public String initLogPage(){
        return SUCCESS;
    }

    /**
     * 查询日志列表
     * @return
     */
    public String queryLog(){
        List<TdOperationLogDTO> logList = logService.queryLog(module,opType,opContent,startDate,endDate,start,limit);
        int totalPorperty = logService.queryLogCount(module,opType,opContent,startDate,endDate);
        resultMap.put("root",logList);
        resultMap.put("totalProperty",totalPorperty);
        return "resultMap";
    }
    /**
     * 查询模块列表
     * @return
     */
    public String queryModule(){
        modules = logService.queryModule();
        return "modules";
    }
    /**
     * 查询操作类型
     * @return
     */
    public String queryOpType(){
        try{
            if(StringUtils.isBlank(module)){
                throw new AppException(ValidatorEnumException.获取参数出错);
            }
            opTypes = logService.queryOpType(module);
        }catch (AppException app){

        }
        return "opTypes";
    }

}
