package com.skyline.upload.engine;

import com.skyline.upload.beans.Cell;
import com.skyline.upload.beans.Row;
import com.skyline.upload.exception.ExcelHandlerException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午3:30
 */
public class ExcelReadHandler extends AbstactExcelReadHandler {

    private Set<Row> rows = new LinkedHashSet<Row>();

    public Set<Row> getRows() {
        return rows;
    }

    protected ExcelReadHandler(String fileName) {
        super(fileName);
    }

    public void addRows(Row row){
        rows.add(row);
    }

    @Override
    public void handler(Row row) throws ExcelHandlerException {
        addRows(row);
    }

    public static void main(String[] args) throws Exception {
//        ExcelReadHandler reader=new ExcelReadHandler("D:/Teradata Central Team-笔试题目-v13.04.xlsx");
//        long start = System.currentTimeMillis();
//        for(Row row:reader.rows){
//            for (int i = 0; i < row.getCellsSize(); i++) {
//                System.out.print("[" + row.getRowIndex() + ","
//                        +  row.getCell(i).getColumnIndex() + ","
//                        + row.getCell(i).getValue() + "]");
//            }
//            System.out.println();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("用时 "+((end-start)/1000) +"  seconds");
        Set<Row> rows = new LinkedHashSet<Row>();
        Row row = new Row();
        List<Cell> cells = new ArrayList<Cell>();
        Cell cell = new Cell();
        cell.setValue("2002");
        cell.setRowIndex(1);
        cell.setColumnIndex(1);
        cells.add(cell);
        cell = new Cell();
        cell.setValue("1000");
        cell.setRowIndex(1);
        cell.setColumnIndex(2);
        cells.add(cell);
        row.setCells(cells);
        row.setRowIndex(1);
        rows.add(row);
        row = new Row();
        cells = new ArrayList<Cell>();
        cell = new Cell();
        cell.setValue("2002");
        cell.setRowIndex(2);
        cell.setColumnIndex(1);
        cells.add(cell);
        cell = new Cell();
        cell.setValue("10009");
        cell.setRowIndex(2);
        cell.setColumnIndex(2);
        cells.add(cell);
        row.setCells(cells);
        row.setRowIndex(2);
        rows.add(row);
        for(Row ro:rows){
            System.out.println(ro.getRowIndex());
        }
    }

}
