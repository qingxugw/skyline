package com.skyline.base.dao;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdUser;
import com.skyline.pub.dao.BaseDAOHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-13
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public class UserDAOHibernate extends BaseDAOHibernate implements UserDAO{
    public TdUser userLogin(String username) {
        String hql = " from TdUser t where t.userNo = ? ";
        List list = getHibernateTemplate().find(hql,username);
        if(list == null || list.isEmpty() || list.size() == 0){
            return null;
        }
        return (TdUser)list.get(0);
    }

    public Set<TdOperation> getTdOperationList(String userId, String menuLevel, String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdOperation op ");
        sb.append(" join op.roles role join role.users user  ");
        sb.append(" where user.userId = :userId and op.menuLevel = :menuLevel and op.status = :status ");
        Query query=session.createQuery(sb.toString());
        query.setString("userId",userId);
        query.setString("menuLevel",menuLevel);
        query.setString("status",status);
        return new HashSet<TdOperation>(query.list());  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<TdOperation> privateChildTree(String userId, String operationId,String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdOperation op,TdRole role,TdUser user ");
        sb.append(" where user.userId in elements(role.users) and role.roleId in elements(op.roles)  ");
        sb.append(" and user.userId = :userId and op.parent = :operationId and op.status = :status ");
        Query query=session.createQuery(sb.toString());
        query.setString("userId",userId);
        query.setString("operationId",operationId);
        query.setString("status",status);
        return new HashSet<TdOperation>(query.list());    //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 查询用户列表
     *
     * @param userNo
     * @param userName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdUser> queryUserList(String userNo,String currentUserNo, String userName, String status, int start, int limit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdUser op where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and op.userNo = :userNo ");
        }
        if (StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        if (StringUtils.isNotBlank(userName)){
            sb.append(" and op.userName like :userName ");
        }
        sb.append(" and  op.userNo !=:currentUserNo ");
        sb.append(" order by op.userNo ");
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(status)){
            query.setString("status", status);
        }
        if (StringUtils.isNotBlank(userName)){
            query.setString("userName", "%" + userName + "%");
        }
        query.setString("currentUserNo", currentUserNo);
        return  query.setFirstResult(start).setMaxResults(limit).list();
    }

    /**
     * 查询满足条件的用户记录总数
     *
     * @param userNo
     * @param userName
     * @param status
     * @return
     */
    public Integer queryUserCount(String userNo,String currentUserNo, String userName, String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(op) from TdUser op where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and op.userNo = :userNo ");
        }
        if (StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        if (StringUtils.isNotBlank(userName)){
            sb.append(" and op.userName like :userName ");
        }
        sb.append(" and  op.userNo !=:currentUserNo ");
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(status)){
            query.setString("status", status);
        }
        if (StringUtils.isNotBlank(userName)){
            query.setString("userName", "%" + userName + "%");
        }
        query.setString("currentUserNo", currentUserNo);
        return  ((Long)query.uniqueResult()).intValue();
    }

    /**
     * 根据状态获取用户列表
     * @param status
     * @return
     */
    public List<TdUser> queryUserCheckedList(String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdUser op where 1=1 ");
        if (StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        sb.append(" order by op.userNo ");
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(status)){
            query.setString("status", status);
        }
        return  query.list();
    }

    /**
     * 查询用户
     *
     * @param userNo
     * @param status
     * @return
     */
    public Integer queryUserCount(String userNo, String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(op) from TdUser op where 1=1 ");
        if (StringUtils.isNotBlank(userNo)){
            sb.append(" and op.userNo = :userNo ");
        }
        if (StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(userNo)){
            query.setString("userNo", userNo);
        }
        if (StringUtils.isNotBlank(status)){
            query.setString("status", status);
        }
        return  ((Long)query.uniqueResult()).intValue();
    }
}
