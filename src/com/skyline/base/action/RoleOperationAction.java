package com.skyline.base.action;

import com.skyline.base.dto.TdRoleDTO;
import com.skyline.base.dto.TdRoleOperationDTO;
import com.skyline.base.service.MenuService;
import com.skyline.base.service.RoleService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.BaseAction;
import com.skyline.pub.utils.enums.LogicEnumException;
import com.skyline.pub.utils.enums.ValidatorEnumException;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-24
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class RoleOperationAction extends BaseAction{
    private String roleNo;   //角色编码
    private String roleName; //角色名称
    private String status;   //状态  启用 1 禁用 0
    private RoleService roleService;
    private MenuService menuService;
    private int start;
    private int limit;
    private String roleId;
    private String roleDesc;
    private int version;
    private String opType;
    private Set<TdRoleOperationDTO> children;
    private String operationId;
    private String ids;//用来接收菜单id的字符串
    private TdRoleDTO dto = new TdRoleDTO();

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public String getIds() {
        return ids;
    }

    public TdRoleDTO getDto() {
        return dto;
    }

    public void setDto(TdRoleDTO dto) {
        this.dto = dto;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public Set<TdRoleOperationDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<TdRoleOperationDTO> children) {
        this.children = children;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    /**
     * 初始化角色管理页面
     * @return
     */
    public String initRoleOperationPage(){
        return SUCCESS;
    }

    /**
     * 查询资源列表
     * @return
     */
    public String queryOperationCheckedList(){
        children = menuService.HandPrivateChildCheckedTree(operationId,status,roleId);
        return "children";
    }

    /**
     * 保存角色菜单关联关系
     * @return
     */
    public String saveRoleOperation(){
        try{
            if(StringUtils.isBlank(roleId)){
                throw new AppException(ValidatorEnumException.获取参数出错);
            }
            String[] idArray = new String[ids.length()];
            idArray = ids.split(",");
            //调用保存方法
            dto = roleService.saveRoleOperation(roleId,idArray,version);
            dto.setSuccess(true);
            dto.setMsg(LogicEnumException.保存成功.getValue());
        }catch (AppException app){
            dto.setSuccess(false);
            dto.setMsg(app.getMessage());
        }catch (HibernateOptimisticLockingFailureException state){
            dto.setSuccess(false);
            dto.setMsg(LogicEnumException.该对象已经被更改.getValue());
        }finally {

        }
        return "dto";
    }
}
