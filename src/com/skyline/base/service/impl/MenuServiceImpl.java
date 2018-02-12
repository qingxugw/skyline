package com.skyline.base.service.impl;

import com.skyline.base.dao.MenuDAO;
import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdRole;
import com.skyline.base.dto.TdOperationDTO;
import com.skyline.base.dto.TdRoleDTO;
import com.skyline.base.dto.TdRoleOperationDTO;
import com.skyline.base.service.MenuService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.annotation.MethodRemark;
import com.skyline.pub.service.BaseServiceImpl;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.enums.ModuleEnum;
import com.skyline.pub.utils.enums.OpTypeEnum;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-13
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
    private MenuDAO menuDAO;

    public void setMenuDAO(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    /**
     * 获取菜单对象
     * @param operationId
     * @return
     */
    public TdOperation HandOperation(String operationId) {
        return menuDAO.HandOperation(operationId);
    }

    /**
     * 获取顶级菜单
     * @param menuLevel
     * @param status
     * @return
     */
    public Set<TdOperation> HandTdOperationList(String menuLevel,String status) {
        return menuDAO.HandTdOperationList(menuLevel,status);
    }

    /**
     * 根据菜单ID返回 当前菜单的下级菜单
     * @param operationId
     * @return
     */
    public Set<TdOperation> HandPrivateChildTree(String operationId,String status) {
        return menuDAO.HandPrivateChildTree(operationId,status);  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 返回需要选中的菜单列表
     *
     * @param operationId
     * @param status
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    public Set<TdRoleOperationDTO> HandPrivateChildCheckedTree(String operationId, String status,String roleId) throws AppException {
        Set<TdOperation> operationSet = menuDAO.HandPrivateChildTree(operationId,null);
        Set<TdRoleOperationDTO> children =  privateChildTreeDTO(operationSet,roleId);
        return children;
    }
    private Set<TdRoleOperationDTO> privateChildTreeDTO(Set<TdOperation> childTree,String roleId) throws  AppException {
        Set<TdRoleOperationDTO> finalChildren = new HashSet<TdRoleOperationDTO>();
        for(TdOperation operation:childTree){
            TdRoleOperationDTO   dto   = new TdRoleOperationDTO();
            String[] ignoreProperties = {"parent","children","roles","createUser","modifyUser"};
            BeanUtils.copyProperties(operation, dto, ignoreProperties);
            dto.setParentId(operation.getParent()==null?null:operation.getParent().getOperationId());
            //下面是最重要的一步 就是递归子菜单，并且返回是否叶子节点
            Set<TdOperation> dynamicChildren = operation.getChildren();
            if(!dynamicChildren.isEmpty()){
                dto.setChildren(privateChildTreeDTO(dynamicChildren,roleId));
                dto.setLeaf(false);
            }else{
                dto.setLeaf(true);
            }
            Set<TdRole> dynamicRoles = operation.getRoles();
            Set<TdRoleDTO> roleList = new HashSet<TdRoleDTO>();
            boolean flag = false;
            for(TdRole role:dynamicRoles){
                TdRoleDTO   roledto   = new TdRoleDTO();
                String[] roleIgnoreProperties = {"createUser","modifyUser","users","operations"};
                BeanUtils.copyProperties(role, roledto, roleIgnoreProperties);
                roleList.add(roledto);
                if(role.getRoleId().equals(roleId)){
                    flag = true;
                    break;
                }
            }
            if(flag) dto.setChecked(true); //如果角色列表中包含该角色，然后就设置菜单选中
            dto.setRoles(roleList);
            finalChildren.add(dto);
        }
        return finalChildren;
    }

    /**
     * 保存菜单对象
     *
     * @param operationId
     * @param parentId
     * @param menuName
     * @param menuLink
     * @param menuIndex
     * @param menuLevel
     * @param version
     * @param status
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.添加菜单,module = ModuleEnum.菜单管理)
    public TdOperationDTO saveMenu(String operationId, String parentId, String menuName, String menuLink,
                                   int menuIndex, int menuLevel, int version, String status,String iconCls,String expandedCls,String collapsedCls) throws AppException {
        TdOperation base = loadObject(TdOperation.class,parentId);
        TdOperation tdOperation = new TdOperation();
        tdOperation.setParent(base);
        tdOperation.setMenuName(menuName);
        tdOperation.setMenuLink(menuLink);
        tdOperation.setMenuIndex(menuIndex);
        tdOperation.setMenuLevel(menuLevel);
        tdOperation.setExpandedCls(expandedCls);
        tdOperation.setCollapsedCls(collapsedCls);
        tdOperation.setIconCls(iconCls);
        tdOperation.setVersion(version);
        tdOperation.setStatus(status);
        tdOperation.setCreateDate(new Date());
        tdOperation.setCreateUser(AppUtils.getCurrentUser());
        saveObject(tdOperation);
        TdOperationDTO dto   = new TdOperationDTO();
        String[] ignoreProperties = {"parent","children","roles","createUser","modifyUser"};
        BeanUtils.copyProperties(tdOperation, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(tdOperation.getCreateUser() ==null?null:tdOperation.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(tdOperation.getModifyUser() ==null?null:tdOperation.getModifyUser().getUserName());
        //设置父级ID
        dto.setParentId(tdOperation.getParent().getOperationId());
        //下面是最重要的一步 就是递归子菜单，并且返回是否叶子节点
        Set<TdOperation> dynamicChildren = tdOperation.getChildren();
        if(!dynamicChildren.isEmpty()){
            dto.setLeaf(false);
        }else{
            dto.setLeaf(true);
        }
        return dto;
    }
    /**
     * 保存菜单对象
     *
     * @param operationId
     * @param parentId
     * @param menuName
     * @param menuLink
     * @param menuIndex
     * @param menuLevel
     * @param version
     * @param status
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.编辑菜单,module = ModuleEnum.菜单管理)
    public TdOperationDTO updateMenu(String operationId, String parentId, String menuName, String menuLink,
                                     int menuIndex, int menuLevel, int version, String status,String iconCls,String expendedCls,String collapsedCls) throws AppException {
        TdOperation tdOperation = loadObject(TdOperation.class,operationId);
        tdOperation.setMenuName(menuName);
        tdOperation.setMenuLink(menuLink);
        tdOperation.setMenuIndex(menuIndex);
        tdOperation.setMenuLevel(menuLevel);
        tdOperation.setExpandedCls(expendedCls);
        tdOperation.setCollapsedCls(collapsedCls);
        tdOperation.setIconCls(iconCls);
        tdOperation.setVersion(version);
        tdOperation.setStatus(status);
        tdOperation.setModifyDate(new Date());
        tdOperation.setModifyUser(AppUtils.getCurrentUser());
        updateObject(tdOperation);
        TdOperationDTO dto   = new TdOperationDTO();
        String[] ignoreProperties = {"parent","children","roles","createUser","modifyUser"};
        BeanUtils.copyProperties(tdOperation, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(tdOperation.getCreateUser() ==null?null:tdOperation.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(tdOperation.getModifyUser() ==null?null:tdOperation.getModifyUser().getUserName());
        //设置父级ID
        dto.setParentId(tdOperation.getParent().getOperationId());
        //下面是最重要的一步 就是递归子菜单，并且返回是否叶子节点
        Set<TdOperation> dynamicChildren = tdOperation.getChildren();
        if(!dynamicChildren.isEmpty()){
            dto.setLeaf(false);
        }else{
            dto.setLeaf(true);
        }
        return dto;
    }

    /**
     * 删除菜单
     * @param operationId
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.删除菜单,module = ModuleEnum.菜单管理)
    public TdOperationDTO deleteMenu(String operationId, int version) throws AppException {
        TdOperation tdOperation = loadObject(TdOperation.class,operationId);
        tdOperation.setVersion(version);
        deleteObject(tdOperation);
        TdOperationDTO dto   = new TdOperationDTO();
        return dto;
    }

    /**
     * 拖动更改菜单顺序
     *
     * @param operationId
     * @param parentId
     * @param menuIndex
     * @param menuLevel
     * @param oldParentId
     * @param oldMenuIndex
     * @param oldMenuLevel
     * @return
     */
    @MethodRemark(opType = OpTypeEnum.菜单排序,module = ModuleEnum.菜单管理)
    public String changeIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version) {
        if(oldMenuLevel != menuLevel){
            menuDAO.changeBeforeIndex(operationId,parentId,menuIndex,menuLevel,oldParentId,oldMenuIndex,oldMenuLevel,version);
            menuDAO.changeAfterIndex(operationId,parentId,menuIndex,menuLevel,oldParentId,oldMenuIndex,oldMenuLevel,version);
        }else{
            menuDAO.changeCurrentIndex(operationId,parentId,menuIndex,menuLevel,oldParentId,oldMenuIndex,oldMenuLevel,version);
        }
        menuDAO.changeIndex(operationId,parentId,menuIndex,menuLevel,oldParentId,oldMenuIndex,oldMenuLevel,version);
        return  null;
    }
}
