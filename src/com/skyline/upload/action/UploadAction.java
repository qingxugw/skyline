package com.skyline.upload.action;

import com.skyline.pub.BaseAction;
import com.skyline.pub.utils.AppUtils;
import com.skyline.pub.utils.constants.Constants;
import com.skyline.upload.engine.ExcelThread;
import com.skyline.upload.engine.ThreadPools;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-10 上午8:18
 */
public class UploadAction extends BaseAction {
    private Map<String,Object> result = null;

    private File upload;

    private String uploadFileName;

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    /**
     * 初始化上传界面
     */
    public String initUploadPage(){
        return SUCCESS;
    }

    public String uploadExcelFile(){
        String sessionId = AppUtils.getHttpSession().getId();
        long time = 0;
        result = new HashMap<String,Object>();
        try {
            // 创建xls工作表对象
            if(uploadFileName != null){
                File destFile = new File(new File(Constants.FILEPATH), uploadFileName);//根据 parent 抽象路径名和 child 路径名字符串创建一个新 File 实例。
                if(!destFile.getParentFile().exists())//判断路径是否存在
                    destFile.getParentFile().mkdirs();//如果不存在，则创建此路径
                //将文件保存到硬盘上，因为action运行结束后，临时文件就会自动被删除
                FileUtils.copyFile(upload, destFile);
                long start = System.currentTimeMillis();
                CountDownLatch countDownLatch = new CountDownLatch(3);
                ThreadPools.daemonThreadPool().submit(new ExcelThread(destFile.getAbsolutePath(),2,countDownLatch,sessionId));
                ThreadPools.daemonThreadPool().submit(new ExcelThread(destFile.getAbsolutePath(), 3,countDownLatch,sessionId));
                ThreadPools.daemonThreadPool().submit(new ExcelThread(destFile.getAbsolutePath(), 4,countDownLatch,sessionId));
                countDownLatch.await();
                long end = System.currentTimeMillis();
                time = (end - start) / 1000;
                logger.info("用时 " + time + " 秒");
                //TODO 调用存储过程清理无效数据 暂时认为 销售历史中的无效 用户编码 和商品编码的为无效数据
                destFile.delete();
            }
            //关闭对象 释放内存
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("msg","上传成功");
        result.put("file",uploadFileName);
        result.put("time",time);
        result.put("success",true);
        return "result";
    }
}
