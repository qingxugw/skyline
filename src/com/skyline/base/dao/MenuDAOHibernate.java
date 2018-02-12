package com.skyline.base.dao;

import com.skyline.base.domain.TdOperation;
import com.skyline.pub.dao.BaseDAOHibernate;
import org.apache.commons.lang.xwork.StringUtils;
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
public class MenuDAOHibernate extends BaseDAOHibernate implements MenuDAO{
    public TdOperation HandOperation(String operationId) {
            return null;
    }

    public Set<TdOperation> HandTdOperationList(String menuLevel, String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdOperation op ");
        sb.append(" join op.roles role join role.users user  ");
        sb.append(" where op.menuLevel = :menuLevel and op.status = :status ");
        sb.append(" order by op.menuIndex ASC ");
        Query query=session.createQuery(sb.toString());
        query.setString("menuLevel",menuLevel);
        query.setString("status",status);
        return new HashSet<TdOperation>(query.list());  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<TdOperation> HandPrivateChildTree(String operationId,String status) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append(" select op from TdOperation op where 1=1 ");
        if(StringUtils.isNotBlank(operationId)){
            sb.append(" and op.parent = :operationId ");
        }
        if(StringUtils.isNotBlank(status)){
            sb.append(" and op.status = :status ");
        }
        sb.append(" order by op.menuIndex ASC ");
        Query query=session.createQuery(sb.toString());
        if(StringUtils.isNotBlank(operationId)){
            query.setString("operationId",operationId);
        }
        if(StringUtils.isNotBlank(status)){
            query.setString("status",status);
        }
        releaseSession(session);
        return new HashSet<TdOperation>(query.list());
    }
    public TdOperation loadOperation(String parentId) {
        String hql = " from TdOperation t where t.operationId = ? and t.status = ? ";
        List list = getHibernateTemplate().find(hql,parentId,"1");
        if(list == null || list.isEmpty() || list.size() == 0){
            return null;
        }
        return (TdOperation)list.get(0);
    }

    public String changeBeforeIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        //首先更新原来位置同级节点的索引
        sb.append(" update from TdOperation op ");
        sb.append(" set op.menuIndex = op.menuIndex - 1");
        sb.append(" where op.parent = :oldParentId and op.menuIndex > :oldMenuIndex and op.menuLevel = :oldMenuLevel  and  op.status = :status ");
        Query query=session.createQuery(sb.toString());
        query.setString("oldParentId",oldParentId);
        query.setInteger("oldMenuIndex",oldMenuIndex);
        query.setInteger("oldMenuLevel",oldMenuLevel);
        query.setString("status","1");
        query.executeUpdate();
        return null;
    }
    public String changeAfterIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        //更新新位置的同级节点
        sb.append(" update from TdOperation op ");
        sb.append(" set op.menuIndex = op.menuIndex + 1");
        sb.append(" where op.parent = :parentId and op.menuIndex >= :menuIndex and op.menuLevel = :menuLevel  and  op.status = :status ");
        Query query=session.createQuery(sb.toString());
        query.setString("parentId",parentId);
        query.setInteger("menuIndex",menuIndex);
        query.setInteger("menuLevel",menuLevel);
        query.setString("status","1");
        query.executeUpdate();
        return null;
    }
    public String changeCurrentIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version) {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        //首先更新原来位置同级节点的索引
        sb.append(" update from TdOperation op ");
        sb.append(" set op.menuIndex = op.menuIndex - 1");
        sb.append(" where op.parent = :oldParentId and op.menuIndex <= :oldMenuIndex and op.menuIndex > :menuIndex and op.menuLevel = :oldMenuLevel  and  op.status = :status ");
        Query query=session.createQuery(sb.toString());
        query.setString("oldParentId",oldParentId);
        if (oldMenuIndex > menuIndex){
            query.setInteger("oldMenuIndex",oldMenuIndex);
            query.setInteger("menuIndex",menuIndex);
        }else{
            query.setInteger("oldMenuIndex",menuIndex);
            query.setInteger("menuIndex",oldMenuIndex);
        }
        query.setInteger("oldMenuLevel",oldMenuLevel);
        query.setString("status","1");
        query.executeUpdate();
        return null;
    }
    public String changeIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version) {
        TdOperation base = loadObject(TdOperation.class,parentId);
        TdOperation tdOperation =loadObject(TdOperation.class,operationId);
        tdOperation.setParent(base);
        tdOperation.setMenuIndex(menuIndex);
        tdOperation.setMenuLevel(menuLevel);
        tdOperation.setVersion(version);
        updateObject(tdOperation);
        return null;
    }
}
