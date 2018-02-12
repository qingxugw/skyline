package com.skyline.upload.util;

import com.skyline.pub.utils.DateUtil;
import com.skyline.pub.utils.constants.Constants;
import com.skyline.upload.beans.Cell;
import com.skyline.upload.beans.Row;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Excel数据处理中用到的一些工具类
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-17 上午9:12
 */
public class ExcelUtil {
    /**
     * 根据列索引返回实际的cell值
     * @param row
     * @param cellIndex
     * @return
     */
    public static String getRealCellValue(Row row,int cellIndex){
        List<Cell> cells = row.getCells();
        for(Cell cell:cells){
            if(cell.getColumnIndex() == cellIndex){
                return  cell.getValue();
            }
        }
        return Constants.NAN;
    }

    /**
     * 用来获取BigDecimal 数据类型
     * @param row
     * @param cellIndex
     * @return
     */
    public static BigDecimal getBigDecimal(Row row,int cellIndex){
        List<Cell> cells = row.getCells();
        for(Cell cell:cells){
            if(cell.getColumnIndex() == cellIndex){
                try {
                    return  new BigDecimal((String)cell.getValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                } catch (Exception e) {
                    e.printStackTrace();
                    return BigDecimal.ZERO;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 获取日期 目前可以处理 YYYYMMDD  YYYY-MM-DD YYYY/MM/DD 三种格式
     * @param row
     * @param cellIndex
     * @return
     */
    public static  Date getDate(Row row,int cellIndex){
        List<Cell> cells = row.getCells();
        for(Cell cell:cells){
            if(cell.getColumnIndex() == cellIndex){
                try {
                    String value = cell.getValue();
                    if(value.indexOf("/") != -1||value.indexOf("-") != -1){
                        return DateUtil.convertStringToDate(cell.getValue());
                    }else{
                        return HSSFDateUtil.getJavaDate(Double.valueOf(cell.getValue()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
}
