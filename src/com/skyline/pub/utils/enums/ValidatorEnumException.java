package com.skyline.pub.utils.enums;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-8-28
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public enum ValidatorEnumException implements BaseEnum{
    获取数组参数出错("GET_ARRAY_ERROR","获取数组参数出错"),
    获取参数出错("GET_PARAMETER_ERROR","获取参数出错"),
    参数类型错误("PARAMETER_TYPE_ERROR","参数类型错误"),
    用户名密码不能为空("USERNAME_PASSWORD_CANNOT_NULL","用户名密码不能为空")
    ;


    private String key;
    private String value;

    private ValidatorEnumException(String key, String value) {
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
        ValidatorEnumException[] state= values ();
        for(ValidatorEnumException st:state){
            if(st.getKey().equals (id)){
                _name= st.name();
            }
        }
        return _name;
    }
}
