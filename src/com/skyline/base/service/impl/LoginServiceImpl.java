package com.skyline.base.service.impl;

import com.skyline.base.acl.Session;
import com.skyline.base.dao.UserDAO;
import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdUser;
import com.skyline.base.service.LoginService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.exception.CacheException;
import com.skyline.pub.annotation.MethodRemark;
import com.skyline.pub.service.BaseServiceImpl;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.DESPwdEncoder;
import com.skyline.pub.utils.enums.LogicEnumException;
import com.skyline.pub.utils.enums.ModuleEnum;
import com.skyline.pub.utils.enums.OpTypeEnum;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-13
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * 获取当前登录用户
     * @param username
     * @param password
     * @return
     */
    @MethodRemark(opType = OpTypeEnum.用户登录,module = ModuleEnum.用户管理)
    public TdUser userLogin(String username,String password) throws Exception {
        DESPwdEncoder desPwdEncoder = new DESPwdEncoder();
        TdUser temp = null;
        temp = userDAO.userLogin(username);
        if(temp == null){
            throw new AppException(LogicEnumException.用户不存在);
        }
        if(!desPwdEncoder.encrypt(password).equals(temp.getPassword())){
            throw new AppException(LogicEnumException.密码不正确);
        }
        //把用户登录信息放入缓存
        initSession(temp);
        return temp;
    }

    /**
     * 用户退出
     *
     * @throws Exception
     */
    @MethodRemark(opType = OpTypeEnum.用户退出,module = ModuleEnum.用户管理)
    public void logout() throws CacheException {

    }

    private void initSession(TdUser user){
        Session session = new Session();
        session.setUser(user);
        session.setIp(AppUtils.getRequest().getRemoteAddr());
        //获取tablist
        Set<TdOperation> tabList = getTdOperationList(user.getUserId(),"0","1");
        session.setTabList(tabList);
        //用户登录信息存入缓存
        AppUtils.getSessionCache().put(AppUtils.getRequest().getSession().getId(), session);
        logger.info("[用户信息]已经成功放入缓存");
    }

    /**
     * 获取用户所具有权限的系统菜单
     * @param userId
     * @param menuLevel
     * @param status
     * @return
     */
    public Set<TdOperation> getTdOperationList(String userId,String menuLevel,String status) {
        return userDAO.getTdOperationList(userId,menuLevel,status);
    }

    /**
     * 根据用户和菜单ID返回 当前菜单的下级菜单
     *
     * @param userId
     * @param operationId
     * @return
     */
    public Set<TdOperation> privateChildTree(String userId, String operationId,String status) {
        return userDAO.privateChildTree(userId,operationId,status);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
