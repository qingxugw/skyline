package com.skyline.base.service;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdUser;
import com.skyline.pub.exception.CacheException;
import com.skyline.pub.service.BaseService;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-13
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public interface LoginService extends BaseService {
    /**
     * 根据用户登录名返回TdUser对象
     * @param username
     * @param password
     * @return
     */
    public TdUser userLogin(String username,String password) throws Exception;

    /**
     * 用户退出
     * @throws Exception
     */
    public void logout() throws CacheException;

    /**
     * 根据用户返回用户所具有的系统菜单
     * @param userId
     * @param menuLevel
     * @param status
     * @return
     */
    public Set<TdOperation> getTdOperationList(String userId,String menuLevel,String status);

    /**
     * 根据用户和菜单ID返回 当前菜单的下级菜单
     * @param userId
     * @param operationId
     * @return
     */
    public Set<TdOperation> privateChildTree(String userId,String operationId,String status);
}
