package com.finance.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Excel 工具类(兼容xls和xlsx)
 *
 * @author Logan
 * @version 1.0.0
 * @createDate 2019-03-07
 *
 */
public class ExcelUtil {

	private static POIFSFileSystem fs;
	private static HSSFWorkbook wb;
	private static HSSFSheet sheet;
	private static HSSFRow row;

	/**
	 * 获取多个sheetExcel表格数据
	 *
	 * @param fileName Excel 数据表格
	 * @return
	 */
	public static ExcelData readMultiSheetExcel(String fileName) {
		InputStream is = null;
		File file = new File(fileName);
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ExcelData excelData = new ExcelData();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer sheetNum = wb.getNumberOfSheets();
		excelData.setSheetSum(sheetNum);
		excelData.setFileName(file.getName());

		//循环获取所有sheet数据
		List<ExcelSheetData> sheetDatas = new ArrayList<>();
		for (int i = 0; i < sheetNum; i++) {
			ExcelSheetData sheetData = new ExcelSheetData();
			sheet = wb.getSheetAt(i);
			sheetData.setLineSum(sheet.getPhysicalNumberOfRows());
			sheetData.setSheetName(sheet.getSheetName());

			List<ExcelLineData> lineDatas = readExcelContentBySheet(sheet);
			sheetData.setLineData(lineDatas);
			sheetDatas.add(sheetData);
		}
		excelData.setSheetData(sheetDatas);
		return excelData;
	}


	private static List<ExcelLineData> readExcelContentBySheet(HSSFSheet sheet) {
		List<ExcelLineData> lineDatas = new ArrayList<>();
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		for (int i = 0; i <= rowNum; i++) {
			int j = 0;
			row = sheet.getRow(i);
			if (Objects.isNull(row)) {
				continue;
			}

			int colNum = row.getPhysicalNumberOfCells();
			ExcelLineData lineData = new ExcelLineData();
			List<String> colData = new ArrayList<>();
			lineData.setColSum(colNum);
			while (j < colNum) {
				String value = getCellValue(row.getCell((short) j)).trim();
				colData.add(value);
				j++;
			}
			lineData.setColData(colData);
			lineDatas.add(lineData);
		}

		return lineDatas;
	}


	/**
	 * 获取单元格数据
	 *
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	private static String getCellValue(HSSFCell cell) {
		if (Objects.isNull(cell)) {
			return "";
		}

		String value = "";
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				//如果为时间格式的内容
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					//注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
					break;
				} else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "非法字符";
				break;
			default:
				value = "未知类型";
				break;
		}
		return value;
	}


}