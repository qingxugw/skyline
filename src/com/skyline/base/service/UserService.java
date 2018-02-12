package com.skyline.base.service;

import com.skyline.base.dto.TdRoleUserDTO;
import com.skyline.base.dto.TdUserDTO;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.service.BaseService;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-20
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public interface UserService extends BaseService {
    /**
     * 查询用户列表
     * @param userNo
     * @param userName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdUserDTO> queryUserList(String userNo, String userName, String status, int start, int limit) throws AppException;

    /**
     * 查询用户记录总数
     * @param userNo
     * @param userName
     * @param status
     * @return
     */
    public Integer queryUserCount(String userNo, String userName, String status) throws AppException;

    /**
     * 保存用户
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
     * @throws AppException
     */
    public TdUserDTO saveUser(String userId,String userNo,String userName,String password,
                              String subsectionId,String userPhone,String userTel,String userMobile,
                              String userQQ,String userMsn,String userMail,String userRemark,String status,int version) throws AppException, Exception;

    /**
     * 编辑用户
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
     * @throws AppException
     */
    public TdUserDTO updateUser(String userId,String userNo,String userName,String password,
                                String subsectionId,String userPhone,String userTel,String userMobile,
                                String userQQ,String userMsn,String userMail,String userRemark,String status,int version) throws AppException;

    /**
     * 根据userId和version 删除用户对象
     * @param userId
     * @param version
     * @throws AppException
     */
    public TdUserDTO deleteUser(String userId,int version) throws AppException;

    /**
     * 根据角色ID 和状态来查询用户列表
     * @param roleId
     * @param status
     * @return
     * @throws AppException
     */
    public Set<TdRoleUserDTO> queryUserCheckedList(String roleId,String status) throws AppException;
}
