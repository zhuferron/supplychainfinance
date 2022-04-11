package com.supplychainfinance.contract;

import java.io.Serializable;
import java.util.ArrayList;

public class Bank implements Serializable {

    //银行ID
    private String bankId;
    //银行名称
    private String bankName;
    //该银行作为单据收取方的单据数组
    private ArrayList<String> receiptId_Asto;

    public Bank() {
    }

    public Bank(String bankId, String bankName, ArrayList<String> receiptId_Asto) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.receiptId_Asto = receiptId_Asto;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ArrayList<String> getReceiptId_Asto() {
        return receiptId_Asto;
    }

    public void setReceiptId_Asto(ArrayList<String> receiptId_Asto) {
        this.receiptId_Asto = receiptId_Asto;
    }
}
