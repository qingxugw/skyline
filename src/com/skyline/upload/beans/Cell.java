package com.skyline.upload.beans;

/**
 * 映射excel的单元格
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 上午9:02
 */
public class Cell {

    //用来返回列索引
//    private char[] abc = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    //行索引
    private int rowIndex;
    //列索引
    private int columnIndex;
    //列值
    private String value;
    //对应于excel中 A1 B1
//    private String column;

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    public String getColumn() {
//        if(columnIndex < abc.length){
//            column = String.valueOf(abc[columnIndex]);
//        }else{
//            int mod = columnIndex % 25;
//            int index = columnIndex / 25;
//            column = StringUtils.join(new String[]{String.valueOf(abc[index - 1]),String.valueOf(abc[mod - 1])});
//        }
//        return column;
//    }

}
