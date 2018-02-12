package com.skyline.base.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-21
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TD_OPERATION")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class TdOperation extends BaseProperties {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(length = 45)
    private String operationId;
    /**
     * 上级菜单ID
     */
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private TdOperation parent;
    /**
     * 菜单级别
     */
    @Column(precision = 5)
    private int menuLevel;
    /**
     * 菜单排序
     */
    @Column(precision = 5)
    private int menuIndex;
    /**
     * 菜单名称
     */
    @Column(length = 200)
    private String menuName;
    /**
     * 菜单路径
     */
    @Column(length = 300)
    private String menuLink;
    /**
     * 菜单状态 是否可用
     */
    @Column(name="status",length = 1)
    private String status;
    /**
     * 下级菜单
     */
    @OneToMany(cascade =  {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "parent",fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @OrderBy(value = "menuIndex ASC")
    private Set<TdOperation> children = new HashSet<TdOperation>();
    /**
     * 资源所对应的角色列表
     */
    @ManyToMany(
            targetEntity = TdRole.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "operations")
    private Set<TdRole> roles;

    /**
     * 菜单状态 是否可用
     */
    @Column(name="iconCls",length = 50)
    private String iconCls;
    @Column(name="collapsedCls",length = 50)
    private String collapsedCls;//折叠图标样式
    @Column(name="expandedCls",length = 50)
    private String expandedCls; //展开图标样式

    public String getExpandedCls() {
        return expandedCls;
    }

    public void setExpandedCls(String expendedCls) {
        this.expandedCls = expendedCls;
    }

    public String getCollapsedCls() {
        return collapsedCls;
    }

    public void setCollapsedCls(String collapsedCls) {
        this.collapsedCls = collapsedCls;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Set<TdRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TdRole> roles) {
        this.roles = roles;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public TdOperation getParent() {
        return parent;
    }

    public void setParent(TdOperation parent) {
        this.parent = parent;
    }

    public int getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(int menuLevel) {
        this.menuLevel = menuLevel;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<TdOperation> getChildren() {
        return children;
    }

    public void setChildren(Set<TdOperation> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdOperation)) return false;

        TdOperation that = (TdOperation) o;

        if (operationId != null ? !operationId.equals(that.operationId) : that.operationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return operationId != null ? operationId.hashCode() : 0;
    }
}
