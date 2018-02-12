package com.skyline.base.dto;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-20
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class TdRoleUserDTO extends BasePropertiesDTO {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 登录号
     */
    private String userNo;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 所属部门
     */
    private String subsectionId;

    private String subsectionName;
    /**
     * 办公电话
     */
    private String userPhone;
    /**
     * 家庭电话
     */
    private String userTel;
    /**
     * 手机
     */
    private String userMobile;
    /**
     * QQ
     */
    private String userQQ;
    /**
     * MSN
     */
    private String userMsn;
    /**
     * 邮件
     */
    private String userMail;
    /**
     * 备注
     */
    private String userRemark;
    /**
     * 用户所对应的角色列表
     */
    private Set<TdRoleDTO> roles;
    /**
     * 状态 启用 1 禁用0
     */
    private String status;

    private boolean success;
    private String msg;
    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

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

    public String getSubsectionName() {
        return subsectionName;
    }

    public void setSubsectionName(String subsectionName) {
        this.subsectionName = subsectionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserQQ() {
        return userQQ;
    }

    public void setUserQQ(String userQQ) {
        this.userQQ = userQQ;
    }

    public String getUserMsn() {
        return userMsn;
    }

    public void setUserMsn(String userMsn) {
        this.userMsn = userMsn;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getSubsectionId() {
        return subsectionId;
    }

    public void setSubsectionId(String subsectionId) {
        this.subsectionId = subsectionId;
    }

    public Set<TdRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<TdRoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdRoleUserDTO)) return false;

        TdRoleUserDTO tdUser = (TdRoleUserDTO) o;

        if (userId != null ? !userId.equals(tdUser.userId) : tdUser.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
