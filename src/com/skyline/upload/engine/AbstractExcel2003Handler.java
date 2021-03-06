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

import com.skyline.upload.beans.Cell;
import com.skyline.upload.beans.Row;
import com.skyline.upload.exception.ExcelHandlerException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.ArrayList;

/**
 * 基于POI官方hssf demo修改
 * @author skyline modified 2013年6月11日9:15:10
 * A XLS -> CSV processor, that uses the MissingRecordAware
 *  EventModel code to ensure it outputs all columns and rows.
 * @author Nick Burch
 */
public abstract class AbstractExcel2003Handler implements HSSFListener,ExcelHandler {
	private int minColumns;
	private POIFSFileSystem fs;
	private PrintStream output;

	private int lastRowNumber;
	private int lastColumnNumber;

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;
	private HSSFWorkbook stubWorkbook;

	// Records we pick up as we process
	private SSTRecord sstRecord;
	private FormatTrackingHSSFListener formatListener;
	
	/** So we known which sheet we're on */
	private int sheetIndex = -1;
	private BoundSheetRecord[] orderedBSRs;
	private ArrayList boundSheetRecords = new ArrayList();

	// For handling formulas with string results
	private int nextRow;
	private int nextColumn;
	private boolean outputNextStringRecord;

    /**
     * @author skyline add 2013年6月11日9:18:05
     */
    private String fileName;
    private InputStream in;
    //当前操作sheet索引
    private int operateSheetIndex=-1;
    //行数据对象
    private Row row = new Row();
    //是否启用控制台打印 默认关闭
    private boolean isout = false;

	/**
	 * Creates a new XLS -> CSV converter
	 * @param fs The POIFSFileSystem to process
	 * @param output The PrintStream to output the CSV to
	 * @param minColumns The minimum number of columns to output, or -1 for no minimum
     * @author skyline modidfy 2013年6月11日10:20:34
     * 修改构造方法为私有方法,对外部隐藏该构造方法
	 */
	private AbstractExcel2003Handler(POIFSFileSystem fs, PrintStream output, int minColumns) {
		this.fs = fs;
		this.output = output;
		this.minColumns = minColumns;
	}

    /**
     * @author skyline add 2013年6月11日9:23:37
     * @param fileName
     */
    public AbstractExcel2003Handler(String fileName) {
        if(!fileName.endsWith(".xls")){
            //这里只根据文件名来判断不知道是不是有点弱
            throw new ExcelHandlerException("当前处理器只支持Excel 97-2003 版本,请提供正确的Excel版本");
        }
        this.fileName = fileName;
    }

    /**
	 * Creates a new XLS -> CSV converter
	 * @param filename The file to process
	 * @param minColumns The minimum number of columns to output, or -1 for no minimum
	 * @throws java.io.IOException
	 * @throws java.io.FileNotFoundException
     * @author skyline modidfy 2013年6月11日10:20:34
     * 修改构造方法为私有方法,对外部隐藏该构造方法
	 */
	private AbstractExcel2003Handler(String filename, int minColumns) throws IOException, FileNotFoundException {
		this(
				new POIFSFileSystem(new FileInputStream(filename)),
				System.out, minColumns
		);
        this.isout = true;
	}

	/**
	 * Initiates the processing of the XLS file to CSV
     * @author
	 */
	private void process() throws IOException {
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
		formatListener = new FormatTrackingHSSFListener(listener);

		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();

		if(outputFormulaValues) {
			request.addListenerForAllRecords(formatListener);
		} else {
			workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
			request.addListenerForAllRecords(workbookBuildingListener);
		}

		factory.processWorkbookEvents(request, fs);
	}

