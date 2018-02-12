package com.skyline.base.dto;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-26
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class TdOperationLogDTO extends BasePropertiesDTO{
    /**
     * 主键
     */
    private String opId;
    /**
     * 模块ID
     */
    private String moduleId;
    /**
     * 模块ID
     */
    private String moduleName;
    /**
     * 操作类型
     */
    private String opType;
    /**
     * 操作类型名称
     */
    private String opTypeName;
    /**
     * 操作内容
     */
    private String opContent;
    /**
     * 操作参数
     */
    private String opParam;
    /**
     * 操作类
     */
    private String opClass;
    /**
     * 操作方法
     */
    private String opMethod;
    /**
     * 操作方法
     */
    private String opIp;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpTypeName() {
        return opTypeName;
    }

    public void setOpTypeName(String opTypeName) {
        this.opTypeName = opTypeName;
    }

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    public String getOpParam() {
        return opParam;
    }

    public void setOpParam(String opParam) {
        this.opParam = opParam;
    }

    public String getOpClass() {
        return opClass;
    }

    public void setOpClass(String opClass) {
        this.opClass = opClass;
    }

    public String getOpMethod() {
        return opMethod;
    }

    public void setOpMethod(String opMethod) {
        this.opMethod = opMethod;
    }

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp;
    }
}
