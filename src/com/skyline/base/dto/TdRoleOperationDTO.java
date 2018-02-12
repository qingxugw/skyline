package com.skyline.base.dto;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-21
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
public class TdRoleOperationDTO extends BasePropertiesDTO {
    /**
     * 主键
     */
    private String id;
    private String operationId;
    /**
     * 上级菜单ID
     */
    private String parentId;
    /**
     * 菜单级别
     */
    private int menuLevel;
    /**
     * 菜单排序
     */
    private int menuIndex;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单路径
     */
    private String menuLink;
    /**
     * 菜单状态 是否可用
     */
    private String status;
    /**
     * 下级菜单
     */
    private Set<TdRoleOperationDTO> children;
    /**
     * ext 前端显示菜单使用
     */
    private String text;
    /**
     * ext 前端判断是否叶子节点使用
     */
    private boolean leaf;

    private boolean success;

    private String msg;

    private String iconCls;//菜单图标样式

    private String collapsedCls;//折叠图标样式

    private String expandedCls; //展开图标样式

    private boolean checked = false;
    private Set<TdRoleDTO> roles;

    public Set<TdRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<TdRoleDTO> roles) {
        this.roles = roles;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getExpandedCls() {
        return expandedCls;
    }

    public void setExpandedCls(String expandedCls) {
        this.expandedCls = expandedCls;
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

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Set<TdRoleOperationDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<TdRoleOperationDTO> children) {
        this.children = children;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getText(){
        return menuName;
    }

    public String getId() {
        return operationId;
    }

    public void setId(String id) {
        this.id = id;
    }
}
