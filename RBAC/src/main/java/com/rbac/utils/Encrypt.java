package com.rbac.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @author: ths
 * @date: 2017/10/20
 * @time: 18:03
 * @description: 新建用户生成加密密码以及生成盐
 */

public class Encrypt {

    /**
     * 模拟创建用户时候存入的密码和盐
     * @param password 用户密码
     * @return saltAndPassword SaltAndPassword
     */
    public static SaltAndPassword getSaltAndPassword(String password){

        //散列随机数
        String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();

        //散列迭代次数
        int hashIterations = 1024;
        /*
        application-shiro.xml 中  storedCredentialsHexEncoded=true 则需要 .toHex()
        application-shiro.xml 中  storedCredentialsHexEncoded=false 则需要 .toBase64()
         */
        String passwordSalt = new Sha256Hash(password, salt, hashIterations).toBase64();

        return new SaltAndPassword(salt,passwordSalt);
    }

    public static void main(String[] args){
        SaltAndPassword saltAndPassword = getSaltAndPassword("123456");
        System.out.println(saltAndPassword);
    }

    /**
     *  定义返回数据类型
     */
    static class SaltAndPassword{
        private String salt;
        private String password;

        public SaltAndPassword() {
        }

        public SaltAndPassword(String salt, String password) {
            this.salt = salt;
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "SaltAndPassword{"+'\n' +
                    "   \"salt\":" + salt +"," + '\n'+
                    "   \"password\":" + password + '\n' +
                    '}';
        }
    }
}
