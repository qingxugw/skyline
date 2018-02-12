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
 * Date: 11-6-21
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TD_ROLE")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class TdRole extends BaseProperties{
    /**
     * 角色ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "roleId",length = 45)
    private String roleId;
    /**
     * 角色编号
     */
    @Column(length = 20)
    private String roleNo;
    /**
     * 角色名称
     */
    @Column(length = 100)
    private String roleName;
    @Column(length = 200)
    private String roleDesc;
    @Column(length = 1)
    private String status;
    /**
     * 角色所所对应的资源列表
     */
    @ManyToMany(
            targetEntity = TdOperation.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "TD_ROLE_OPERATION",
            joinColumns = {@JoinColumn(name = "ROLEID")},
            inverseJoinColumns = {@JoinColumn(name = "OPERATIONID")})
    private Set<TdOperation> operations;
    /**
     * 角色所对应的用户列表
     */
    @ManyToMany(
            targetEntity = TdUser.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "TD_USER_ROLE",
            joinColumns = {@JoinColumn(name = "ROLEID")},
            inverseJoinColumns = {@JoinColumn(name = "USERID")})
    private Set<TdUser> users;

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

    public Set<TdOperation> getOperations() {
        return operations;
    }

    public void setOperations(Set<TdOperation> operations) {
        this.operations = operations;
    }

    public Set<TdUser> getUsers() {
        return users;
    }

    public void setUsers(Set<TdUser> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdRole)) return false;

        TdRole tdRole = (TdRole) o;

        if (roleId != null ? !roleId.equals(tdRole.roleId) : tdRole.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return roleId != null ? roleId.hashCode() : 0;
    }
}
