package com.supplychainfinance.contract;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.ArrayList;

@org.hyperledger.fabric.contract.annotation.Contract(
        name = "Contract",
        info = @Info(
                title = "Contract",
                description = "Supply Chain Finance",
                version = "1.0.0",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "2963624004@qq.com",
                        name = "Ferron"
                )
        )
)
@Default
final public class Contract implements ContractInterface {

    /**
     * 初始化合约：初始化权威机构、公司、银行、单据列表，初始化一个权威机构
     * @param ctx
     * @param authorityId
     */
    @Transaction(name = "init", intent = Transaction.TYPE.SUBMIT)
    public void init(final Context ctx, final String authorityId) {

        ChaincodeStub stub = ctx.getStub();

        //初始权威机构、公司、银行、单据列表
        ArrayList<String> authorities = new ArrayList<>();
        ArrayList<String> companies = new ArrayList<>();
        ArrayList<String> banks = new ArrayList<>();
        ArrayList<String> receipts = new ArrayList<>();

        //初始化权威机构
        Authority authority = new Authority();
        authority.setAuthorityId(authorityId);
        authority.setAuthorityName("供应链金融区块链开发小组");
        authorities.add(authority.getAuthorityId());

        //添加权威机构、公司、银行、单据列表
        stub.putStringState("Authorities",JSON.toJSONString(authorities));
        stub.putStringState("Companies",JSON.toJSONString(companies));
        stub.putStringState("Banks",JSON.toJSONString(banks));
        stub.putStringState("Receipts",JSON.toJSONString(receipts));

        //添加一个权威机构
        if(authority.getAuthorityId() != null){
            stub.putStringState("Authority:"+authority.getAuthorityId(), JSON.toJSONString(authority));
        }
    }

    /**
     * 添加公司：只有权威机构才能使用
     * @param ctx
     * @param companyId
     * @param companyName
     * @param core
     * @param receiptId_AsFrom
     * @param receiptId_Asto
     */
    @Transaction(name = "addCompany", intent = Transaction.TYPE.SUBMIT)
    public void addCompany(final Context ctx, final String companyId, final String companyName, final String core , final String receiptId_AsFrom, final String receiptId_Asto) {

        ChaincodeStub stub = ctx.getStub();
        Company company = new Company(companyId, companyName, core, JSONObject.parseObject(receiptId_AsFrom, ArrayList.class), JSONObject.parseObject(receiptId_Asto, ArrayList.class));

        //添加公司
        stub.putStringState("Company:"+company.getCompanyId(),JSON.toJSONString(company));
        //将公司Id添加到公司列表中
        ArrayList<String> companies = JSONObject.parseObject(stub.getStringState("Companies"), ArrayList.class);
        companies.add(company.getCompanyId());
        stub.putStringState("Companies",JSON.toJSONString(companies));
    }

    /**
     * 添加银行：只有权威机构才能使用
     * @param ctx
     * @param bankId
     * @param bankName
     * @param receiptId_Asto
     */
    @Transaction(name = "addBank", intent = Transaction.TYPE.SUBMIT)
    public void addBank(final Context ctx, final String bankId, final String bankName, final String receiptId_Asto) {

        ChaincodeStub stub = ctx.getStub();
        Bank bank = new Bank(bankId, bankName, JSONObject.parseObject(receiptId_Asto, ArrayList.class));

        //添加银行
        stub.putStringState("Bank:"+bank.getBankId(),JSON.toJSONString(bank));
        //将银行数据添加到银行列表中
        ArrayList<String> banks = JSONObject.parseObject(stub.getStringState("Banks"), ArrayList.class);
        banks.add(bank.getBankId());
        stub.putStringState("Banks",JSON.toJSONString(banks));
    }

