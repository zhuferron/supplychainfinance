package com.supplychainfinance.contract;

import java.io.Serializable;

public class Authority implements Serializable {

    //权威机构Id
    private String authorityId;
    //权威机构名称
    private String authorityName;

    public Authority() {
    }

    public Authority(String authorityId, String authorityName) {
        this.authorityId = authorityId;
        this.authorityName = authorityName;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
