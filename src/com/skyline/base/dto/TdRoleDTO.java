package com.skyline.base.dto;

import com.skyline.base.domain.BaseProperties;
import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdUser;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-21
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class TdRoleDTO extends BasePropertiesDTO{
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 角色编号
     */
    private String roleNo;
    /**
     * 角色名称
     */
    private String roleName;
    private String roleDesc;
    /**
     * 角色所所对应的资源列表
     */
    private Set<TdOperationDTO> operations;
    /**
     * 角色所对应的用户列表
     */
    private Set<TdUserDTO> users;

    private String status;
    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Set<TdUserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<TdUserDTO> users) {
        this.users = users;
    }

    public Set<TdOperationDTO> getOperations() {
        return operations;
    }

    public void setOperations(Set<TdOperationDTO> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdRoleDTO)) return false;

        TdRoleDTO tdRole = (TdRoleDTO) o;

        if (roleId != null ? !roleId.equals(tdRole.roleId) : tdRole.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return roleId != null ? roleId.hashCode() : 0;
    }
}
