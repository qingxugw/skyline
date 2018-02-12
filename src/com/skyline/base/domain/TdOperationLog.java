package com.skyline.base.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-21
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TD_OPERATION_LOG")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TdOperationLog extends BaseProperties {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(length = 45)
    private String opId;
    /**
     * 模块ID
     */
    @Column(length = 50)
    private String moduleId;
    /**
     * 模块ID
     */
    @Column(length = 200)
    private String moduleName;
    /**
     * 操作类型
     */
    @Column(length = 50)
    private String opType;
    /**
     * 操作类型名称
     */
    @Column(length = 200)
    private String opTypeName;
    /**
     * 操作内容
     */
    @Column(length = 1000)
    private String opContent;
    /**
     * 操作参数
     */
    @Column(length = 1000)
    private String opParam;
    /**
     * 操作类
     */
    @Column(length = 300)
    private String opClass;
    /**
     * 操作方法
     */
    @Column(length = 100)
    private String opMethod;
    /**
     * 操作方法
     */
    @Column(length = 50)
    private String opIp;

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdOperationLog)) return false;

        TdOperationLog that = (TdOperationLog) o;

        if (opId != null ? !opId.equals(that.opId) : that.opId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return opId != null ? opId.hashCode() : 0;
    }
}
