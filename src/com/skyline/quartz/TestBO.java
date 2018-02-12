package com.skyline.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: skyline
 * Date: 12-11-1
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class TestBO implements Serializable {
    private static final long  serialVersionUID = 1234567891237458493L;
    private static int i=0;
    public void work(){
        i++;
        System.out.println("这是第 "+i+" 次输出了");
    }
}