    /**
     * 创建新单据：核心公司使用，只会流向企业而不会流向银行
     * @param ctx
     * @param receiptId
     * @param companyIdFrom
     * @param companyIdTo
     * @param amount
     * @param maturityDate
     */
    @Transaction(name = "addReceipt", intent = Transaction.TYPE.SUBMIT)
    public void addReceipt(final Context ctx, final String receiptId, final String companyIdFrom, final String companyIdTo, final String amount, final String maturityDate) {

        ChaincodeStub stub = ctx.getStub();
        Receipt receipt = new Receipt(receiptId, companyIdFrom, companyIdTo, "null", amount, maturityDate);

        //添加票据
        stub.putStringState("Receipt:"+receipt.getReceiptId(),JSON.toJSONString(receipt));
        //更新票据列表
        ArrayList<String> receipts = JSONObject.parseObject(stub.getStringState("Receipts"), ArrayList.class);
        receipts.add(receipt.getReceiptId());
        stub.putStringState("Receipts",JSON.toJSONString(receipts));

        //更新单据发行公司的单据列表
        Company companyFrom = JSONObject.parseObject(stub.getStringState("Company:" + receipt.getCompanyIdFrom()), Company.class);
        ArrayList<String> receiptId_asFrom = companyFrom.getReceiptId_AsFrom();
        receiptId_asFrom.add(receipt.getReceiptId());
        companyFrom.setReceiptId_AsFrom(receiptId_asFrom);
        stub.putStringState("Company:"+receipt.getCompanyIdFrom(),JSON.toJSONString(companyFrom));

        //更新单据接收公司的单据列表
        Company companyTo = JSONObject.parseObject(stub.getStringState("Company:" + receipt.getCompanyIdTo()), Company.class);
        ArrayList<String> receiptId_asto = companyTo.getReceiptId_Asto();
        receiptId_asto.add(receipt.getReceiptId());
        companyTo.setReceiptId_Asto(receiptId_asto);
        stub.putStringState("Company:"+receipt.getCompanyIdTo(),JSON.toJSONString(companyTo));


    }

    /**
     * 小微企业使用票据：对银行或对其他小微企业
     * @param ctx
     * @param receiptId
     * @param companyIdFrom
     * @param companyIdTo
     * @param bankIdTo
     * @param amount
     * @param maturityDate
     */
    @Transaction(name = "useReceipt", intent = Transaction.TYPE.SUBMIT)
    public void useReceipt(final Context ctx, final String receiptId, final String companyIdFrom, final String companyIdTo, final String bankIdTo, final String amount, final String maturityDate) {

        ChaincodeStub stub = ctx.getStub();
        Receipt receipt = new Receipt(receiptId, companyIdFrom, companyIdTo, bankIdTo,amount, maturityDate);


        Company companyFrom = JSONObject.parseObject(stub.getStringState("Company:" + receipt.getCompanyIdFrom()), Company.class);
        //计算账户余额
        double balance=0;
		String tmp = "";
        for (String out_receiptId : companyFrom.getReceiptId_AsFrom()) {
            tmp = JSONObject.parseObject(stub.getStringState("Receipt:"+out_receiptId),Receipt.class).getAmount();
             balance -= Double.parseDouble(tmp);
        }
        for (String in_receiptId : companyFrom.getReceiptId_Asto()) {
            tmp =JSONObject.parseObject(stub.getStringState("Receipt:"+in_receiptId),Receipt.class).getAmount();
			balance += Double.parseDouble(tmp);
        }
        //余额大于等于单据金额，则被允许使用单据
        if(balance>= Double.parseDouble(receipt.getAmount()) ){
            //添加该单据信息
            stub.putStringState("Receipt:"+receipt.getReceiptId(),JSON.toJSONString(receipt));
            //将单据id添加到单据列表
            ArrayList<String> receipts = JSONObject.parseObject(stub.getStringState("Receipts"), ArrayList.class);
            receipts.add(receipt.getReceiptId());
            stub.putStringState("Receipts",JSON.toJSONString(receipts));
            //更新单据发行公司的单据列表
            ArrayList<String> receiptId_asFrom = companyFrom.getReceiptId_AsFrom();
            receiptId_asFrom.add(receipt.getReceiptId());
            companyFrom.setReceiptId_AsFrom(receiptId_asFrom);
            stub.putStringState("Company:"+receipt.getCompanyIdFrom(),JSON.toJSONString(companyFrom));
            //更新单据收到方(银行或公司)的单据列表
            ArrayList<String> receiptId_asto;
            if(!receipt.getCompanyIdTo().equals("null")){
                Company companyTo = JSONObject.parseObject(stub.getStringState("Company:" + receipt.getCompanyIdTo()), Company.class);
                receiptId_asto = companyTo.getReceiptId_Asto();
                receiptId_asto.add(receipt.getReceiptId());
                companyTo.setReceiptId_Asto(receiptId_asto);
                stub.putStringState("Company:"+receipt.getCompanyIdTo(),JSON.toJSONString(companyTo));
            }else{
                Bank bankTo = JSONObject.parseObject(stub.getStringState("Bank:" + receipt.getBankIdTo()), Bank.class);
                receiptId_asto = bankTo.getReceiptId_Asto();
                receiptId_asto.add(receipt.getReceiptId());
                bankTo.setReceiptId_Asto(receiptId_asto);
                stub.putStringState("Bank:"+receipt.getBankIdTo(),JSON.toJSONString(bankTo));
            }
        }
    }

