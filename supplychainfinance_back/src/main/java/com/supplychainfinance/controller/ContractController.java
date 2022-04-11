package com.supplychainfinance.controller;

import com.alibaba.fastjson.JSON;
import com.supplychainfinance.common.lang.RandomID;
import com.supplychainfinance.common.lang.Result;
import com.supplychainfinance.contract.Company;
import com.supplychainfinance.contract.Receipt;
import com.supplychainfinance.pojo.LoginUser;
import com.supplychainfinance.sdk.InvokeSDK;
import com.supplychainfinance.sdk.ManageSDK;
import com.supplychainfinance.service.LoginUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class ContractController {

    private static final String ORGNAME_ORG1 = "Org1";
    private static final String ADMINNAME_ORG1 = "admin";
    private static final String ADMINPWD_ORG1 = "adminpw";
    private static final String CA_CERT_ORG1 = "profiles/" + ORGNAME_ORG1 + "/tls/" + "ca.org1.example.com-cert.pem";
    private static final String MSPID_ORG1 = "Org1MSP";
    private static final String USERNAME_ORG1 = "ca2c5af4cf7645d7812c4207c40bcec7";
    private static final String USERPWD_ORG1 = "sanfashi";

    @Autowired
    LoginUserService loginUserService;


    @ApiOperation("默认管理员和默认注册,链码初始化")
    @RequestMapping(value = "/invokeContract/init",method = RequestMethod.GET)
    public String enrollAdmin(){
        try {
            //调用注册管理sdk
            ManageSDK.doEnroll(CA_CERT_ORG1, ORGNAME_ORG1, MSPID_ORG1, ADMINNAME_ORG1, ADMINPWD_ORG1);
            //调用添加用户sdk
            ManageSDK.doRegisterUser(CA_CERT_ORG1, ORGNAME_ORG1, MSPID_ORG1, ADMINNAME_ORG1, USERNAME_ORG1, USERPWD_ORG1);
            //调用初始化方法
            //InvokeSDK.doInit(ORGNAME_ORG1,USERNAME_ORG1,RandomID.getUUID());
            return JSON.toJSONString(Result.succ("初始化成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("初始化失败"));
        }
    }

    @ApiOperation("用户注册")
    @RequestMapping(value = "/invokeContract/registerUser",method = RequestMethod.POST)
    public String registerUser(@RequestBody Map<String,String> user){
        try {
            //调用添加用户sdk
            ManageSDK.doRegisterUser(CA_CERT_ORG1, ORGNAME_ORG1, MSPID_ORG1, ADMINNAME_ORG1,user.get("id"), user.get("password"));
            return JSON.toJSONString(Result.succ("用户注册成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("用户注册失败"));
        }
    }

    @ApiOperation("添加企业")
    @RequestMapping(value = "/invokeContract/addCompany",method = RequestMethod.POST)
    public String addCompany(@RequestBody Map<String,String> map){
        try {
            InvokeSDK.doAddCompany(ORGNAME_ORG1, USERNAME_ORG1, map.get("companyId"), map.get("companyName"), map.get("isCore"), JSON.toJSONString(new ArrayList<>()), JSON.toJSONString(new ArrayList<>()));
            return JSON.toJSONString(Result.succ("添加公司成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("添加公司失败"));
        }
    }

    @ApiOperation("添加银行")
    @RequestMapping(value = "/invokeContract/addBank",method = RequestMethod.POST)
    public String addBank(@RequestBody Map<String,String> map){
        try {
            InvokeSDK.doAddBank(ORGNAME_ORG1, USERNAME_ORG1, map.get("bankId"), map.get("bankName"),JSON.toJSONString(new ArrayList<>()));
            return JSON.toJSONString(Result.succ("添加银行成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("添加银行失败"));
        }
    }

    //@ApiOperation("查询所有公司id")
    //@RequestMapping(value = "/invokeContract/queryCompanies",method = RequestMethod.GET)
    //public String queryCompanies(){
    //    try {
    //        String s = InvokeSDK.doQueryCompanies(ORGNAME_ORG1, USERNAME_ORG1);
    //        return JSON.toJSONString(Result.succ("查询成功",s));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return JSON.toJSONString(Result.fail("查询失败"));
    //    }
    //}
    //
    //@ApiOperation("查询所有银行id")
    //@RequestMapping(value = "/invokeContract/queryBanks",method = RequestMethod.GET)
    //public String queryBanks(){
    //    try {
    //        String s = InvokeSDK.doQueryBanks(ORGNAME_ORG1, USERNAME_ORG1);
    //        return JSON.toJSONString(Result.succ("查询成功",s));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return JSON.toJSONString(Result.fail("查询失败"));
    //    }
    //}
    //
    //@ApiOperation("查询所有票据id")
    //@RequestMapping(value = "/invokeContract/queryReceipts",method = RequestMethod.GET)
    //public String queryReceipts(){
    //    try {
    //        String s = InvokeSDK.doQueryReceipts(ORGNAME_ORG1, USERNAME_ORG1);
    //        return JSON.toJSONString(Result.succ("查询成功",s));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return JSON.toJSONString(Result.fail("查询失败"));
    //    }
    //}
    //
    //
    //@ApiOperation("根据id查询公司")
    //@RequestMapping(value = "/invokeContract/queryCompany/{id}",method = RequestMethod.GET)
    //public String queryCompany(@PathVariable("id") String id){
    //    try {
    //        String s = InvokeSDK.doQueryCompany(ORGNAME_ORG1, USERNAME_ORG1,id);
    //        return JSON.toJSONString(Result.succ("查询成功",s));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return JSON.toJSONString(Result.fail("查询失败"));
    //    }
    //}
    //
    //
    //@ApiOperation("根据id查询银行")
    //@RequestMapping(value = "/invokeContract/queryBank/{id}",method = RequestMethod.GET)
    //public String queryBank(@PathVariable("id") String id){
    //    try {
    //        String s = InvokeSDK.doQueryBank(ORGNAME_ORG1, USERNAME_ORG1,id);
    //        return JSON.toJSONString(Result.succ("查询成功",s));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return JSON.toJSONString(Result.fail("查询失败"));
    //    }
    //}
    //
    //
    //
    //@ApiOperation("根据id查询票据")
    //@RequestMapping(value = "/invokeContract/queryReceipt/{id}",method = RequestMethod.GET)
    //public String queryReceipt(@PathVariable("id") String id){
    //    try {
    //        String s = InvokeSDK.doQueryReceipt(ORGNAME_ORG1, USERNAME_ORG1,id);
    //        return JSON.toJSONString(Result.succ("查询成功",s));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return JSON.toJSONString(Result.fail("查询失败"));
    //    }
    //}


    @ApiOperation("核心公司添加票据")
    @RequestMapping(value = "/invokeContract/addReceipt", method = RequestMethod.POST)
    public String addReceipt(@RequestBody Map<String,String> map){
        try {
            InvokeSDK.doAddReceipt(ORGNAME_ORG1, map.get("companyIdFrom"), RandomID.getUUID(), map.get("companyIdFrom"), map.get("companyIdTo"),map.get("amount"),map.get("maturityDate"));
            return JSON.toJSONString(Result.succ("票据添加成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("票据添加失败"));
        }

    }

    @ApiOperation("下游公司使用票据")
    @RequestMapping(value = "/invokeContract/useReceipt", method = RequestMethod.POST)
    public String useReceipt(@RequestBody Map<String,String> map){
        try {
            InvokeSDK.doUseReceipt(ORGNAME_ORG1, map.get("companyIdFrom"), RandomID.getUUID(), map.get("companyIdFrom"),  map.get("companyIdTo"),map.get("bankIdTo"),map.get("amount"),map.get("maturityDate"));
            return JSON.toJSONString(Result.succ("使用票据成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("使用票据失败"));
        }
    }

    //前端需求
    @ApiOperation("查询所有公司信息")
    @RequestMapping(value = "/getCompaniesInfo", method = RequestMethod.GET)
    public String getCompaniesInfo(){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.getCompaniesInfo(ORGNAME_ORG1, USERNAME_ORG1)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("查询所有票据信息")
    @RequestMapping(value = "/getReceiptsInfo", method = RequestMethod.GET)
    public String getReceiptsInfo(){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.getReceiptsInfo(ORGNAME_ORG1, USERNAME_ORG1)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("查询所有银行信息")
    @RequestMapping(value = "/getBanksInfo", method = RequestMethod.GET)
    public String getBanksInfo(){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.getBanksInfo(ORGNAME_ORG1, USERNAME_ORG1)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("查询公司余额")
    @RequestMapping(value = "/getCompanyBalance/{id}", method = RequestMethod.GET)
    public String getBalance(@PathVariable("id") String id){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.doQueryBalance(ORGNAME_ORG1, id,id)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("查询银行余额")
    @RequestMapping(value = "/getBankBalance/{id}", method = RequestMethod.GET)
    public String getBankBalance(@PathVariable("id") String id){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.doQueryBankBalance(ORGNAME_ORG1, id,id)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("根据公司id查询发起票据")
    @RequestMapping(value = "/getCompanyReceiptsFromById/{id}", method = RequestMethod.GET)
    public String getCompanyReceiptsFromById(@PathVariable("id") String id){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.getCompanyReceiptsFromById(ORGNAME_ORG1, id,id)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("根据公司id查询收到票据")
    @RequestMapping(value = "/getCompanyReceiptsToById/{id}", method = RequestMethod.GET)
    public String getCompanyReceiptsToById(@PathVariable("id") String id){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.getCompanyReceiptsToById(ORGNAME_ORG1, id,id)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("根据银行id查询收到票据")
    @RequestMapping(value = "/getBankReceiptsToById/{id}", method = RequestMethod.GET)
    public String getBankReceiptsToById(@PathVariable("id") String id){
        try {
            return JSON.toJSONString(Result.succ("查询成功", InvokeSDK.getBankReceiptsToById(ORGNAME_ORG1, id,id)));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("根据单据id承兑票据")
    @RequestMapping(value = "/acceptReceipt", method = RequestMethod.POST)
    public String acceptReceipt(@RequestBody Map<String,String> map){
        try {
            InvokeSDK.doAcceptReceipt(ORGNAME_ORG1,map.get("id"),map.get("rid"));
            return JSON.toJSONString(Result.succ("承兑成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.fail("承兑失败"));
        }
    }

}
