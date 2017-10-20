package com.rbac.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @author: ths
 * @date: 2017/10/20
 * @time: 18:03
 * @description:
 */

public class Encrypt {

    /**
     * 模拟创建用户时候存入的密码和盐
     * @param password
     */
    public static String credent(String password){

        //散列随机数
        String salt = createSalt(password);
        //散列迭代次数
        int hashIterations=1024;
        /*
        application-shiro.xml 中  storedCredentialsHexEncoded=true 则需要 .toHex()
        application-shiro.xml 中  storedCredentialsHexEncoded=false 则需要 .toBase64()
         */

        return new Sha256Hash(password, "9Se1CbOxZNFC7a9PaiKZCQ==", hashIterations).toBase64();
        /*
            效果等同于上句代码
            SimpleHash hash = new SimpleHash("SHA-256", password, salt, hashIterations);
         */
    }

    public static String createSalt(String password){
        return new SecureRandomNumberGenerator().nextBytes().toBase64();
    }

}
