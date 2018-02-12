package com.skyline.base.action;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.dto.TdOperationDTO;
import com.skyline.base.service.MenuService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.BaseAction;
import com.skyline.pub.utils.constants.Constants;
import com.skyline.pub.utils.enums.LogicEnumException;
import com.skyline.pub.utils.enums.ValidatorEnumException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import java.util.HashSet;
import java.util.Set;

public class MenuAction extends BaseAction {
    private MenuService menuService;
    private String operationId;
    private Set<TdOperationDTO> children = new HashSet<TdOperationDTO>();
    private String parentId;
    private String menuName;
    private String menuLink;
    private int menuIndex;
    private int menuLevel;
    private int version;
    private String status;
    private String opType;
    private TdOperationDTO dto = new TdOperationDTO();
    private String oldParentId;
    private int oldMenuIndex;
    private int oldMenuLevel;
    private String iconCls;
    private String collapsedCls;//折叠图标样式
    private String expandedCls; //展开图标样式

    public String getCollapsedCls() {
        return collapsedCls;
    }

    public void setCollapsedCls(String collapsedCls) {
        this.collapsedCls = collapsedCls;
    }

    public String getExpandedCls() {
        return expandedCls;
    }

    public void setExpandedCls(String expandedCls) {
        this.expandedCls = expandedCls;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getOldParentId() {
        return oldParentId;
    }

    public void setOldParentId(String oldParentId) {
        this.oldParentId = oldParentId;
    }

    public int getOldMenuIndex() {
        return oldMenuIndex;
    }

    public void setOldMenuIndex(int oldMenuIndex) {
        this.oldMenuIndex = oldMenuIndex;
    }

    public int getOldMenuLevel() {
        return oldMenuLevel;
    }

    public void setOldMenuLevel(int oldMenuLevel) {
        this.oldMenuLevel = oldMenuLevel;
    }

    public TdOperationDTO getDto() {
        return dto;
    }

    public void setDto(TdOperationDTO dto) {
        this.dto = dto;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public Set<TdOperationDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<TdOperationDTO> children) {
        this.children = children;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }

    public int getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(int menuLevel) {
        this.menuLevel = menuLevel;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    /**
     * 初始化菜单页面
     * @return
     * @throws Exception
     */
	public String InitMenuPage() throws Exception {
        return SUCCESS;
	}

    /**
     * 获得下级子菜单
     * @return
     * @throws Exception
     */
    public String privateChildTree() throws Exception {
        Set<TdOperation> childTree = menuService.HandPrivateChildTree(operationId, "1");
        children =  privateChildTreeDTO(childTree);
        return "children";
    }
    public Set<TdOperationDTO> privateChildTreeDTO(Set<TdOperation> childTree) throws  Exception {
        Set<TdOperationDTO> finalChildren = new HashSet<TdOperationDTO>();
        for(TdOperation operation:childTree){
            TdOperationDTO   dto   = new TdOperationDTO();
            String[] ignoreProperties = {"parent","children","roles","createUser","modifyUser"};
            BeanUtils.copyProperties(operation,dto,ignoreProperties);
            //设置创建人
            dto.setCreateUser(operation.getCreateUser() ==null?null:operation.getCreateUser().getUserName());
            //设置修改人
            dto.setModifyUser(operation.getModifyUser() ==null?null:operation.getModifyUser().getUserName());
            //设置父级ID
            dto.setParentId(operation.getParent().getOperationId());
            //下面是最重要的一步 就是递归子菜单，并且返回是否叶子节点
            Set<TdOperation> dynamicChildren = operation.getChildren();
            if(!dynamicChildren.isEmpty()){
                dto.setChildren(privateChildTreeDTO(dynamicChildren));
                dto.setLeaf(false);
            }else{
                dto.setLeaf(true);
            }
            finalChildren.add(dto);
        }
        return finalChildren;
    }

    /**
     * 保存菜单 根据opType 判断是新增还是编辑
     * @return
     */
    public String saveMenu() {
        try{
            //首先校验参数合法性
            if(StringUtils.isBlank(opType)){
                throw new AppException(ValidatorEnumException.获取参数出错.getValue());
            }
            if(StringUtils.equals(Constants.ADD,opType)){
                if(StringUtils.isBlank(menuName)||StringUtils.isBlank(parentId)){
                    throw new AppException(ValidatorEnumException.获取参数出错.getValue());
                }
                dto =  menuService.saveMenu(operationId,parentId,menuName,menuLink,
                                                        menuIndex,menuLevel,version,status,iconCls, expandedCls,collapsedCls);
                dto.setSuccess(true);
                dto.setMsg(LogicEnumException.保存成功.getValue());
            }else if(StringUtils.equals(Constants.EDIT,opType)){
                if(StringUtils.isBlank(menuName)||StringUtils.isBlank(parentId)){
                    throw new AppException(ValidatorEnumException.获取参数出错.getValue());
                }
                dto =  menuService.updateMenu(operationId, parentId, menuName, menuLink,
                        menuIndex, menuLevel, version, status,iconCls, expandedCls,collapsedCls);
                dto.setSuccess(true);
                dto.setMsg(LogicEnumException.保存成功.getValue());
            }else if(StringUtils.equals(Constants.DELETE,opType)){
                if(StringUtils.isBlank(operationId)){
                    throw new AppException(ValidatorEnumException.获取参数出错.getValue());
                }
                dto =  menuService.deleteMenu(operationId, version);
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
            System.out.println("运行异常抛出");
            run.printStackTrace();
        }finally {
            //todo 暂时没想到这里可以做什么
        }
        return "dto";
    }

    /**
     * 更改索引排序
     * @return
     */
    public String changeIndex() {
        try{
            //首先校验参数合法性
            if(StringUtils.isBlank(operationId)||StringUtils.isBlank(parentId)
                    ||StringUtils.isBlank(oldParentId)|| StringUtils.isBlank(String.valueOf(menuIndex))
                    || StringUtils.isBlank(String.valueOf(menuLevel))|| StringUtils.isBlank(String.valueOf(oldMenuIndex))
                    || StringUtils.isBlank(String.valueOf(oldMenuLevel))|| StringUtils.isBlank(String.valueOf(version))){
                throw new AppException(ValidatorEnumException.获取参数出错.getValue());
            }
            menuService.changeIndex(operationId,parentId,menuIndex,menuLevel,oldParentId,oldMenuIndex,oldMenuLevel,version);
            resultMap.put("success",true);
            resultMap.put("msg","操作成功");
        }catch (AppException app){
            resultMap.put("success",false);
            resultMap.put("msg",app.getMessage());
        }catch (HibernateOptimisticLockingFailureException state){
            resultMap.put("success",false);
            resultMap.put("msg",LogicEnumException.该对象已经被更改.getValue());
        }catch (RuntimeException run){
            run.printStackTrace();
        }finally {
            //todo 暂时没想到这里可以做什么
        }
        return "resultMap";
    }
}
