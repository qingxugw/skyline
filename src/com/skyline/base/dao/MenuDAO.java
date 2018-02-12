package com.skyline.base.dao;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.domain.TdUser;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-2-13
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public interface MenuDAO {
    public TdOperation HandOperation(String operationId);

    public TdOperation loadOperation(String parentId);

    public Set<TdOperation> HandTdOperationList( String menuLevel, String status);

    public Set<TdOperation> HandPrivateChildTree( String operationId, String status);

    public String changeBeforeIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version);
    public String changeAfterIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version);
    public String changeCurrentIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version);
    public String changeIndex(String operationId, String parentId, int menuIndex, int menuLevel, String oldParentId, int oldMenuIndex, int oldMenuLevel,int version);
}
