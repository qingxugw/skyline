package com.skyline.base.action;

import com.skyline.base.dto.TdRoleDTO;
import com.skyline.base.service.RoleService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.BaseAction;
import com.skyline.pub.exception.CacheException;
import com.skyline.pub.utils.constants.Constants;
import com.skyline.pub.utils.enums.LogicEnumException;
import com.skyline.pub.utils.enums.ValidatorEnumException;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-24
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class RoleAction extends BaseAction{
    private String roleNo;   //角色编码
    private String roleName; //角色名称
    private String status;   //状态  启用 1 禁用 0
    private RoleService roleService;
    private int start;
    private int limit;
    private String roleId;
    private String roleDesc;
    private int version;
    private String opType;
    private TdRoleDTO dto = new TdRoleDTO();

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
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

    public TdRoleDTO getDto() {
        return dto;
    }

    public void setDto(TdRoleDTO dto) {
        this.dto = dto;
    }

    /**
     * 初始化角色管理页面
     * @return
     */
    public String initRolePage(){
        return SUCCESS;
    }

    /**
     * 查询角色列表
     * @return
     */
    public String queryRoleList(){
        List<TdRoleDTO> roleList = roleService.queryRoleList(roleNo,roleName,status,start,limit);
        int totalPorperty = roleService.queryRoleCount(roleNo,roleName,status);
        resultMap.put("root",roleList);
        resultMap.put("totalProperty",totalPorperty);
        return "resultMap";
    }

    /**
     * 保存角色
     * @return
     */
    public String saveRole(){
        try{
            //首先校验参数合法性
            if(StringUtils.isBlank(opType)||StringUtils.isBlank(roleNo)||StringUtils.isBlank(roleName)){
                throw new AppException(ValidatorEnumException.获取参数出错.getValue());
            }
            if(StringUtils.equals(Constants.ADD,opType)){
                dto =  roleService.saveRole(roleId, roleNo, roleName,roleDesc, status, version);
                dto.setSuccess(true);
                dto.setMsg(LogicEnumException.保存成功.getValue());
            }else if(StringUtils.equals(Constants.EDIT,opType)){
                if(StringUtils.isBlank(roleId)){
                    throw new AppException(ValidatorEnumException.获取参数出错.getValue());
                }
                dto =  roleService.updateRole(roleId, roleNo, roleName,roleDesc, status, version);
                dto.setSuccess(true);
                dto.setMsg(LogicEnumException.保存成功.getValue());
            }
        }catch (AppException app){
            dto.setSuccess(false);
            dto.setMsg(app.getMessage());
        }catch (HibernateOptimisticLockingFailureException state){
            dto.setSuccess(false);
            dto.setMsg(LogicEnumException.该对象已经被更改.getValue());
        }catch (RuntimeException run){
            run.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //todo 暂时没想到这里可以做什么
        }
        return "dto";
    }
    /**
     * 删除角色
     * @return
     */
    public String deleteRole(){
        try{
            //首先校验参数合法性
            if(StringUtils.isBlank(roleId)){
                throw new AppException(ValidatorEnumException.获取参数出错.getValue());
            }
            if(StringUtils.equals(Constants.DELETE,opType)){
                dto =  roleService.deleteRole(roleId,version);
                dto.setSuccess(true);
                dto.setMsg(LogicEnumException.删除成功.getValue());
            }
        }catch (AppException app){
            dto.setSuccess(false);
            dto.setMsg(app.getMessage());
        }catch (HibernateOptimisticLockingFailureException state){
            dto.setSuccess(false);
            dto.setMsg(LogicEnumException.该对象已经被更改.getValue());
        }catch (RuntimeException run){
            run.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //todo 暂时没想到这里可以做什么
        }
        return "dto";
    }
}
