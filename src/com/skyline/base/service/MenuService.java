package com.skyline.base.service;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.dto.TdOperationDTO;
import com.skyline.base.dto.TdRoleOperationDTO;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.service.BaseService;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-13
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public interface MenuService extends BaseService {
    /**
     * 根据菜单ID 返回菜单对象
     * @param operationId
     * @return
     */
    public TdOperation HandOperation(String operationId) throws AppException;

    /**
     * 根据菜单级别返回顶层菜单
     * @param menuLevel
     * @param status
     * @return
     */
    public Set<TdOperation> HandTdOperationList(String menuLevel, String status) throws AppException;

    /**
     * 根据用户和菜单ID返回 当前菜单的下级菜单
     * @param operationId
     * @return
     */
    public Set<TdOperation> HandPrivateChildTree(String operationId, String status) throws AppException;

    /**
     * 返回需要选中的菜单列表
     * @param operationId
     * @param status
     * @return
     * @throws AppException
     */
    public Set<TdRoleOperationDTO> HandPrivateChildCheckedTree(String operationId, String status,String roleId) throws AppException;

    /**
     * 保存菜单对象
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
     */
    public TdOperationDTO saveMenu(String operationId, String parentId, String menuName, String menuLink,
                                   int menuIndex, int menuLevel, int version, String status,String iconCls,String expendedCls,String collapsedCls) throws AppException;
    /**
     * 保存菜单对象
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
     */
    public TdOperationDTO updateMenu(String operationId, String parentId, String menuName, String menuLink,
                                     int menuIndex, int menuLevel, int version, String status,String iconCls,String expendedCls,String collapsedCls) throws AppException;

    /**
     * 删除菜单
     * @param operationId
     * @param version
     * @return
     * @throws AppException
     */
    public TdOperationDTO deleteMenu(String operationId,int version) throws AppException;

    /**
     * 拖动更改菜单顺序
     * @param operationId
     * @param parentId
     * @param menuIndex
     * @param menuLevel
     * @param oldParentId
     * @param oldMenuIndex
     * @param oldMenuLevel
     * @return
     */
    public String changeIndex(String operationId,String parentId,int menuIndex,int menuLevel,String oldParentId,int oldMenuIndex,int oldMenuLevel,int version);
}
