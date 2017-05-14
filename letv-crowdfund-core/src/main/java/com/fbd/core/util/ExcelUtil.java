package com.fbd.core.util;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 
 * Description: Excel工具类
 * 
 * @author dekunzhao
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2013-11-27  zhaokevin       1.0        1.0 Version
 * </pre>
 */
public class ExcelUtil {

	public static HSSFWorkbook createWorkBook() {

		HSSFWorkbook workbook = null;
		workbook = new HSSFWorkbook();
		return workbook;
	}

	public static HSSFSheet createSheet(HSSFWorkbook workbook, String sheetName) {
		return workbook.createSheet(sheetName);
	}

	public static HSSFRow row(HSSFSheet sheet, int index) {
		return sheet.createRow(index);
	}

	public static HSSFCell cell(HSSFRow row,int index,Object content){
		return cell(row,index,null,content,null);
	}
	
	public static HSSFCell cell(HSSFRow row,int index,Object content,CellStyle cellStyle){
		return cell(row,index,null,content,cellStyle);
	}
	
	public static HSSFCell cell(HSSFRow row, int index, Integer type,
			Object content,CellStyle cellStyle) {

		HSSFCell cell = null;
		if (type == null) {
			cell = row.createCell(index);
		} else {
			cell = row.createCell(index, type);
		}
		if(cellStyle!=null){
			cell.setCellStyle(cellStyle);
		}
		if (content instanceof Boolean) {
			cell.setCellValue((Boolean) content);
		} else if (content instanceof Double || content instanceof Float
				|| content instanceof Long || content instanceof Integer) {
			cell.setCellValue(new Double(content.toString()));
		} else if (content instanceof Date) {
			cell.setCellValue((Date)content);
		} else {
			cell.setCellValue((String) content);
		}
		return cell;
	}

	public static void autoSizeColumn(HSSFSheet sheet,int startIndex,int endIndex){
		for(int index = startIndex;index<(endIndex-startIndex);index++){
			autoSizeColumn(sheet,index);
		}
	}
	
	public static void autoSizeColumn(HSSFSheet sheet,int index){
		sheet.autoSizeColumn(index);
	}
	
}
