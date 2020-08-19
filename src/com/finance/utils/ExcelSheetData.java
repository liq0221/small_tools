package com.finance.utils;

import java.util.List;

public class ExcelSheetData {

    /**
     * 工作簿名称
     */
    private String sheetName;
    /**
     * 表格总行数
     */
    private int lineSum;
    /**
     * 行数据集合
     */
    private List<ExcelLineData> lineData;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getLineSum() {
        return lineSum;
    }

    public void setLineSum(int lineSum) {
        this.lineSum = lineSum;
    }

    public List<ExcelLineData> getLineData() {
        return lineData;
    }

    public void setLineData(List<ExcelLineData> lineData) {
        this.lineData = lineData;
    }
}
