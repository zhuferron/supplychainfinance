package com.supplychainfinance.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.supplychainfinance.contract.Bank;
import com.supplychainfinance.contract.Company;
import com.supplychainfinance.contract.Receipt;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class InvokeSDK {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = "mycc02_java";  //chaincode部署名称

    /**使用组织名和用户名，获取gateway连接
     * @param orgName
     * @param userName
     * @return
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static Gateway getGateway(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
        // 从wallet获得user标识
        Path walletPath = Paths.get("wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        Identity identity = wallet.get(userName);

        // 检查wallet中是否存在user标识
        if (identity == null) {
            System.out.println("The identity \"" + userName + "@" + orgName + "\" doesn't exists in the wallet");
            return null;
        }

        //加载创建连接的tls文件
        Path networkConfigPath = Paths.get("profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        return builder.connect();

    }

    /**
     * 调用初始化方法
     * @param orgName
     * @param userName
     * @param authorityId
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void doInit(String orgName, String userName, String authorityId) throws IOException, ContractException, TimeoutException, InterruptedException {
        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("init", authorityId);
            System.out.println(new String(result));
        }
    }

    /**
     * 调用添加公司的chaincode
     * @param orgName
     * @param userName
     * @param companyId
     * @param companyName
     * @param isCore
     * @param receiptId_AsFrom
     * @param receiptId_Asto
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void doAddCompany(String orgName, String userName, String companyId, String companyName, String isCore, String receiptId_AsFrom, String receiptId_Asto) throws IOException, ContractException, TimeoutException, InterruptedException {

        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("addCompany", companyId, companyName, isCore, receiptId_AsFrom, receiptId_Asto);
            System.out.println(new String(result));
        }

    }


    /**
     * 调用添加银行的chaincode
     * @param orgName
     * @param userName
     * @param bankId
     * @param bankName
     * @param receiptId_Asto
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void doAddBank(String orgName, String userName, String bankId, String bankName, String receiptId_Asto) throws IOException, ContractException, TimeoutException, InterruptedException {

        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("addBank", bankId, bankName, receiptId_Asto);
            System.out.println(new String(result));
        }

    }


    /**
     * 调用创建新单据的chaincode
     * @param orgName
     * @param userName
     * @param receiptId
     * @param companyIdFrom
     * @param companyIdTo
     * @param amount
     * @param maturityDate
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void doAddReceipt(String orgName, String userName, String receiptId, String companyIdFrom, String companyIdTo, String amount, String maturityDate) throws IOException, ContractException, TimeoutException, InterruptedException {

        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("addReceipt", receiptId, companyIdFrom, companyIdTo, amount, maturityDate);
            System.out.println(new String(result));
        }


    }


    /**
     * 小微企业使用票据的chaincode
     * @param orgName
     * @param userName
     * @param receiptId
     * @param companyIdFrom
     * @param companyIdTo
     * @param bankIdTo
     * @param amount
     * @param maturityDate
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void doUseReceipt(String orgName, String userName, String receiptId, String companyIdFrom, String companyIdTo, String bankIdTo, String amount, String maturityDate) throws IOException, ContractException, TimeoutException, InterruptedException {

        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("useReceipt", receiptId, companyIdFrom, companyIdTo, bankIdTo, amount, maturityDate);
            System.out.println(new String(result));
        }

    }


    /**
     * 企业承兑票据的chaincode
     * @param orgName
     * @param userName
     * @param receiptId
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void doAcceptReceipt(String orgName, String userName, String receiptId) throws IOException, ContractException, TimeoutException, InterruptedException {

        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("acceptReceipt", receiptId);
            System.out.println(new String(result));
        }

    }


    /**
     * 查询企业账户余额的chaincode
     * @param orgName
     * @param userName
     * @param companyId
     * @return
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static String doQueryBalance(String orgName, String userName, String companyId) throws IOException, ContractException, TimeoutException, InterruptedException {

        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryBalance", companyId);
            System.out.println(new String(result));
            return new String(result);
        }

    }

    /**
     * 查询银行账户余额的chaincode
     * @param orgName
     * @param userName
     * @param bankId
     * @return
     * @throws IOException
     * @throws ContractException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static String doQueryBankBalance(String orgName, String userName, String bankId) throws IOException, ContractException, TimeoutException, InterruptedException {

        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryBankBalance", bankId);
            System.out.println(new String(result));
            return new String(result);
        }

    }


    ///**
    // * 查询全部的权威机构的chaincode
    // * @param orgName
    // * @param userName
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryAuthorities(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
    //
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryAuthorities");
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //
    //
    //}

    ///**
    // * 查询全部的公司的chaincode
    // * @param orgName
    // * @param userName
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryCompanies(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryCompanies");
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //}
    //
    //
    //
    ///**
    // * 查询全部的银行的chaincode
    // * @param orgName
    // * @param userName
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryBanks(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
    //
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryBanks");
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //
    //}
    //
    //
    //
    ///**
    // * 查询全部的单据的chaincode
    // * @param orgName
    // * @param userName
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryReceipts(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
    //
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryReceipts");
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //
    //
    //}



    ///**
    // * 按照权威机构的id查询权威机构的chaincode
    // * @param orgName
    // * @param userName
    // * @param authorityId
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryAuthority(String orgName, String userName, String authorityId) throws IOException, ContractException, TimeoutException, InterruptedException {
    //
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryAuthority", authorityId);
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //
    //}
    //
    //
    //
    ///**
    // * 按照公司的id查询公司的chaincode
    // * @param orgName
    // * @param userName
    // * @param companyId
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryCompany(String orgName, String userName, String companyId) throws IOException, ContractException, TimeoutException, InterruptedException {
    //
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryCompany", companyId);
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //}
    //
    //
    //
    ///**
    // * 按照银行的id查询银行的chaincode
    // * @param orgName
    // * @param userName
    // * @param bankId
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryBank(String orgName, String userName, String bankId) throws IOException, ContractException, TimeoutException, InterruptedException {
    //
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryBank", bankId);
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //
    //}
    //
    ///**
    // * 按照单据的id查询单据的chaincode
    // * @param orgName
    // * @param userName
    // * @param receiptId
    // * @return
    // * @throws IOException
    // * @throws ContractException
    // * @throws TimeoutException
    // * @throws InterruptedException
    // */
    //public static String doQueryReceipt(String orgName, String userName, String receiptId) throws IOException, ContractException, TimeoutException, InterruptedException {
    //
    //    try (Gateway gateway = getGateway(orgName,userName)) {
    //        Network network = gateway.getNetwork(CHANNEL_NAME);
    //        Contract contract = network.getContract(CONTRACT_NAME);
    //        byte[] result = contract.submitTransaction("queryReceipt", receiptId);
    //        System.out.println(new String(result));
    //        return new String(result);
    //    }
    //
    //}

    //前端需求SDK
    //权威机构：查询所有公司信息
    public static ArrayList getCompaniesInfo(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryCompanies");

            ArrayList<String> arrayList = JSONObject.parseObject(new String(result), ArrayList.class);
            ArrayList<Company> res = new ArrayList<>();
            for (String s : arrayList) {
                res.add(JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",s)),Company.class));
            }
            return res;
        }
    }

    //权威机构：查询所有银行信息
    public static ArrayList getBanksInfo(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryBanks");

            ArrayList<String> arrayList = JSONObject.parseObject(new String(result), ArrayList.class);
            ArrayList<Bank> res = new ArrayList<>();
            for (String s : arrayList) {
                res.add(JSONObject.parseObject(new String(contract.submitTransaction("queryBank",s)),Bank.class));
            }
            return res;
        }
    }

    //权威机构：查询所有票据信息
    //返回的是公司或银行名称
    public static ArrayList getReceiptsInfo(String orgName, String userName) throws IOException, ContractException, TimeoutException, InterruptedException {
        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryReceipts");

            ArrayList<String> arrayList = JSONObject.parseObject(new String(result), ArrayList.class);
            ArrayList<Receipt> receipts = new ArrayList<>();
            for (String s : arrayList) {
                receipts.add(JSONObject.parseObject(new String(contract.submitTransaction("queryReceipt",s)),Receipt.class));
            }

            ArrayList<Receipt> res = new ArrayList<>();
            for(Receipt receipt : receipts){

                String companyNameFrom = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdFrom())),Company.class).getCompanyName();
                Company companyTo = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdTo())),Company.class);
                Bank bankTo = JSONObject.parseObject(new String(contract.submitTransaction("queryBank",receipt.getBankIdTo())),Bank.class);
                String companyNameTo;
                String bankName;
                if(companyTo != null){
                    companyNameTo = companyTo.getCompanyName();
                    bankName = null;
                }else {
                    companyNameTo = null;
                    bankName = bankTo.getBankName();
                }
                receipt.setCompanyIdFrom(companyNameFrom);
                receipt.setCompanyIdTo(companyNameTo);
                receipt.setBankIdTo(bankName);
                res.add(receipt);
            }
            return res;
        }
    }


    //根据公司id查询发起票据
    //名称
    public static ArrayList getCompanyReceiptsFromById(String orgName, String userName,String id) throws IOException, ContractException, TimeoutException, InterruptedException {
        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryCompany",id);

            Company company = JSONObject.parseObject(new String(result), Company.class);
            ArrayList<Receipt> receipts = new ArrayList<>();
            for (String s : company.getReceiptId_AsFrom()) {
                Receipt receipt = JSONObject.parseObject(contract.submitTransaction("queryReceipt", s), Receipt.class);
                receipts.add(receipt);
            }

            ArrayList<Receipt> res = new ArrayList<>();
            for(Receipt receipt : receipts){
                String companyNameFrom = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdFrom())),Company.class).getCompanyName();
                Company companyTo = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdTo())),Company.class);
                Bank bankTo = JSONObject.parseObject(new String(contract.submitTransaction("queryBank",receipt.getBankIdTo())),Bank.class);
                String companyNameTo;
                String bankName;
                if(companyTo != null){
                    companyNameTo = companyTo.getCompanyName();
                    bankName = null;
                }else {
                    companyNameTo = null;
                    bankName = bankTo.getBankName();
                }
                receipt.setCompanyIdFrom(companyNameFrom);
                receipt.setCompanyIdTo(companyNameTo);
                receipt.setBankIdTo(bankName);
                res.add(receipt);
            }
            return res;
        }
    }

    //根据公司id查询收到票据
    //名称
    public static ArrayList getCompanyReceiptsToById(String orgName, String userName,String id) throws IOException, ContractException, TimeoutException, InterruptedException {
        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryCompany",id);

            Company company = JSONObject.parseObject(new String(result), Company.class);
            ArrayList<Receipt> receipts = new ArrayList<>();
            for (String s : company.getReceiptId_Asto()) {
                Receipt receipt = JSONObject.parseObject(contract.submitTransaction("queryReceipt", s), Receipt.class);
                receipts.add(receipt);
            }

            ArrayList<Receipt> res = new ArrayList<>();
            for(Receipt receipt : receipts){
                String companyNameFrom = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdFrom())),Company.class).getCompanyName();
                Company companyTo = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdTo())),Company.class);
                Bank bankTo = JSONObject.parseObject(new String(contract.submitTransaction("queryBank",receipt.getBankIdTo())),Bank.class);
                String companyNameTo;
                String bankName;
                if(companyTo != null){
                    companyNameTo = companyTo.getCompanyName();
                    bankName = null;
                }else {
                    companyNameTo = null;
                    bankName = bankTo.getBankName();
                }
                receipt.setCompanyIdFrom(companyNameFrom);
                receipt.setCompanyIdTo(companyNameTo);
                receipt.setBankIdTo(bankName);
                res.add(receipt);
            }
            return res;
        }
    }


    //根据银行id查询收到票据
    //名称
    public static ArrayList getBankReceiptsToById(String orgName, String userName,String id) throws IOException, ContractException, TimeoutException, InterruptedException {
        try (Gateway gateway = getGateway(orgName,userName)) {
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = contract.submitTransaction("queryBank",id);

            Bank bank = JSONObject.parseObject(new String(result), Bank.class);
            ArrayList<Receipt> receipts = new ArrayList<>();
            for (String s : bank.getReceiptId_Asto()) {
                Receipt receipt = JSONObject.parseObject(contract.submitTransaction("queryReceipt", s), Receipt.class);
                receipts.add(receipt);
            }

            ArrayList<Receipt> res = new ArrayList<>();
            for(Receipt receipt : receipts){
                String companyNameFrom = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdFrom())),Company.class).getCompanyName();
                Company companyTo = JSONObject.parseObject(new String(contract.submitTransaction("queryCompany",receipt.getCompanyIdTo())),Company.class);
                Bank bankTo = JSONObject.parseObject(new String(contract.submitTransaction("queryBank",receipt.getBankIdTo())),Bank.class);
                String companyNameTo;
                String bankName;
                if(companyTo != null){
                    companyNameTo = companyTo.getCompanyName();
                    bankName = null;
                }else {
                    companyNameTo = null;
                    bankName = bankTo.getBankName();
                }
                receipt.setCompanyIdFrom(companyNameFrom);
                receipt.setCompanyIdTo(companyNameTo);
                receipt.setBankIdTo(bankName);
                res.add(receipt);
            }
            return res;
        }
    }








}

