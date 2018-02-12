package com.skyline.base.dao;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdUser;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-13
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {
    public TdUser userLogin(String username);

    public Set<TdOperation> getTdOperationList(String userId, String menuLevel, String status);

    public Set<TdOperation> privateChildTree(String userId, String operationId, String status);

    /**
     * 查询用户列表
     * @param userNo
     * @param userName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdUser> queryUserList(String userNo, String currentUserNo, String userName, String status, int start, int limit);

    /**
     * 查询满足条件的用户记录总数
     * @param userNo
     * @param userName
     * @param status
     * @return
     */
    public Integer queryUserCount(String userNo, String currentUserNo, String userName, String status);

    /**
     * 根据状态获取用户列表
     * @param status
     * @return
     */
    public List<TdUser> queryUserCheckedList(String status);

    /**
     * 查询用户
     * @param userNo
     * @param status
     * @return
     */
    public Integer queryUserCount(String userNo,String status);
}