    /**
     * 企业承兑票据：对象为小微企业或者银行
     * 单据承兑：核心企业向银行承兑，核心企业向小微企业承兑，小微企业向小微企业承兑
     * @param ctx
     * @param receiptId
     */
    @Transaction(name = "acceptReceipt", intent = Transaction.TYPE.SUBMIT)
    public void acceptReceipt(final Context ctx, final String receiptId) {

        ChaincodeStub stub = ctx.getStub();

        //查找该票据
        Receipt receipt = JSONObject.parseObject(stub.getStringState("Receipt:" + receiptId), Receipt.class);
        //删除该单据信息
        stub.delState("Receipt:"+receipt.getReceiptId());
        //更新单据列表
        ArrayList<String> receipts = JSONObject.parseObject(stub.getStringState("Receipts"), ArrayList.class);
        receipts.remove(receipt.getReceiptId());
        stub.putStringState("Receipts",JSON.toJSONString(receipts));

        //更新单据发行公司的单据列表
        Company companyFrom = JSONObject.parseObject(stub.getStringState("Company:" + receipt.getCompanyIdFrom()),Company.class);
        ArrayList<String> receiptId_asFrom = companyFrom.getReceiptId_AsFrom();
        receiptId_asFrom.remove(receipt.getReceiptId());
        companyFrom.setReceiptId_AsFrom(receiptId_asFrom);
        stub.putStringState("Company:"+receipt.getCompanyIdFrom(),JSON.toJSONString(companyFrom));

        //更新单据收到方(银行或公司)的单据列表
        ArrayList<String> receiptId_asto;
        if(!receipt.getCompanyIdTo().equals("null")){
            Company companyTo = JSONObject.parseObject(stub.getStringState("Company:" + receipt.getCompanyIdTo()), Company.class);
            receiptId_asto = companyTo.getReceiptId_Asto();
            receiptId_asto.remove(receipt.getReceiptId());
            companyTo.setReceiptId_Asto(receiptId_asto);
            stub.putStringState("Company:"+receipt.getCompanyIdTo(),JSON.toJSONString(companyTo));
        }else{
            Bank bankTo = JSONObject.parseObject(stub.getStringState("Bank:" + receipt.getBankIdTo()), Bank.class);
            receiptId_asto = bankTo.getReceiptId_Asto();
            receiptId_asto.remove(receipt.getReceiptId());
            bankTo.setReceiptId_Asto(receiptId_asto);
            stub.putStringState("Bank:"+receipt.getBankIdTo(),JSON.toJSONString(bankTo));
        }

    }

