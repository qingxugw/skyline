package com.skyline.base.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-20
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TD_USER")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TdUser extends BaseProperties {
    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "userId",length = 45)
    private String userId;
    /**
     * 登录号
     */
    @Column(name = "userNo",length = 20)
    private String userNo;
    /**
     * 用户名
     */
    @Column(name = "userName",length = 50)
    private String userName;
    /**
     * 密码
     */
    @Column(name = "password",length = 50)
    private String password;
    /**
     * 所属部门
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subsectionId")
    private TdSubsection subsectionId;
    /**
     * 办公电话
     */
    @Column(length = 20)
    private String userPhone;
    /**
     * 家庭电话
     */
    @Column(length = 20)
    private String userTel;
    /**
     * 手机
     */
    @Column(length = 20)
    private String userMobile;
    /**
     * QQ
     */
    @Column(length = 20)
    private String userQQ;
    /**
     * MSN
     */
    @Column(length = 100)
    private String userMsn;
    /**
     * 邮件
     */
    @Column(length = 100)
    private String userMail;
    /**
     * 备注
     */
    @Column(length = 500)
    private String userRemark;
    /**
     * 用户所对应的角色列表
     */
    @ManyToMany(
            targetEntity = TdRole.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "users")
    private Set<TdRole> roles;
    @Column(length = 1)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<TdRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TdRole> roles) {
        this.roles = roles;
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

    public TdSubsection getSubsectionId() {
        return subsectionId;
    }

    public void setSubsectionId(TdSubsection subsectionId) {
        this.subsectionId = subsectionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdUser)) return false;

        TdUser tdUser = (TdUser) o;

        if (userId != null ? !userId.equals(tdUser.userId) : tdUser.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
