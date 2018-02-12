package com.skyline.base.dao;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdRole;
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
public class RoleDAOHibernate extends BaseDAOHibernate implements RoleDAO{
    /**
     * 查询角色
     *
     * @param roleNo
     * @param roleName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdRole> queryRoleList(String roleNo, String roleName, String status, int start, int limit) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdRole op where 1=1 ");
        if (StringUtils.isNotBlank(roleNo)){
            sb.append(" and op.roleNo = :roleNo ");
        }
        if (StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        if (StringUtils.isNotBlank(roleName)){
            sb.append(" and op.roleName like :roleName ");
        }
        sb.append(" order by op.roleNo ");
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(roleNo)){
            query.setString("roleNo", roleNo);
        }
        if (StringUtils.isNotBlank(status)){
            query.setString("status", status);
        }
        if (StringUtils.isNotBlank(roleName)){
            query.setString("roleName", "%" + roleName + "%");
        }
        return  query.setFirstResult(start).setMaxResults(limit).list();
    }

    /**
     * 查询满足条件的用户记录总数
     *
     * @param roleNo
     * @param roleName
     * @param status
     * @return
     */
    public Integer queryRoleCount(String roleNo, String roleName, String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(op) from TdRole op where 1=1 ");
        if (StringUtils.isNotBlank(roleNo)){
            sb.append(" and op.roleNo = :roleNo ");
        }
        if (StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        if (StringUtils.isNotBlank(roleName)){
            sb.append(" and op.roleName like :roleName ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(roleNo)){
            query.setString("roleNo", roleNo);
        }
        if (StringUtils.isNotBlank(status)){
            query.setString("status", status);
        }
        if (StringUtils.isNotBlank(roleName)){
            query.setString("roleName", "%" + roleName + "%");
        }
        return  ((Long)query.uniqueResult()).intValue();
    }

    /**
     * 根据roleno查询角色是否重复
     *
     * @param roleNo
     * @param status
     * @return
     */
    public Integer queryRoleCount(String roleNo, String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(op) from TdRole op where 1=1 ");
        if (StringUtils.isNotBlank(roleNo)){
            sb.append(" and op.roleNo = :roleNo ");
        }
        if (StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        Query query=session.createQuery(sb.toString());
        if (StringUtils.isNotBlank(roleNo)){
            query.setString("roleNo", roleNo);
        }
        if (StringUtils.isNotBlank(status)){
            query.setString("status", status);
        }
        return  ((Long)query.uniqueResult()).intValue();
    }
}
