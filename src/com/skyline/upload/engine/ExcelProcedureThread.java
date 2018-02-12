package com.skyline.upload.engine;

import com.skyline.pub.utils.SpringContextUtil;
import com.skyline.upload.service.UploadService;

import java.util.concurrent.CountDownLatch;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-17 上午9:05
 */
public class ExcelProcedureThread extends Thread {

    private int type;
    private int start;
    private int limit;
    private CountDownLatch countDownLatch;

    public ExcelProcedureThread(int type,int start,int limit,CountDownLatch countDownLatch) {
        this.type = type;
        this.start = start;
        this.limit = limit;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        if(type ==  -1) return;
        UploadService uploadService = SpringContextUtil.getBean("uploadService");
        switch (type){
            case 1:uploadService.updateMiddleSaleProcedure(start,limit); break;
            case 2:uploadService.updateProductCountProcedure();break;
            case 3:uploadService.updateMonthProcedure();break;
        }
        countDownLatch.countDown();
    }
}
