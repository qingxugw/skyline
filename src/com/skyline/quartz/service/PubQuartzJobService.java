package com.skyline.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: skyline
 * Date: 12-11-10
 * Time: 下午6:53
 * To change this template use File | Settings | File Templates.
 */
public class PubQuartzJobService implements Serializable {
    private static final long serialVersionUID = 123456789L;
    private static final Logger logger = LoggerFactory.getLogger(PubQuartzJobService.class);


    public void executeDefaultGroup(String triggerName, String group){
        //这里执行定时调度业务
        logger.info("执行默认组的定时任务:["+triggerName+"]-------["+group+"]");

    }

    public void executeDefineGroup( String triggerName,String group){
        //这里执行定时调度业务
        logger.info("执行自定义组的定时任务:["+triggerName+"]-------["+group+"]");
    }
}
