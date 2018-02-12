package com.skyline.pub.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-08-28
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper {

    public static void createQuery(Query query,Object... param){
        int index = 0;
        for(int i = 0 ; i < param.length ; i += 3){
            if(param[i + 2] != null && StringUtils.isNotBlank(param[i + 2].toString())){
                if(param[ i + 2 ] instanceof String){
                    query.setString(index,param[i + 2].toString());
                }else if(param[ i + 2 ] instanceof Integer){
                    query.setInteger(index,(Integer)param[i + 2]);
                }else if(param[ i + 2 ] instanceof Date){
                    query.setTimestamp(index,(Date)param[i + 2]);
                }else if(param[ i + 2 ] instanceof Boolean){
                    query.setBoolean(index,(Boolean)param[i + 2]);
                }else{
                    throw new IllegalArgumentException("不支持的参数类型!");
                }
                index ++;
            }

        }
    }
}
