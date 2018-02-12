package com.skyline.base.service;

import com.skyline.base.dto.TdRoleDTO;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.service.BaseService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-20
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public interface RoleService extends BaseService {
    /**
     * 查询角色列表
     * @param roleNo
     * @param roleName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdRoleDTO> queryRoleList(String roleNo, String roleName, String status, int start, int limit) throws AppException;

    /**
     * 查询角色记录总数
     * @param roleNo
     * @param roleName
     * @param status
     * @return
     */
    public Integer queryRoleCount(String roleNo, String roleName, String status) throws AppException;

    /**
     * 保存角色
     * @param roleId
     * @param roleNo
     * @param roleName
     * @param roleDesc
     * @param status
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     */
    public TdRoleDTO saveRole(String roleId, String roleNo, String roleName, String roleDesc, String status, int version) throws AppException, Exception;

    /**
     * 编辑角色
     * @param roleId
     * @param roleNo
     * @param roleName
     * @param roleDesc
     * @param status
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     */
    public TdRoleDTO updateRole(String roleId, String roleNo, String roleName, String roleDesc, String status, int version) throws AppException, Exception;

    /**
     * 根据roleId和version 删除角色对象
     * @param roleId
     * @param version
     * @throws com.skyline.pub.exception.AppException
     */
    public TdRoleDTO deleteRole(String roleId, int version) throws AppException;

    /**
     * 根据角色Id和菜单Id数组 来保存关联关系
     * @param roleId
     * @param ids
     * @param version
     * @return
     * @throws AppException
     */
    public TdRoleDTO saveRoleOperation(String roleId, String[] ids,int version) throws AppException;

    /**
     * 根据角色Id和菜单id数组 来保存角色用户的关联关系
     * @param roleId
     * @param ids
     * @param version
     * @return
     * @throws AppException
     */
    public TdRoleDTO saveRoleUser(String roleId, String[] ids,int version) throws AppException;

}
