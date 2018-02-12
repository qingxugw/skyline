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
public enum ModuleEnum {
    菜单管理("MENU_MANAGE","菜单管理"),
    用户管理("USER_MANAGE","用户管理"),
    角色管理("ROLE_MANAGE","角色管理"),
    角色资源管理("ROLE_OPERATION_MANAGE","角色资源管理"),
    角色用户管理("ROLE_USER_MANAGE","角色用户管理"),
    无模块("NO_MODULE","无模块")
    ;


    private String key;
    private String value;

    private ModuleEnum(String key, String value) {
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
        ModuleEnum[] state= values ();
        for(ModuleEnum st:state){
            if(st.getKey().equals (id)){
                _name= st.name();
            }
        }
        return _name;
    }
    /**
     * 获取模块列表
     * @param
     * @return
     */
    public static List<Map<String,Object>> getModuleList(){
        List<Map<String,Object>>  module = new ArrayList<Map<String, Object>>();
        ModuleEnum[] state= values ();
        for(ModuleEnum st:state){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("key",st.getKey());
            map.put("value",st.getValue());
            module.add(map);
        }
        return module;
    }
}
