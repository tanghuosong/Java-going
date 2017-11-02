package com.rbac.utils;

/**
 * @author: ths
 * @date: 2017/11/2
 * @time: 14:02
 * @description:
 */
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 将String进行base64编码解码，使用utf-8
 */
public class Base64Util {

    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);

    private static final String UTF_8 = "UTF-8";
    private static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static String decodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.decodeBase64(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
            logger.error(inputData, e);
        }

        return null;
    }

    /**
     * 对给定的字符串进行base64加密操作
     */
    public static String encodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.encodeBase64(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
            logger.error(inputData, e);
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(Base64Util.encodeData("A"));
    }

}