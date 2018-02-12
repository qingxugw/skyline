package com.skyline.upload.engine;

import com.skyline.pub.utils.SpringContextUtil;
import com.skyline.upload.beans.Row;
import com.skyline.upload.service.UploadService;
import org.hibernate.Session;

import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-17 上午9:05
 */
public class ExcelInsertThread extends Thread {

    private int sheetIndex;
    private CountDownLatch countDownLatch;
    private Set<Row> rows;
    private int start;
    private int limit;

    public ExcelInsertThread(int sheetIndex, Set<Row> rows,int start,int limit, CountDownLatch countDownLatch) {
        this.sheetIndex = sheetIndex;
        this.rows = rows;
        this.countDownLatch = countDownLatch;
        this.start = start;
        this.limit = limit;
    }

    @Override
    public void run() {
        if(sheetIndex ==  -1 || rows == null) return;
        UploadService uploadService = SpringContextUtil.getBean("uploadService");
        switch (sheetIndex){
            case 1:uploadService.updateMiddleSaleProcedure(start,limit); break;
            case 2:uploadService.updateProductCountProcedure();break;
            case 3:uploadService.updateMonthProcedure();break;
        }
        countDownLatch.countDown();
    }
}
