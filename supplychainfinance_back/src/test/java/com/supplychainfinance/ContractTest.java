package com.supplychainfinance;


import com.alibaba.fastjson.JSON;
import com.supplychainfinance.common.lang.RandomID;
import com.supplychainfinance.contract.Authority;
import com.supplychainfinance.contract.Company;
import com.supplychainfinance.contract.Contract;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.InOrder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ContractTest {

    @Test
    public void initTest() {
    //    Contract contract = new Contract();
    //    Context ctx = mock(Context.class);
    //    ChaincodeStub stub = mock(ChaincodeStub.class);
    //    when(ctx.getStub()).thenReturn(stub);
    //
    //    contract.init(ctx);
    //
    //    Authority authority = new Authority();
    //    authority.setAuthorityId("123456"); //RandomId.getUUID()
    //    authority.setAuthorityName("供应链金融区块链开发小组");
    //
    //
    //    InOrder inOrder = inOrder(stub);
    //    inOrder.verify(stub).putStringState("Authority:123456", JSON.toJSONString(authority));
    //}

    //@Test
    //public void addTest() {
    //    ////需要注释掉Contract将公司添加到公司列表中的代码
        //Contract contract = new Contract();
        //Context ctx = mock(Context.class);
        //ChaincodeStub stub = mock(ChaincodeStub.class);
        //Company company = new Company();
        //company.setCompanyId(RandomID.getUUID());
        //company.setCompanyName("朱小龙财团");
        //company.setCore(true);
        //company.setReceiptId_AsFrom(new ArrayList<String>(){{add("123456");}});
        //company.setReceiptId_Asto(new ArrayList<String>(){{add("123456");}});
        //when(ctx.getStub()).thenReturn(stub);
        //
        //contract.addCompany(ctx,company);
        //InOrder inOrder = inOrder(stub);
        //inOrder.verify(stub).putStringState("Company:"+company.getCompanyId(), JSON.toJSONString(company));
     }






}
