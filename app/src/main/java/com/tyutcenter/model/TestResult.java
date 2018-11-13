package com.tyutcenter.model;

/**
 * 2018/11/8
 * Created by SharkChao
 * 827623353@qq.com
 * https://github.com/sharkchao
 */
public class TestResult {
    private String Status;
    private String Message;
    private String RowCount;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRowCount() {
        return RowCount;
    }

    public void setRowCount(String rowCount) {
        RowCount = rowCount;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "Status='" + Status + '\'' +
                ", Message='" + Message + '\'' +
                ", RowCount='" + RowCount + '\'' +
                '}';
    }
}
