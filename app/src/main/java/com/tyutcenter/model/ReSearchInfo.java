package com.tyutcenter.model;

import java.io.Serializable;

/**
 * Created by Admin on 2018/2/22.
 */

public class ReSearchInfo implements Serializable {

    private String ZXMID;   //主项目id
    private String XMYS;   //项目预算
    private String KHMC;    //客户名称
    private String XMMC;    //项目名称
    private String DJMC;    //等级名称
    private String MZLMC;   //门诊量名称
    private String KFCWMC;  //开放床位名称
    private String SJJDMC;  //商机阶段名称
    private String SFZDSJ;     //是否重大商机
    private String XWJSR;   //希望接手人
    private String KHLXR;   //客户联系人
    private String KHLXFS;  //客户联系方式
    private String QUMC;    //区域名称
    private String XSR;     //销售人
    private String XSLXFS;  //销售联系方式

    private String BLDJMC;     //电子病历等级
    private String HLHTDJMC; //互联互通等级名称
    private String YYDJMC;   //医院等级名称：这个地方因为数据库字段和电子病历等级重复所以加上YY
    private String HIMSSMC;  //HIMSS名称
    private String JCI;      //JCI
    private String PSXQQT;   //评审需求其他
    private String WNGH;    //五年规划
    private String SNGH;    //三年规划
    private String QYZX;     //区域中心
    private String GHXQQT;   //规划需求其他
    private String SQR;     //申请人
    private String CJR;     //申请人


    private String WLJCMC;     //网络集成等级名称
    private String ZJYCTGSJ; //最近一次通过时间
    private String YDBFMC;   //异地备份名称
    private String SFDYQ;  //是否多院区
    private String SFXJYY;      //是否新建医院
    private String CHANGEDATE;   //修改时间

    public String getXMYS() {
        return XMYS;
    }

    public void setXMYS(String XMYS) {
        this.XMYS = XMYS;
    }

    public String getKHMC() {
        return KHMC;
    }

    public void setKHMC(String KHMC) {
        this.KHMC = KHMC;
    }

    public String getXMMC() {
        return XMMC;
    }

    public void setXMMC(String XMMC) {
        this.XMMC = XMMC;
    }

    public String getDJMC() {
        return DJMC;
    }

    public void setDJMC(String DJMC) {
        this.DJMC = DJMC;
    }

    public String getMZLMC() {
        return MZLMC;
    }

    public void setMZLMC(String MZLMC) {
        this.MZLMC = MZLMC;
    }

    public String getKFCWMC() {
        return KFCWMC;
    }

    public void setKFCWMC(String KFCWMC) {
        this.KFCWMC = KFCWMC;
    }

    public String getSJJDMC() {
        return SJJDMC;
    }

    public void setSJJDMC(String SJJDMC) {
        this.SJJDMC = SJJDMC;
    }


    public String getXWJSR() {
        return XWJSR;
    }

    public void setXWJSR(String XWJSR) {
        this.XWJSR = XWJSR;
    }

    public String getKHLXR() {
        return KHLXR;
    }

    public void setKHLXR(String KHLXR) {
        this.KHLXR = KHLXR;
    }

    public String getKHLXFS() {
        return KHLXFS;
    }

    public void setKHLXFS(String KHLXFS) {
        this.KHLXFS = KHLXFS;
    }

    public String getQUMC() {
        return QUMC;
    }

    public void setQUMC(String QUMC) {
        this.QUMC = QUMC;
    }

    public String getXSR() {
        return XSR;
    }

    public void setXSR(String XSR) {
        this.XSR = XSR;
    }

    public String getXSLXFS() {
        return XSLXFS;
    }

    public void setXSLXFS(String XSLXFS) {
        this.XSLXFS = XSLXFS;
    }

    public String getDZBLDJMC() {
        return BLDJMC;
    }

    public void setDZBLDJMC(String DZBLDJMC) {
        this.BLDJMC = DZBLDJMC;
    }

    public String getHLHTDJMC() {
        return HLHTDJMC;
    }

    public void setHLHTDJMC(String HLHTDJMC) {
        this.HLHTDJMC = HLHTDJMC;
    }

    public String getYYDJMC() {
        return YYDJMC;
    }

    public void setYYDJMC(String YYDJMC) {
        this.YYDJMC = YYDJMC;
    }

    public String getHIMSSMC() {
        return HIMSSMC;
    }

    public void setHIMSSMC(String HIMSSMC) {
        this.HIMSSMC = HIMSSMC;
    }

    public String getSFZDSJ() {
        return SFZDSJ;
    }

    public void setSFZDSJ(String SFZDSJ) {
        this.SFZDSJ = SFZDSJ;
    }

    public String getJCI() {
        return JCI;
    }

    public void setJCI(String JCI) {
        this.JCI = JCI;
    }

    public String getPSXQQT() {
        return PSXQQT;
    }

    public void setPSXQQT(String PSXQQT) {
        this.PSXQQT = PSXQQT;
    }

    public String getWNGH() {
        return WNGH;
    }

    public void setWNGH(String WNGH) {
        this.WNGH = WNGH;
    }

    public String getSNGH() {
        return SNGH;
    }

    public void setSNGH(String SNGH) {
        this.SNGH = SNGH;
    }

    public String getQYZX() {
        return QYZX;
    }

    public void setQYZX(String QYZX) {
        this.QYZX = QYZX;
    }

    public String getGHXQQT() {
        return GHXQQT;
    }

    public void setGHXQQT(String GHXQQT) {
        this.GHXQQT = GHXQQT;
    }

    public String getWLJCMC() {
        return WLJCMC;
    }

    public void setWLJCMC(String WLJCMC) {
        this.WLJCMC = WLJCMC;
    }

    public String getZJYCTGSJ() {
        return ZJYCTGSJ;
    }

    public void setZJYCTGSJ(String ZJYCTGSJ) {
        this.ZJYCTGSJ = ZJYCTGSJ;
    }

    public String getYDBFMC() {
        return YDBFMC;
    }

    public void setYDBFMC(String YDBFMC) {
        this.YDBFMC = YDBFMC;
    }

    public String getSFDYQ() {
        return SFDYQ;
    }

    public void setSFDYQ(String SFDYQ) {
        this.SFDYQ = SFDYQ;
    }

    public String getSFXJYY() {
        return SFXJYY;
    }

    public void setSFXJYY(String SFXJYY) {
        this.SFXJYY = SFXJYY;
    }

    public String getZXMID() {
        return ZXMID;
    }

    public void setZXMID(String ZXMID) {
        this.ZXMID = ZXMID;
    }

    public String getSQR() {
        return SQR;
    }

    public void setSQR(String SQR) {
        this.SQR = SQR;
    }
}
