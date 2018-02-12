package com.skyline.base.service.impl;

import com.skyline.base.dao.UserDAO;
import com.skyline.base.domain.TdRole;
import com.skyline.base.domain.TdSubsection;
import com.skyline.base.domain.TdUser;
import com.skyline.base.dto.TdRoleUserDTO;
import com.skyline.base.dto.TdUserDTO;
import com.skyline.base.service.UserService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.annotation.MethodRemark;
import com.skyline.pub.service.BaseServiceImpl;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.DESPwdEncoder;
import com.skyline.pub.utils.enums.LogicEnumException;
import com.skyline.pub.utils.enums.ModuleEnum;
import com.skyline.pub.utils.enums.OpTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-20
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    /**
     * 查询用户列表
     * @param userNo
     * @param userName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdUserDTO> queryUserList(String userNo, String userName, String status, int start, int limit) {
        List<TdUser> userList = userDAO.queryUserList(userNo,AppUtils.getCurrentUser().getUserNo(), userName, status, start, limit);
        List<TdUserDTO> finalList = new ArrayList<TdUserDTO> ();
        for(TdUser user:userList){
            TdUserDTO   dto   = new TdUserDTO();
            String[] ignoreProperties = {"createUser","modifyUser","roles","subsectionId","subsectionName"};
            BeanUtils.copyProperties(user, dto, ignoreProperties);
            //设置部门Id
            dto.setSubsectionId(user.getSubsectionId() ==null?null:user.getSubsectionId().getSubsectionId());
//            //设置部门名称
            dto.setSubsectionName(user.getSubsectionId() == null ? null : user.getSubsectionId().getSubsectionName());
            //设置创建人
            dto.setCreateUser(user.getCreateUser() ==null?null:user.getCreateUser().getUserName());
            //设置修改人
            dto.setModifyUser(user.getModifyUser() == null ? null : user.getModifyUser().getUserName());
            finalList.add(dto);
        }
        return finalList;
    }

    /**
     * 查询用户记录总数
     * @param userNo
     * @param userName
     * @param status
     * @return
     */
    public Integer queryUserCount(String userNo, String userName, String status) {
        Integer count = userDAO.queryUserCount(userNo,AppUtils.getCurrentUser().getUserNo(),userName,status);
        return count;
    }

    /**
     * 保存用户
     *
     * @param userId
     * @param userNo
     * @param userName
     * @param password
     * @param subsectionId
     * @param userPhone
     * @param userTel
     * @param userMobile
     * @param userQQ
     * @param userMsn
     * @param userMail
     * @param userRemark
     * @param status
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.添加用户,module = ModuleEnum.用户管理)
    public TdUserDTO saveUser(String userId, String userNo, String userName, String password, String subsectionId,
                              String userPhone, String userTel, String userMobile, String userQQ, String userMsn,
                              String userMail, String userRemark, String status, int version) throws AppException,Exception {
        //首先查询用户名是否重复
        int count = userDAO.queryUserCount(userNo,status);
        if(count > 0){
            throw new AppException(LogicEnumException.用户已经存在);
        }
        DESPwdEncoder desPwdEncoder = null;
        try {
            desPwdEncoder = new DESPwdEncoder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TdSubsection subsection = null;
        if(StringUtils.isNotBlank(subsectionId)){
            //如果subsectionId 不为空 去查询分部对象
            subsection = loadObject(TdSubsection.class,subsectionId);
        }
        TdUser user = new TdUser();
        user.setUserNo(userNo);
        user.setUserName(userName);
        user.setPassword(desPwdEncoder.encrypt(password));
        user.setSubsectionId(subsection);
        user.setUserPhone(userPhone);
        user.setUserTel(userTel);
        user.setUserMobile(userMobile);
        user.setUserQQ(userQQ);
        user.setUserMsn(userMsn);
        user.setUserMail(userMail);
        user.setUserRemark(userRemark);
        user.setVersion(version);
        user.setStatus(status);
        user.setCreateDate(new Date());
        user.setCreateUser(AppUtils.getCurrentUser());
        saveObject(user);
        TdUserDTO dto = new TdUserDTO();
        String[] ignoreProperties = {"subsectionId","roles","createUser","modifyUser"};
        BeanUtils.copyProperties(user, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(user.getCreateUser() == null ? null : user.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(user.getModifyUser() == null ? null : user.getModifyUser().getUserName());
        //设置部门ID
        dto.setSubsectionId(user.getSubsectionId() == null ? null : user.getSubsectionId().getSubsectionId());
        return dto;
    }

    /**
     * 编辑用户
     *
     * @param userId
     * @param userNo
     * @param userName
     * @param password
     * @param subsectionId
     * @param userPhone
     * @param userTel
     * @param userMobile
     * @param userQQ
     * @param userMsn
     * @param userMail
     * @param userRemark
     * @param status
     * @param version
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.编辑用户,module = ModuleEnum.用户管理)
    public TdUserDTO updateUser(String userId, String userNo, String userName, String password,
                                String subsectionId, String userPhone, String userTel, String userMobile,
                                String userQQ, String userMsn, String userMail, String userRemark, String status, int version) throws AppException {
        TdSubsection subsection = null;
        if(StringUtils.isNotBlank(subsectionId)){
            //如果subsectionId 不为空 去查询分部对象
            subsection = loadObject(TdSubsection.class,subsectionId);
        }
        TdUser user = loadObject(TdUser.class,userId);
        user.setUserNo(userNo);
        user.setUserName(userName);
        user.setSubsectionId(subsection);
        user.setUserPhone(userPhone);
        user.setUserTel(userTel);
        user.setUserMobile(userMobile);
        user.setUserQQ(userQQ);
        user.setUserMsn(userMsn);
        user.setUserMail(userMail);
        user.setUserRemark(userRemark);
        user.setVersion(version);
        user.setStatus(status);
        user.setModifyDate(new Date());
        user.setModifyUser(AppUtils.getCurrentUser());
        updateObject(user);
        TdUserDTO dto = new TdUserDTO();
        String[] ignoreProperties = {"subsectionId","roles","createUser","modifyUser"};
        BeanUtils.copyProperties(user, dto, ignoreProperties);
        //设置创建人
        dto.setCreateUser(user.getCreateUser() == null ? null : user.getCreateUser().getUserName());
        //设置修改人
        dto.setModifyUser(user.getModifyUser() == null ? null : user.getModifyUser().getUserName());
        //设置部门ID
        dto.setSubsectionId(user.getSubsectionId() == null ? null : user.getSubsectionId().getSubsectionId());
        return dto;
    }

    /**
     * 根据userId和version 删除用户对象
     *
     * @param userId
     * @param version
     * @throws com.skyline.pub.exception.AppException
     *
     */
    @MethodRemark(opType = OpTypeEnum.删除用户,module = ModuleEnum.用户管理)
    public TdUserDTO deleteUser(String userId, int version) throws AppException {
        TdUser user = loadObject(TdUser.class,userId);
        user.setVersion(version);
        deleteObject(user);
        return  new TdUserDTO();
    }

    /**
     * 根据角色ID 和状态来查询用户列表
     *
     * @param roleId
     * @param status
     * @return
     * @throws com.skyline.pub.exception.AppException
     *
     */
    public Set<TdRoleUserDTO> queryUserCheckedList(String roleId, String status) throws AppException {
        List<TdUser> userList = userDAO.queryUserCheckedList(status);
        Set<TdRoleUserDTO> finalList = new HashSet<TdRoleUserDTO>();
        for(TdUser user:userList){
            TdRoleUserDTO   dto   = new TdRoleUserDTO();
            String[] ignoreProperties = {"createUser","modifyUser","roles","subsectionId","subsectionName"};
            BeanUtils.copyProperties(user, dto, ignoreProperties);
            Set<TdRole> roleSet = user.getRoles();
            boolean flag = false;
            if(StringUtils.isNotBlank(roleId)){
                for(TdRole role:roleSet){
                    if(role.getRoleId().equals(roleId)){
                        flag = true;
                        break;
                    }
                }
            }
            dto.setChecked(flag);
            //设置部门Id
            dto.setSubsectionId(user.getSubsectionId() ==null?null:user.getSubsectionId().getSubsectionId());
//            //设置部门名称
            dto.setSubsectionName(user.getSubsectionId() == null ? null : user.getSubsectionId().getSubsectionName());
            //设置创建人
            dto.setCreateUser(user.getCreateUser() ==null?null:user.getCreateUser().getUserName());
            //设置修改人
            dto.setModifyUser(user.getModifyUser() == null ? null : user.getModifyUser().getUserName());
            finalList.add(dto);
        }
        return finalList;
    }
}
