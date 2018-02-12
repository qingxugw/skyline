package com.skyline.quartz;

import com.skyline.quartz.service.PubQuartzJobService;
import org.apache.commons.lang.xwork.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created with IntelliJ IDEA.
 * User: skyline
 * Date: 12-11-10
 * Time: 下午6:49
 * To change this template use File | Settings | File Templates.
 */
public class PubQuartzJobBean extends QuartzJobBean {

    /**
     * Execute the actual job. The job data map will already have been
     * applied as bean property values by execute. The contract is
     * exactly the same as for the standard Quartz execute method.
     *
     * @see #execute
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Trigger trigger = context.getTrigger();
//        String triggerName = trigger.getName();
//        String group = trigger.getGroup();
        //根据Trigger组别调用不同的业务逻辑方法
//        if (StringUtils.equals(group, Scheduler.DEFAULT_GROUP)) {
//            System.out.println("默认组输出。。。");
//        } else {
//            System.out.println("自定义组输出。。。");
//        }
    }
}