    /**
     * 查询企业的账户票据余额
     * @param ctx
     * @param companyId
     * @return
     */
    @Transaction(name = "queryBalance", intent = Transaction.TYPE.EVALUATE)
    public String queryBalance(final Context ctx, final String companyId) {
        ChaincodeStub stub = ctx.getStub();

        Company companyFrom = JSONObject.parseObject(stub.getStringState("Company:" + companyId), Company.class);
        //计算账户余额
        double balance=0;
		String tmp = "";
        for (String out_receiptId : companyFrom.getReceiptId_AsFrom()) {
            tmp =JSONObject.parseObject(stub.getStringState("Receipt:"+out_receiptId),Receipt.class).getAmount();
			balance -= Double.parseDouble(tmp);
        }
        for (String in_receiptId : companyFrom.getReceiptId_Asto()) {
            tmp = JSONObject.parseObject(stub.getStringState("Receipt:"+in_receiptId),Receipt.class).getAmount();
			balance += Double.parseDouble(tmp);
        }
        return String.valueOf(balance);
    }

    /**
     * 查询银行的账户票据余额
     * @param ctx
     * @param bankId
     * @return
     */
    @Transaction(name = "queryBankBalance", intent = Transaction.TYPE.EVALUATE)
    public String queryBankBalance(final Context ctx, final String bankId) {
        ChaincodeStub stub = ctx.getStub();

        Bank bank = JSONObject.parseObject(stub.getStringState("Bank:" + bankId), Bank.class);
        //计算账户余额
        double balance=0;
        String tmp = "";
        for (String in_receiptId : bank.getReceiptId_Asto()) {
            tmp = JSONObject.parseObject(stub.getStringState("Receipt:"+in_receiptId),Receipt.class).getAmount();
            balance += Double.parseDouble(tmp);
        }
        return String.valueOf(balance);
    }

    /**
     * 查询全部的权威机构
     * @param ctx
     * @return 权威机构id的列表
     */
    @Transaction(name = "queryAuthorities", intent = Transaction.TYPE.EVALUATE)
    public String queryAuthorities(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Authorities");
    }

    /**
     * 查询全部的公司
     * @param ctx
     * @return 公司id的列表
     */
    @Transaction(name = "queryCompanies", intent = Transaction.TYPE.EVALUATE)
    public String queryCompanies(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Companies");
    }

    /**
     * 查询全部的银行
     * @param ctx
     * @return 银行id的列表
     */
    @Transaction(name = "queryBanks", intent = Transaction.TYPE.EVALUATE)
    public String queryBanks(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Banks");
    }

    /**
     * 查询全部的单据
     * @param ctx
     * @return 单据id的列表
     */
    @Transaction(name = "queryReceipts", intent = Transaction.TYPE.EVALUATE)
    public String queryReceipts(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Receipts");
    }

    /**
     * 按照权威机构的id查询权威机构
     * @param ctx
     * @param authorityId
     * @return
     */
    @Transaction(name = "queryAuthority", intent = Transaction.TYPE.EVALUATE)
    public String queryAuthority(final Context ctx, String authorityId) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Authority:"+authorityId);
    }

    /**
     * 按照公司的id查询公司
     * @param ctx
     * @param companyId
     * @return
     */
    @Transaction(name = "queryCompany", intent = Transaction.TYPE.EVALUATE)
    public String queryCompany(final Context ctx, String companyId) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Company:"+companyId);
    }

    /**
     * 按照银行的id查询银行
     * @param ctx
     * @param bankId
     * @return
     */
    @Transaction(name = "queryBank", intent = Transaction.TYPE.EVALUATE)
    public String queryBank(final Context ctx, String bankId) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Bank:"+bankId);
    }

    /**
     * 按照单据的id查询单据
     * @param ctx
     * @param receiptId
     * @return
     */
    @Transaction(name = "queryReceipt", intent = Transaction.TYPE.EVALUATE)
    public String queryReceipt(final Context ctx, String receiptId) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Receipt:"+receiptId);
    }

}
