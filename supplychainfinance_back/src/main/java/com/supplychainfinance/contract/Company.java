package com.supplychainfinance.contract;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable {

    //公司Id
    private String companyId;
    //公司名称
    private String companyName;
    //是否为核心企业
    private String core;
    //该公司作为单据发行方的单据数组
    private ArrayList<String> receiptId_AsFrom;
    //该公司作为单据收取方的单据数组
    private ArrayList<String> receiptId_Asto;

    public Company() {
    }

    public Company(String companyId, String companyName, String core, ArrayList<String> receiptId_AsFrom, ArrayList<String> receiptId_Asto) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.core = core;
        this.receiptId_AsFrom = receiptId_AsFrom;
        this.receiptId_Asto = receiptId_Asto;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public ArrayList<String> getReceiptId_AsFrom() {
        return receiptId_AsFrom;
    }

    public void setReceiptId_AsFrom(ArrayList<String> receiptId_AsFrom) {
        this.receiptId_AsFrom = receiptId_AsFrom;
    }

    public ArrayList<String> getReceiptId_Asto() {
        return receiptId_Asto;
    }

    public void setReceiptId_Asto(ArrayList<String> receiptId_Asto) {
        this.receiptId_Asto = receiptId_Asto;
    }
}
