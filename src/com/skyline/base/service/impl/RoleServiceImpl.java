package com.skyline.base.service.impl;

import com.skyline.base.dao.RoleDAO;
import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdRole;
import com.skyline.base.domain.TdUser;
import com.skyline.base.dto.TdRoleDTO;
import com.skyline.base.service.RoleService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.annotation.MethodRemark;
import com.skyline.pub.service.BaseServiceImpl;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.enums.LogicEnumException;
import com.skyline.pub.utils.enums.ModuleEnum;
import com.skyline.pub.utils.enums.OpTypeEnum;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-20
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
    private RoleDAO roleDAO;

    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    /**
     * 查询角色列表
     *
     * @param roleNo
     * @param roleName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdRoleDTO> queryRoleList(String roleNo, String roleName, String status, int start, int limit) throws AppException {
        List<TdRole> roleList = roleDAO.queryRoleList(roleNo, roleName, status, start, limit);
        List<TdRoleDTO> finalList = new ArrayList<TdRoleDTO> ();
        for(TdRole role:roleList){
            TdRoleDTO   dto   = new TdRoleDTO();
            String[] ignoreProperties = {"createUser","modifyUser","users","operations"};
            BeanUtils.copyProperties(role, dto, ignoreProperties);

            //设置创建人
            dto.setCreateUser(role.getCreateUser() ==null?null:role.getCreateUser().getUserName());
            //设置修改人
            dto.setModifyUser(role.getModifyUser() == null ? null : role.getModifyUser().getUserName());
            finalList.add(dto);
        }
        return finalList;
    }

    /**
     * 查询角色记录总数
     *
     * @param roleNo
     * @param roleName
     * @param status
     * @return
     */
    public Integer queryRoleCount(String roleNo, String roleName, String status) throws AppException {
        Integer count = roleDAO.queryRoleCount(roleNo, roleName, status);
        return count;
    }

    /**
     * 保存角色
     *
     * @param roleId
     * @param roleNo
     * @param roleName
     * @param status
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.添加角色,module = ModuleEnum.角色管理)
    public TdRoleDTO saveRole(String roleId, String roleNo, String roleName, String roleDesc, String status, int version) throws AppException, Exception {
        int count = roleDAO.queryRoleCount(roleNo,null);
        if(count > 0) throw new AppException(LogicEnumException.角色已经存在);
        TdRole role = new TdRole();
        role.setRoleNo(roleNo);
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setVersion(version);
        role.setStatus(status);
        role.setCreateDate(new Date());
        role.setCreateUser(AppUtils.getCurrentUser());
        saveObject(role);
        TdRoleDTO dto = new TdRoleDTO();
        String[] ignoreProperties = {"createUser","modifyUser","users","operations"};
        BeanUtils.copyProperties(role, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(role.getCreateUser() == null ? null : role.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(role.getModifyUser() == null ? null : role.getModifyUser().getUserName());
        return dto;
    }

    /**
     * 编辑角色
     *
     * @param roleId
     * @param roleNo
     * @param roleName
     * @param status
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.编辑角色,module = ModuleEnum.角色管理)
    public TdRoleDTO updateRole(String roleId, String roleNo, String roleName, String roleDesc, String status, int version) throws AppException, Exception {
        TdRole role = loadObject(TdRole.class,roleId);
        role.setRoleNo(roleNo);
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setVersion(version);
        role.setStatus(status);
        role.setModifyDate(new Date());
        role.setModifyUser(AppUtils.getCurrentUser());
        updateObject(role);
        TdRoleDTO dto = new TdRoleDTO();
        String[] ignoreProperties = {"createUser","modifyUser","users","operations"};
        BeanUtils.copyProperties(role, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(role.getCreateUser() == null ? null : role.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(role.getModifyUser() == null ? null : role.getModifyUser().getUserName());
        return dto;
    }

    /**
     * 根据roleId和version 删除角色对象
     *
     * @param roleId
     * @param version
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.删除角色,module = ModuleEnum.角色管理)
    public TdRoleDTO deleteRole(String roleId, int version) throws AppException {
        TdRole role = loadObject(TdRole.class,roleId);
        role.setVersion(version);
        deleteObject(role);
        return  new TdRoleDTO();
    }

    /**
     * 根据角色Id和菜单Id数组 来保存关联关系
     *
     * @param roleId
     * @param ids
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.保存角色资源关联,module = ModuleEnum.角色资源管理)
    public TdRoleDTO saveRoleOperation(String roleId, String[] ids, int version) throws AppException {
        TdRole role = loadObject(TdRole.class,roleId);
        role.setVersion(version);
        Set<TdOperation> operationSet = new HashSet<TdOperation>();
        if(ids.length !=0){
            for(String id:ids){
                if(!"".equals(id)&&id !=null&&!"-1".equals(id)){
                    TdOperation operation = loadObject(TdOperation.class,id);
                    operationSet.add(operation);
                }
            }
        }else{
            operationSet = role.getOperations();
            operationSet.clear();
        }
        role.setOperations(operationSet);
        updateObject(role);
        TdRoleDTO dto = new TdRoleDTO();
        String[] ignoreProperties = {"createUser","modifyUser","users","operations"};
        BeanUtils.copyProperties(role, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(role.getCreateUser() == null ? null : role.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(role.getModifyUser() == null ? null : role.getModifyUser().getUserName());
        return dto;
    }

    /**
     * 根据角色Id和菜单id数组 来保存角色用户的关联关系
     *
     * @param roleId
     * @param ids
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.保存角色用户关联,module = ModuleEnum.角色用户管理)
    public TdRoleDTO saveRoleUser(String roleId, String[] ids, int version) throws AppException {
        TdRole role = loadObject(TdRole.class,roleId);
        role.setVersion(version);
        Set<TdUser> userSet = new HashSet<TdUser>();
        if(ids.length !=0){
            for(String id:ids){
                if(!"".equals(id)&&id !=null){
                    TdUser user = loadObject(TdUser.class,id);
                    userSet.add(user);
                }
            }
        }else{
            userSet = role.getUsers();
            userSet.clear();
        }
        role.setUsers(userSet);
        updateObject(role);
        TdRoleDTO dto = new TdRoleDTO();
        String[] ignoreProperties = {"createUser","modifyUser","users","operations"};
        BeanUtils.copyProperties(role, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(role.getCreateUser() == null ? null : role.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(role.getModifyUser() == null ? null : role.getModifyUser().getUserName());
        return dto;
    }
}
