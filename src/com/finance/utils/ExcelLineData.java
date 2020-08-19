package com.finance.utils;

import java.util.List;

public class ExcelLineData {
    /**
     * 行编号
     */
    private int lineNumber;
    /**
     * 行总列数
     */
    private int colSum;
    /**
     * 列数据集合
     */
    private List<String> colData;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getColSum() {
        return colSum;
    }

    public void setColSum(int colSum) {
        this.colSum = colSum;
    }

    public List<String> getColData() {
        return colData;
    }

    public void setColData(List<String> colData) {
        this.colData = colData;
    }
}