	/**
	 * Main HSSFListener method, processes events, and outputs the
	 *  CSV as the file is processed.
	 */
	public void processRecord(Record record) {
		int thisRow = -1;
		int thisColumn = -1;
		String thisStr = null;

		switch (record.getSid())
		{
		case BoundSheetRecord.sid:
			boundSheetRecords.add(record);
			break;
		case BOFRecord.sid:
			BOFRecord br = (BOFRecord)record;
			if(br.getType() == BOFRecord.TYPE_WORKSHEET) {
				// Create sub workbook if required
				if(workbookBuildingListener != null && stubWorkbook == null) {
					stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
				}
				// Output the worksheet name
				// Works by ordering the BSRs by the location of
				//  their BOFRecords, and then knowing that we
				//  process BOFRecords in byte offset order
				sheetIndex++;
				if(orderedBSRs == null) {
					orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
				}
				if (isout) {
                    output.println();
                    output.println(
                            orderedBSRs[sheetIndex].getSheetname() +
                                    " [" + (sheetIndex+1) + "]:"
                    );
                }
			}
			break;

		case SSTRecord.sid:
			sstRecord = (SSTRecord) record;
			break;

		case BlankRecord.sid:
			BlankRecord brec = (BlankRecord) record;

			thisRow = brec.getRow();
			thisColumn = brec.getColumn();
			thisStr = "";
			break;
		case BoolErrRecord.sid:
			BoolErrRecord berec = (BoolErrRecord) record;

			thisRow = berec.getRow();
			thisColumn = berec.getColumn();
			thisStr = "";
			break;

		case FormulaRecord.sid:
			FormulaRecord frec = (FormulaRecord) record;

			thisRow = frec.getRow();
			thisColumn = frec.getColumn();

			if(outputFormulaValues) {
				if(Double.isNaN(frec.getValue())) {
					// Formula result is a string
					// This is stored in the next record
					outputNextStringRecord = true;
					nextRow = frec.getRow();
					nextColumn = frec.getColumn();
				} else {
					thisStr = formatListener.formatNumberDateCell(frec);
				}
			} else {
				thisStr = '"' +
					HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
			}
			break;
		case StringRecord.sid:
			if(outputNextStringRecord) {
				// String for formula
				StringRecord srec = (StringRecord)record;
				thisStr = srec.getString();
				thisRow = nextRow;
				thisColumn = nextColumn;
				outputNextStringRecord = false;
			}
			break;

		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			thisRow = lrec.getRow();
			thisColumn = lrec.getColumn();
			thisStr = '"' + lrec.getValue() + '"';
			break;
		case LabelSSTRecord.sid:
			LabelSSTRecord lsrec = (LabelSSTRecord) record;

			thisRow = lsrec.getRow();
			thisColumn = lsrec.getColumn();
			if(sstRecord == null) {
				thisStr = '"' + "(No SST Record, can't identify string)" + '"';
			} else {
				thisStr = '"' + sstRecord.getString(lsrec.getSSTIndex()).toString() + '"';
			}
			break;
		case NoteRecord.sid:
			NoteRecord nrec = (NoteRecord) record;

			thisRow = nrec.getRow();
			thisColumn = nrec.getColumn();
			// TODO: Find object to match nrec.getShapeId()
			thisStr = '"' + "(TODO)" + '"';
			break;
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;

			thisRow = numrec.getRow();
			thisColumn = numrec.getColumn();

			// Format
			thisStr = formatListener.formatNumberDateCell(numrec);
			break;
		case RKRecord.sid:
			RKRecord rkrec = (RKRecord) record;

			thisRow = rkrec.getRow();
			thisColumn = rkrec.getColumn();
			thisStr = '"' + "(TODO)" + '"';
			break;
		default:
			break;
		}

        //判断如果不需要处理所有sheet,并且当前sheet 不是要操作的sheet跳过
        if((sheetIndex+1)!=operateSheetIndex&&operateSheetIndex!=-1){
            return;
        }

		// Handle new row
		if(thisRow != -1 && thisRow != lastRowNumber) {
			lastColumnNumber = -1;
		}

		// Handle missing column
		if(record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
			thisRow = mc.getRow();
			thisColumn = mc.getColumn();
			thisStr = "";
		}

		// If we got something to print out, do so
		if(thisStr != null) {
			if(thisColumn > 0) {
				if (isout) output.print(',');
			}
            if (isout) output.print(thisStr);
            row.setRowIndex(thisRow + 1);
            Cell cell=new Cell();
            cell.setRowIndex(thisRow+1);
            cell.setColumnIndex(thisColumn);
            cell.setValue(thisStr);
            row.addCell(cell);
		}

		// Update column and row count
		if(thisRow > -1)
			lastRowNumber = thisRow;
		if(thisColumn > -1)
			lastColumnNumber = thisColumn;

		// Handle end of row
		if(record instanceof LastCellOfRowDummyRecord) {
			// Print out any missing commas if needed
			if(minColumns > 0) {
				// Columns are 0 based
				if(lastColumnNumber == -1) { lastColumnNumber = 0; }
                if (isout)
				for(int i=lastColumnNumber; i<(minColumns); i++) {
					output.print(',');
				}
			}

			// We're onto a new row
			lastColumnNumber = -1;

			// End the row
            if(!isBlankRow(row)){
                handler(row);
            }
            row=new Row();
            if (isout) output.println();
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
    public void handler() throws Exception {
        this.in=new FileInputStream(fileName);
        this.fs=new POIFSFileSystem(in);
        sheetIndex = -1;
        this.operateSheetIndex = -1;
        this.process();
    }

    public void handler(int sheetIndex) throws Exception {
        this.in=new FileInputStream(fileName);
        this.fs=new POIFSFileSystem(in);
        if(sheetIndex<1){
            throw new ExcelHandlerException("Excel sheet索引应该从1开始");
        }
        this.operateSheetIndex = sheetIndex;
        this.process();
    }

    /**
     * 该方法延迟到子类进行
     * @param row
     * @throws ExcelHandlerException
     */
    public abstract void handler(Row row) throws ExcelHandlerException;
}
