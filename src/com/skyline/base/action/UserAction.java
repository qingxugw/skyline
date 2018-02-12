package com.skyline.base.action;

import com.skyline.base.dto.TdUserDTO;
import com.skyline.base.service.UserService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.BaseAction;
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
public class UserAction extends BaseAction{
    private String userNo;   //登录名
    private String userName; //用户中文名
    private String status;   //状态  启用 1 禁用 0
    private UserService userService;
    private int start;
    private int limit;
    private String userId;
    private String password;
    private String subsectionId;
    private String userPhone;
    private String userTel;
    private String userMobile;
    private String userQQ;
    private String userMsn;
    private String userMail;
    private String userRemark;
    private int version;
    private String opType;
    private TdUserDTO dto = new TdUserDTO();

    public TdUserDTO getDto() {
        return dto;
    }

    public void setDto(TdUserDTO dto) {
        this.dto = dto;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubsectionId() {
        return subsectionId;
    }

    public void setSubsectionId(String subsectionId) {
        this.subsectionId = subsectionId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserQQ() {
        return userQQ;
    }

    public void setUserQQ(String userQQ) {
        this.userQQ = userQQ;
    }

    public String getUserMsn() {
        return userMsn;
    }

    public void setUserMsn(String userMsn) {
        this.userMsn = userMsn;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 初始化用户管理页面
     * @return
     */
    public String initUserPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 查询用户列表
     * @return
     */
    public String queryUserList(){
        List<TdUserDTO> userList = userService.queryUserList(userNo,userName,status,start,limit);
        int totalPorperty = userService.queryUserCount(userNo,userName,status);
        resultMap.put("root",userList);
        resultMap.put("totalProperty",totalPorperty);
        return "resultMap";
    }

    /**
     * 保存用户
     * @return
     */
    public String saveUser(){
        try{
            //首先校验参数合法性
            if(StringUtils.isBlank(opType)||StringUtils.isBlank(userNo)||StringUtils.isBlank(userName)){
                throw new AppException(ValidatorEnumException.获取参数出错.getValue());
            }
            if(StringUtils.equals(Constants.ADD,opType)){
                if(StringUtils.isBlank(password)){
                    throw new AppException(ValidatorEnumException.获取参数出错.getValue());
                }
                dto =  userService.saveUser(userId, userNo, userName, password, subsectionId,
                        userPhone, userTel, userMobile, userQQ, userMsn, userMail, userRemark, status, version);
                dto.setSuccess(true);
                dto.setMsg(LogicEnumException.保存成功.getValue());
            }else if(StringUtils.equals(Constants.EDIT,opType)){
                if(StringUtils.isBlank(userId)){
                    throw new AppException(ValidatorEnumException.获取参数出错.getValue());
                }
                dto =  userService.updateUser(userId, userNo, userName, password, subsectionId,
                        userPhone, userTel, userMobile, userQQ, userMsn, userMail, userRemark, status, version);
                dto.setSuccess(true);
                dto.setMsg(LogicEnumException.保存成功.getValue());
            }else if(StringUtils.equals(Constants.DELETE,opType)){
                if(StringUtils.isBlank(userId)){
                    throw new AppException(ValidatorEnumException.获取参数出错.getValue());
                }
                dto =  userService.deleteUser(userId,version);
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
    /**
     * 保存用户
     * @return
     */
    public String deleteUser(){
        try{
            //首先校验参数合法性
            if(StringUtils.isBlank(userId)){
                throw new AppException(ValidatorEnumException.获取参数出错.getValue());
            }
            if(StringUtils.equals(Constants.DELETE,opType)){
                dto =  userService.deleteUser(userId,version);
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
