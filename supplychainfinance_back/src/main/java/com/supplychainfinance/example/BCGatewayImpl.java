package com.supplychainfinance.example;

//import com.markerhub.entity.*;
//import com.markerhub.gateway.BCGateway;
//
//import org.hyperledger.fabric.gateway.*;
//import org.springframework.stereotype.Repository;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.concurrent.TimeoutException;
//
//@Repository
//public class BCGatewayImpl implements BCGateway {
//
//    @Override
//    public String hi(String orgName, String userName)
//            throws IOException, ContractException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.evaluateTransaction("Hi");
//            return new String(result);
//        }
//    }
//
//    @Override
//    public String queryStrategies(String orgName, String userName)
//            throws IOException, ContractException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.evaluateTransaction("QueryStrategies");
//            return new String(result);
//        }
//    }
//
//
//    @Override
//    public String queryStrategy(String orgName, String userName, String strategyName)
//            throws IOException, ContractException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.evaluateTransaction("QueryStrategy", strategyName);
//            return new String(result);
//        }
//    }
//
//    @Override
//    public void addStrategy(String orgName, String userName, Strategy strategy)
//            throws IOException, ContractException, TimeoutException, InterruptedException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.submitTransaction("AddStrategy", strategy.getStrategyName(),
//                    strategy.getProvider(),strategy.getDescription(),strategy.getMaxDrawdown(),
//                    strategy.getAnnualReturn(),strategy.getSharpeRatio(),
//                    strategy.getState(),strategy.getDate());
//            System.out.println(new String(result));
//        }
//    }
//
//    @Override
//    public String queryPlanningTrades(String orgName, String userName, String strategyName)
//            throws IOException, ContractException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.evaluateTransaction("QueryPlanningTrades", strategyName);
//            return new String(result);
//        }
//    }
//
//    @Override
//    public void addPlanningTrade(String orgName, String userName, String strategyName, PlanningTrade planningTrade)
//            throws IOException, ContractException, TimeoutException, InterruptedException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.submitTransaction("AddPlanningTrade", strategyName,
//                    planningTrade.getStockId(),planningTrade.getStockName(),planningTrade.getAction(),
//                   planningTrade.getAmount(),planningTrade.getHash(),planningTrade.getDate());
//            System.out.println(new String(result));
//        }
//    }
//
//    @Override
//    public String queryPositions(String orgName, String userName, String strategyName)
//            throws IOException, ContractException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.evaluateTransaction("QueryPositions", strategyName);
//            return new String(result);
//        }
//    }
//
//    @Override
//    public void addPosition(String orgName, String userName, String strategyName, Position position)
//            throws IOException, ContractException, TimeoutException, InterruptedException {
//
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.submitTransaction("AddPosition", strategyName,position.getStockId(),
//                    position.getStockName(),position.getPositionPct(),
//                    position.getCost(),position.getCloseNewest(),
//                    position.getAmount(),position.getValue(),
//                   position.getReturnRate());
//            System.out.println(new String(result));
//        }
//    }
//
//    @Override
//    public String queryTrades(String orgName, String userName, String strategyName)
//            throws IOException, ContractException {
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.evaluateTransaction("QueryTrades", strategyName);
//            return new String(result);
//        }
//    }
//
//    @Override
//    public void addTrade(String orgName, String userName, String strategyName, String stockId, String stockName,
//                         String amount, String transactionPrice, String transactionValue,
//                         String commission, String date)
//            throws IOException, ContractException, TimeoutException, InterruptedException {
//
//        Trade trade = new Trade();
//
//        Gateway.Builder builder = getBuilder(orgName, userName);
//        //create a gateway connection
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(CHANNEL_NAME);
//            Contract contract = network.getContract(CONTRACT_NAME);
//
//            byte[] result = contract.submitTransaction("AddTrade", strategyName,trade.getStockId(),
//                    trade.getStockName(),trade.getAction(),trade.getAmount(),trade.getTransactionPrice(),
//                    trade.getTransactionValue(),trade.getCommission(),trade.getDate());
//            System.out.println(new String(result));
//        }
//    }
//
//
//
//    public Gateway.Builder getBuilder(String orgName, String userName) throws IOException{
//        //get user identity from wallet.
//        Path walletPath = Paths.get("wallet", orgName);
//        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
//        Identity identity = wallet.get(userName);
//
//        //check identity existence in wallet
//        if (identity == null) {
//            System.out.println("The identity \"" + userName + "@"+ orgName + "\" doesn't exists in the wallet");
//            return null;
//        }
//
//        //load connection profile
//        Path networkConfigPath = Paths.get( "profiles", orgName, "connection.json");
//        Gateway.Builder builder = Gateway.createBuilder();
//        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);
//
//        return builder;
//    }
//}
