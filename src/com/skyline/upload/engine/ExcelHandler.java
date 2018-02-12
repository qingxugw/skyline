package com.skyline.upload.engine;

import com.skyline.upload.beans.Row;
import com.skyline.upload.exception.ExcelHandlerException;

/**
 * Excel 处理器
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 上午8:38
 */
public interface ExcelHandler {

    /**
     * 处理excel所有sheet
     * @throws ExcelHandlerException
     */
    public void handler() throws Exception;

    /**
     * 处理excel指定索引的sheet  -1表示处理所有sheet
     * @param sheetIndex 与excel2007同一处理,sheetIndex 从 1 开始
     * @throws ExcelHandlerException
     */
    public void handler(int sheetIndex) throws Exception;

    /**
     * 基于行数据处理,子类必须实现改方法,从而取得行数据对象Row
     * @param row
     * @throws ExcelHandlerException
     */
    public void handler(Row row) throws ExcelHandlerException;
}
