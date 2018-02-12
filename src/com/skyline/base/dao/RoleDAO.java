package com.skyline.base.dao;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdRole;
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
public interface RoleDAO {
    /**
     * 查询角色
     * @param roleNo
     * @param roleName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<TdRole> queryRoleList(String roleNo, String roleName, String status, int start, int limit);

    /**
     * 查询满足条件的用户记录总数
     * @param roleNo
     * @param roleName
     * @param status
     * @return
     */
    public Integer queryRoleCount(String roleNo, String roleName, String status);

    /**
     * 根据roleno查询角色是否重复
     * @param roleNo
     * @param status
     * @return
     */
    public Integer queryRoleCount(String roleNo, String status);
}
