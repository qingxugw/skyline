package com.skyline.base.action;

import com.skyline.base.domain.TdOperation;
import com.skyline.base.dto.TdOperationDTO;
import com.skyline.base.service.LoginService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.BaseAction;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.enums.ValidatorEnumException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

public class LoginAction extends BaseAction {
	private String username;
	private String password;
    private LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    private String operationId;
    private Set<TdOperationDTO> children = new HashSet<TdOperationDTO>();

    public Set<TdOperationDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<TdOperationDTO> children) {
        this.children = children;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

    /**
     * 登录系统
     * @return
     * @throws Exception
     */
	public String login() throws Exception {
        try{
            //首先校验参数合法性
            if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
                throw new AppException(ValidatorEnumException.用户名密码不能为空);
            }
            loginService.userLogin(username,password);
            return SUCCESS;
        }catch (AppException app){
            request.setAttribute("msg", app.getMessage());
            return ERROR;
        }catch (RuntimeException run){
            run.printStackTrace();
        }finally {
            //todo 暂时没想到这里可以做什么
        }
        return SUCCESS;
	}

    /**
     * 退出系统
     * @return
     * @throws Exception
     */
	public String logout(){
        try {
            loginService.logout();
            AppUtils.getSessionCache().removeIt(AppUtils.getRequest().getSession().getId());
            AppUtils.getRequest().getSession().invalidate();
            logger.info("[用户信息]从缓存中移除");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return SUCCESS;
	}
    /**
     * 才测试啊
     * @return
     * @throws Exception
     */
    public String ceshi() throws Exception {
        System.out.print(getSession().getUser().getUserName()+"============"+request.getSession().getId());
        return "children";
    }

    /**
     * 获得下级子菜单
     * @return
     * @throws Exception
     */
    public String privateChildTree() throws Exception {
        Set<TdOperation> childTree = loginService.privateChildTree(getSession().getUser().getUserId(), operationId, "1");
        children =  privateChildTreeDTO(childTree);
        return "children";
    }
    public Set<TdOperationDTO> privateChildTreeDTO(Set<TdOperation> childTree) throws  Exception {
        Set<TdOperationDTO> finalChildren = new HashSet<TdOperationDTO>();
        for(TdOperation operation:childTree){
            if(StringUtils.equals(operation.getStatus(),"0"))
                continue;
            TdOperationDTO   dto   = new TdOperationDTO();
            String[] ignoreProperties = {"parent","children","roles","createUser","modifyUser"};
            BeanUtils.copyProperties(operation,dto,ignoreProperties);
            //下面是最重要的一步 就是递归子菜单，并且返回是否叶子节点
            Set<TdOperation> dynamicChildren = loginService.privateChildTree(getSession().getUser().getUserId(), operation.getOperationId(), "1");
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
}
