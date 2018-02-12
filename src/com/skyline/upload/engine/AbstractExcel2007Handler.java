/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */
package com.skyline.upload.engine;

import com.skyline.upload.engine.ExcelHandler;
import com.skyline.upload.exception.ExcelHandlerException;
import com.skyline.upload.beans.Cell;
import com.skyline.upload.beans.Row;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.*;

/**
 * 基于POI官方提供的xssf demo修改
 * XSSF and SAX (Event API)
 * @author skyline modified 2013年6月11日11:12:30
 */
public abstract class AbstractExcel2007Handler implements ExcelHandler {

    /**
     * @author skyline add 2013年6月11日11:16:22
     */
    private OPCPackage pkg;
    private int sheetIndex = -1;
    private List<Map<Integer,String>> rowlist = new ArrayList<Map<Integer,String>>();
    private int currentRow = 0;
    private int currentCol = 0;
    private String fileName;
    private boolean isout = false;

    /**
     * 构造方法里面初始化需要用到的 pkg 对象
     * @param fileName
     */
    public AbstractExcel2007Handler(String fileName) {
        if(!fileName.endsWith(".xlsx")){
            throw new ExcelHandlerException("当前处理器只支持Excel 2007 版本,请提供正确的Excel版本");
        }
        this.fileName = fileName;
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser =
                XMLReaderFactory.createXMLReader(
                        "org.apache.xerces.parsers.SAXParser"
                );
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * 这里的修改是基于 原POI 官方demo中的  processAllSheets 方法
     * @throws Exception
     */
    public void handler() throws Exception {
        currentRow = 0;
        pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            currentRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
//        if(pkg != null) pkg.close();
    }

    /**
     * 这里的修改是基于 原POI 官方demo中的  processOneSheet 方法
     * @param sheetIndex 与excel2007同一处理,sheetIndex 从 1 开始
     * @throws Exception
     */
    public void handler(int sheetIndex) throws Exception {
        currentRow = 0;
        pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader( pkg );
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // rId2 found by processing the Workbook
        // 根据 rId#  查找sheet
        InputStream sheet = r.getSheet("rId" + sheetIndex);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet);
        parser.parse(sheetSource);
        sheet.close();
//        if(pkg != null) pkg.close();
    }

    /**
     * 该方法延迟到子类进行
     * @param row
     * @throws ExcelHandlerException
     */
    public abstract void handler(Row row) throws ExcelHandlerException;

    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private class SheetHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private StringBuilder lastContents;
        private boolean nextIsString;
        private boolean close = false;
        private int thisColumnIndex = -1;

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
            // c => cell
            if(name.equals("c")) {
                // Print the cell reference
                if (isout) System.out.print(attributes.getValue("r") + " - ");
                // Figure out if the value is an index in the SST
                //计算列索引
                String r = attributes.getValue("r");
                int firstDigit = -1;
                for (int c = 0; c < r.length(); ++c) {
                    if (Character.isDigit(r.charAt(c))) {
                        firstDigit = c;
                        break;
                    }
                }
                // 当前列索引
                thisColumnIndex = nameToColumn(r.substring(0, firstDigit));
                String cellType = attributes.getValue("t");
                if(cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }
            // Clear contents cache
//			lastContents = "";
            lastContents = new StringBuilder("");
        }

        /**
         * 从列名转换为列索引
         * @param name
         * @return
         */
        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length(); ++i) {
                int c = name.charAt(i);
                column = (column + 1) * 26 + c - 'A';
            }
            return column;
        }

        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsString) {
                try {
                    int idx = Integer.parseInt(lastContents.toString());
                    lastContents =  new StringBuilder(new XSSFRichTextString(sst.getEntryAt(idx)).toString());
                } catch (NumberFormatException e) {
//                    e.printStackTrace();
                }
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v")) {
                if(isout) System.out.println(lastContents);
                String value = lastContents.toString().trim();
                value = value.equals("") ? "" : value;
//                rowlist.add(currentCol, value);
                Map<Integer,String> map = new HashMap<Integer,String>();
                map.put(thisColumnIndex, value);
                rowlist.add(map);
                currentCol++;
                close=true;
            }else if(name.equals("c")){
                if(!close){
                    Map<Integer,String> map = new HashMap<Integer,String>();
                    map.put(thisColumnIndex,"");
                    rowlist.add(map);
//                    rowlist.add(currentCol, "");
                    currentCol++;
                }
            }else if(name.equals("row")){
                //标签名称为row,已到行尾
                Row row=new Row();
                for(int i=0;i<rowlist.size();i++){
                    Map<Integer,String> map = rowlist.get(i);
                    int key = map.keySet().iterator().next();
                    Cell cell=new Cell();
                    cell.setColumnIndex(key + 1);
                    cell.setRowIndex(currentRow + 1);
                    cell.setValue(map.get(key));
                    row.setRowIndex(currentRow+1);
                    row.addCell(cell);
                }
                if(!isBlankRow(row)){
                    handler(row);
                }
                rowlist.clear();
                currentRow++;
                currentCol = 0;
            }
        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents.append(new String(ch, start, length));
        }
    }

    /**
     * 判断该行是不是空值
     * @param row
     * @return
     */
    private boolean isBlankRow(Row row){
        boolean flag=true;
        for(int i=0;i<row.getCellsSize();i++){
            Cell cell=row.getCell(i);
            if(StringUtils.isNotBlank(cell.getValue())){
                flag=false;
                break;
            }
        }
        return flag;
    }
}
