package com.skyline.pub.utils.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-8-28
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public enum OpTypeEnum {
    添加菜单("ADD_MENU","添加菜单",ModuleEnum.菜单管理.getKey()),
    编辑菜单("EDIT_MENU","编辑菜单",ModuleEnum.菜单管理.getKey()),
    删除菜单("DELETE_MENU","删除菜单",ModuleEnum.菜单管理.getKey()),
    菜单排序("CHANG_MENU_INDEX","菜单排序",ModuleEnum.菜单管理.getKey()),
    用户登录("USER_LOGIN","用户登录",ModuleEnum.用户管理.getKey()),
    用户退出("USER_LOGOUT","用户退出",ModuleEnum.用户管理.getKey()),
    添加用户("ADD_USER","添加用户",ModuleEnum.用户管理.getKey()),
    编辑用户("EDIT_USER","编辑用户",ModuleEnum.用户管理.getKey()),
    删除用户("DELETE_USER","删除用户",ModuleEnum.用户管理.getKey()),
    添加角色("ADD_ROLE","添加角色",ModuleEnum.角色管理.getKey()),
    编辑角色("EDIT_ROLE","编辑角色",ModuleEnum.角色管理.getKey()),
    删除角色("DELETE_ROLE","删除角色",ModuleEnum.角色管理.getKey()),
    保存角色资源关联("SAVE_ROLE_OPERATION","保存角色资源关联",ModuleEnum.角色资源管理.getKey()),
    保存角色用户关联("SAVE_ROLE_USER","保存角色用户关联",ModuleEnum.角色用户管理.getKey()),
    无操作类型("NO_OPTYPE","无操作类型","no module")
    ;


    private String key;
    private String value;
    private String module;

    private OpTypeEnum(String key, String value,String module) {
        this.key = key;
        this.value = value;
        this.module = module;
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 根据key 获取value
     * @param id
     * @return
     */
    public static String getName(String id){
        String _name=null;
        OpTypeEnum[] state= values ();
        for(OpTypeEnum st:state){
            if(st.getKey().equals (id)){
                _name= st.name();
            }
        }
        return _name;
    }
    /**
     * 获取操作类型列表
     * @param
     * @return
     */
    public static List<Map<String,Object>> getOpTypeList(String module){
        List<Map<String,Object>>  modules = new ArrayList<Map<String, Object>>();
        OpTypeEnum[] state= values ();
        for(OpTypeEnum st:state){
            if(st.getModule().equals(module)){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("key",st.getKey());
                map.put("value",st.getValue());
                modules.add(map);
            }
        }
        return modules;
    }
}
