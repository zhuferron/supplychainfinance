package com.supplychainfinance.contract;


import java.io.Serializable;


public class Receipt implements Serializable {

    //单据ID
    private String receiptId;
    //单据来源
    private String companyIdFrom;
    //单据流向公司 若流向银行则为"null"
    private String companyIdTo;
    //单据流向银行 若流向公司则为"null"
    private String bankIdTo;
    //单据数额
    private String amount;
    //单据到期日期
    private String maturityDate;

    public Receipt() {
    }

    public Receipt(String receiptId, String companyIdFrom, String companyIdTo, String bankIdTo, String amount, String maturityDate) {
        this.receiptId = receiptId;
        this.companyIdFrom = companyIdFrom;
        this.companyIdTo = companyIdTo;
        this.bankIdTo = bankIdTo;
        this.amount = amount;
        this.maturityDate = maturityDate;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getCompanyIdFrom() {
        return companyIdFrom;
    }

    public void setCompanyIdFrom(String companyIdFrom) {
        this.companyIdFrom = companyIdFrom;
    }

    public String getCompanyIdTo() {
        return companyIdTo;
    }

    public void setCompanyIdTo(String companyIdTo) {
        this.companyIdTo = companyIdTo;
    }

    public String getBankIdTo() {
        return bankIdTo;
    }

    public void setBankIdTo(String bankIdTo) {
        this.bankIdTo = bankIdTo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }


}
