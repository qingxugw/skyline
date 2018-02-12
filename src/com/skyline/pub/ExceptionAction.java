package com.skyline.pub;

import com.skyline.base.dto.TdOperationLogDTO;
import com.skyline.base.service.LogService;
import com.skyline.pub.exception.AppException;
import com.skyline.pub.utils.enums.ValidatorEnumException;
import org.apache.commons.lang.xwork.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-24
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionAction extends BaseAction{
    /**
     * 返回ajax错误信息
     * @return
     */
    public String queryAjax(){
        resultMap.put("root",null);
        resultMap.put("totalProperty",1);
        return "resultMap";
    }
}
