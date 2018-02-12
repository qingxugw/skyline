package com.skyline.pub.utils;

import java.util.HashMap;
import java.util.Map;

import com.skyline.base.action.LoginAction;
import org.apache.log4j.Logger;

public class SysUtilities {
    private static Map<String,Object> ACL_MAP= new HashMap<String,Object>();
    private static SysUtilities sysUtilities = null;

    static Logger logger = Logger.getLogger(SysUtilities.class);

    public SysUtilities() {

    }
    public static synchronized SysUtilities getInstance(){
        if(sysUtilities ==null){
            sysUtilities = new SysUtilities();
        }

        return sysUtilities;
    }
    public Map getACL_MAP(){
        ACL_MAP.put("com.skyline.base.action.LoginAction",null);
        return ACL_MAP;
    }


}