package com.supplychainfinance.sdk;

import com.supplychainfinance.example.UserContext;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.File;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;

public class ManageSDK {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }



    /**
     * 添加新管理员
     * chaincode初始化后，平台管理员进行的操作
     * @param tlsCert
     * @param orgName
     * @param orgMSP
     * @param adminName
     * @param adminSecret
     * @throws Exception
     */
    public static void doEnroll(String tlsCert, String orgName, String orgMSP, String adminName, String adminSecret) throws Exception {
        // 加载创建连接的tls文件
        String filePath = Paths.get( "profiles", orgName, "connection.json").toString();
        NetworkConfig config = NetworkConfig.fromJsonFile(new File(filePath));
        NetworkConfig.CAInfo caInfo = config.getOrganizationInfo(orgName).getCertificateAuthorities().get(0);
        String caURL = caInfo.getUrl();
        Properties props = new Properties();
        props.put("pemFile", tlsCert);
        props.put("allowAllHostNames", "true");

        // 加载CA客户端
        HFCAClient caClient = HFCAClient.createNewInstance(caURL, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet", orgName));

        // 检查管理员是否存在wallet中
        Identity adminExists = wallet.get(adminName);
        if (adminExists != null) {
            System.out.println("An identity for the admin user \"admin@"+ orgName + "\" already exists in the wallet");
            return;
        }

        // 管理员登录
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll(adminName, adminSecret, enrollmentRequestTLS);
        Identity user = Identities.newX509Identity(orgMSP, enrollment);
        wallet.put(adminName, user);
        System.out.println("Successfully enrolled user \"admin@"+ orgName + "\" and imported into the wallet");
    }


    /**
     * 注册一个新用户
     * 不管是银行还是公司，都使用该方法在链上对其进行注册
     * 因此，添加一个公司或者银行的过程就是：1)调用注册用户的sdk，2)调用添加银行或者公司的sdk
     * @param tlsCert
     * @param orgName
     * @param orgMSP
     * @param adminName
     * @param userName
     * @param userSecret
     * @throws Exception
     */
    public static void doRegisterUser(String tlsCert, String orgName, String orgMSP, String adminName, String userName, String userSecret) throws Exception {
        // 加载创建连接的tls文件
        String filePath = Paths.get( "profiles", orgName, "connection.json").toString();
        NetworkConfig config = NetworkConfig.fromJsonFile(new File(filePath));
        NetworkConfig.CAInfo caInfo = config.getOrganizationInfo(orgName).getCertificateAuthorities().get(0);
        String caURL = caInfo.getUrl();
        Properties props = new Properties();
        props.put("pemFile", tlsCert);
        props.put("allowAllHostNames", "true");

        // 加载CA客户端
        HFCAClient caClient = HFCAClient.createNewInstance(caURL, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet", orgName));

        // 检查管理员是否存在wallet中
        X509Identity adminIdentity = (X509Identity) wallet.get(adminName);
        if (adminIdentity == null) {
            System.out.println("\"" + adminName + "@" + orgName + "\" needs to be enrolled and added to the wallet first");
            return;
        }

        // 检查该用户是否存在wallet中
        if (wallet.get(userName) != null) {
            System.out.println("An identity for the user \"" + userName + "@" + orgName + "\" already exists in the wallet");
            return;
        }

        // 获取管理员上下文
        Enrollment adminKeys = new Enrollment() {
            @Override
            public PrivateKey getKey() {
                return adminIdentity.getPrivateKey();
            }
            @Override
            public String getCert() {
                return Identities.toPemString(adminIdentity.getCertificate());
            }
        };
        User admin = new UserContext(adminName, orgMSP, adminKeys);

        // 用户注册
        RegistrationRequest registrationRequest = new RegistrationRequest(userName);
        registrationRequest.setSecret(userSecret);
        caClient.register(registrationRequest, admin);
        // 用户登录
        Enrollment enrollment = caClient.enroll(userName, userSecret);
        Identity user = Identities.newX509Identity(orgMSP, enrollment);
        wallet.put(userName, user);
        System.out.println("Successfully enrolled user \"" + userName + "@" + orgName + "\" and imported into the wallet");
    }
}
