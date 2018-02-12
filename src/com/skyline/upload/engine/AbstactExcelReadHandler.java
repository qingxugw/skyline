package com.skyline.upload.engine;

import com.skyline.upload.beans.Row;
import com.skyline.upload.exception.ExcelHandlerException;

import java.io.File;

/**
 * 根据文件名构造这个对象,进行excel的读取
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午1:50
 */
public abstract class AbstactExcelReadHandler implements ExcelHandler{

    private ExcelHandler excelHandler;

    protected AbstactExcelReadHandler(String fileName) {
        if(fileName==null||fileName.equals("")){
            throw new ExcelHandlerException("请指定文件名");
        }
        File file=new File(fileName);
        if(!file.exists()){
            throw new ExcelHandlerException("指定的文件不存在："+fileName);
        }
        //暂时根据文件名来判断
        if(fileName.endsWith("xls")){
            excelHandler=new RealExcel2003Handler(fileName);
        }else{
            excelHandler=new RealExcel2007Handler(fileName);
        }
    }

    public void handler() throws Exception {
        excelHandler.handler();
    }

    public void handler(int sheetIndex) throws Exception {
        excelHandler.handler(sheetIndex);
    }

    /**
     * 再次将方法延迟到子类进行
     * @param row
     * @throws ExcelHandlerException
     */
    public abstract void handler(Row row) throws ExcelHandlerException;


    private class RealExcel2003Handler extends AbstractExcel2003Handler {

        private RealExcel2003Handler(String fileName) {
            super(fileName);
        }

        @Override
        public void handler(Row row) throws ExcelHandlerException {
            AbstactExcelReadHandler.this.handler(row);
        }
    }

    private class RealExcel2007Handler extends AbstractExcel2007Handler {
        private RealExcel2007Handler(String fileName) {
            super(fileName);
        }

        @Override
        public void handler(Row row) throws ExcelHandlerException {
            AbstactExcelReadHandler.this.handler(row);
        }
    }
}
