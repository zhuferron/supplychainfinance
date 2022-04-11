package com.supplychainfinance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplychainfinance.contract.Authority;
import com.supplychainfinance.contract.Company;
import com.supplychainfinance.contract.Receipt;
import com.supplychainfinance.pojo.LoginUser;
import com.supplychainfinance.service.LoginUserService;
import com.supplychainfinance.common.lang.RandomID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SupplyChainFinanceApplicationTests {

    @Autowired
    LoginUserService loginUserService;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() throws JsonProcessingException {
    }

    @Test
    void test2(){


    }




}
