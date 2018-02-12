package com.skyline.pub.utils.constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Constant values used throughout the application.
 *
 * <p>
 * <a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:qingxugw@163.com">Matt Raible</a>
 */
public class Constants {

    /**
     *记录有效状态
     */
    private static final String YES = "1";
    /**
     * 记录删除状态
     */
    private static final String NO="0";
    /**
     * 升序
     */
    public static final String ASCEND = "asc";

    /**
     * 降序
     */
    public static final String DESCEND = "desc";
    /**
     * 一页显示的记录数量
     */
    public static final int pageSize = 15;

    public static int getPageSize (int page){
        int i = page * pageSize;
        return i;
    }
    public static String ADD = "add";
    public static String EDIT = "edit";
    public static String DELETE = "delete";
    public static String QUERY = "query";

    public static final String 	TRIGGERNAME = "triggerName";
    public static final String 	TRIGGERGROUP = "triggerGroup";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String REPEATCOUNT = "repeatCount";
    public static final String REPEATINTERVEL = "repeatInterval";

    public static final Map<String,String> status = new HashMap<String,String>();
    static{
        status.put("ACQUIRED", "运行中");
        status.put("PAUSED", "暂停中");
        status.put("WAITING", "等待中");
    }

    public static final String FILEPATH = System.getProperty("java.io.tmpdir")+ File.separator;

    /**
     * 空数据用NaN来表示
     */
    public static final String NAN = "NaN";

}
