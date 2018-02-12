package com.skyline.pub.utils.enums;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-8-28
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public enum LogicEnumException implements BaseEnum{
    当前用户会话信息已失效("USER_SESSION_NOT_EXIST","当前用户会话信息已失效"),
    用户不存在("USER_NOT_EXIST","用户不存在"),
    密码不正确("PASSWORD_IS_NOT_RIGHT","密码不正确"),
    该对象已经被更改("OBJECT_ALREADY_MODIFY","该对象已经被更改"),
    保存失败("SAVE_ERROR","保存失败"),
    保存成功("SAVE_SUCCESS","保存成功"),
    删除成功("DELETE_SUCCESS","删除成功"),
    用户已经存在("USER_ALREADY_EXIST","用户已经存在"),
    菜单已经存在("MENU_ALREADY_EXIST","菜单已经存在"),
    角色已经存在("ROLE_ALREADY_EXIST","角色已经存在"),
    组织机构已经存在("ORG_ALREADY_EXIST","组织机构已经存在")
    ;


    private String key;
    private String value;

    private LogicEnumException(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据key 获取value
     * @param id
     * @return
     */
    public static String getName(String id){
        String _name=null;
        LogicEnumException[] state= values ();
        for(LogicEnumException st:state){
            if(st.getKey().equals (id)){
                _name= st.name();
            }
        }
        return _name;
    }
}
