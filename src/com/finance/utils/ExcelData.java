package com.finance.utils;

import java.util.List;

public class ExcelData {

    private int sheetSum;
    private String fileName;
    private List<ExcelSheetData> sheetData;

    public int getSheetSum() {
        return sheetSum;
    }

    public void setSheetSum(int sheetSum) {
        this.sheetSum = sheetSum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ExcelSheetData> getSheetData() {
        return sheetData;
    }

    public void setSheetData(List<ExcelSheetData> sheetData) {
        this.sheetData = sheetData;
    }
}
