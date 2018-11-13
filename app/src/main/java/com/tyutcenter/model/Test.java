package com.tyutcenter.model;

/**
 * 2018/11/8
 * Created by SharkChao
 * 827623353@qq.com
 * https://github.com/sharkchao
 */
public class Test {
    private String Mac;
    private String TargetName;
    private String TargetCode;
    private String TargetType;
    private String OfficeName;
    private String OfficeCode;
    private String TargetGender;
    private String TargetBirthday;

    public String getMac() {
        return Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }

    public String getTargetName() {
        return TargetName;
    }

    public void setTargetName(String targetName) {
        TargetName = targetName;
    }

    public String getTargetCode() {
        return TargetCode;
    }

    public void setTargetCode(String targetCode) {
        TargetCode = targetCode;
    }

    public String getTargetType() {
        return TargetType;
    }

    public void setTargetType(String targetType) {
        TargetType = targetType;
    }

    public String getOfficeName() {
        return OfficeName;
    }

    public void setOfficeName(String officeName) {
        OfficeName = officeName;
    }

    public String getOfficeCode() {
        return OfficeCode;
    }

    public void setOfficeCode(String officeCode) {
        OfficeCode = officeCode;
    }

    public String getTargetGender() {
        return TargetGender;
    }

    public void setTargetGender(String targetGender) {
        TargetGender = targetGender;
    }

    public String getTargetBirthday() {
        return TargetBirthday;
    }

    public void setTargetBirthday(String targetBirthday) {
        TargetBirthday = targetBirthday;
    }

    @Override
    public String toString() {
        return "Test{" +
                "Mac='" + Mac + '\'' +
                ", TargetName='" + TargetName + '\'' +
                ", TargetCode='" + TargetCode + '\'' +
                ", TargetType='" + TargetType + '\'' +
                ", OfficeName='" + OfficeName + '\'' +
                ", OfficeCode='" + OfficeCode + '\'' +
                ", TargetGender='" + TargetGender + '\'' +
                ", TargetBirthday='" + TargetBirthday + '\'' +
                '}';
    }
}
