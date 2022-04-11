package com.supplychainfinance.common.constant;

public class FabricConstant {

    // ORG1的admin的私钥文件目录、私钥名称、证书目录、证书名称
    public static final String org1keyFolderPath = "./src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore";
    public static final String org1keyFileName="edabbc0194516b074aafd3c2f6b993da80e866117cd1eeec926999c02a1dfd8d_sk";
    public static final String org1certFoldePath="./src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/admincerts";
    public static final String org1certFileName="Admin@org1.example.com-cert.pem";


    //ORG2的admin的私钥文件目录、私钥名称、证书目录、证书名称
    public static final String org2keyFolderPath = "./src/main/resources/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/keystore";
    public static final String org2keyFileName = "0965ced938372650e1bb0f46743c519112469d366e007e3fbb559332711a1e78_sk";
    public static final String org2certFoldePath = "./src/main/resources/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/admincerts";
    public static final String org2certFileName = "Admin@org2.example.com-cert.pem";

    // Orderer节点的TLS证书地址和文件名
    public static final String tlsOrderFilePath = "./src/main/resources/crypto-config/ordererOrganizations/example.com/tlsca/tlsca.example.com-cert.pem";
    public static final String tlsOrderFileName = "tlsca.example.com-cert.pem";

    // channel test生成的tx文件路径
    public static final String testTxFilePath = "./src/main/resources/test.tx";
    // channel test1生成的tx文件路径
    public static final String test1TxFilePath = "./src/main/resources/test1.tx";

    // ORG1 peer0节点的TLS证书
    public static final String org1tlsPeer0FilePath = "./src/main/resources/crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/msp/tlscacerts/tlsca.org1.example.com-cert.pem";
    // ORG1 peer1节点的TLS证书
    public static final String org1tlsPeer1FilePath = "./src/main/resources/crypto-config/peerOrganizations/org1.example.com/peers/peer1.org1.example.com/msp/tlscacerts/tlsca.org1.example.com-cert.pem";

    // ORG1 peer的tls证书路径
    public static final String org1tlsPeerFilePath = "./src/main/resources/crypto-config/peerOrganizations/org1.example.com/tlsca/tlsca.org1.example.com-cert.pem";
    // ORG2 peer的tls证书路径
    public static final String org2tlsPeerFilePath = "./src/main/resources/crypto-config/peerOrganizations/org2.example.com/tlsca/tlsca.org2.example.com-cert.pem";



}
