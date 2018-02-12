package com.skyline.upload.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 映射excel 行数据
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 上午8:50
 */
public class Row {

    //行索引
    private int rowIndex;
    //存储excel 一行中每一列的值
    private List<Cell> cells = new ArrayList<Cell>();

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public int getCellsSize() {
        return cells.size();
    }
    public Row addCell(Cell cell){
        this.cells.add(cell);
        return this;
    }
    public Cell getCell(int cellIndex){
        return cells.get(cellIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        for(int i=0;i<row.cells.size();i++){
            if(!cells.get(i).getValue().equals(row.cells.get(i).getValue())){
               return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for(Cell cell:cells){
            h += cell.getValue().hashCode();
            h += cell.getColumnIndex();
        }
        return h;
    }
}
