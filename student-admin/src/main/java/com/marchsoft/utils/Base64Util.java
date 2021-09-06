package com.marchsoft.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;

import java.util.Random;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/29 14:15
 **/

public class Base64Util {

    /**
     *功能描述: 加密
     * @param name 待加密内容
     * @return java.lang.String
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    public static String encode( String name ) {
        String encode = Base64.encode(name);
        StringBuilder sb = new StringBuilder();
        sb.append(encode).insert(4, getRandomString(1));
        return sb.toString();
    }

    /**
     *功能描述: 解密
     * @param encode 待解密内容
     * @return java.lang.String
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    public static String decode( String encode ) {
        String encodeDelete = StrUtil.sub(encode, 0, 4) + StrUtil.sub(encode, 5, encode.length());
        return Base64.decodeStr(encodeDelete);
    }

    /**
     *功能描述: length用户要求产生字符串的长度
     * @param length 生成的长度
     * @return java.lang.String
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    public static String getRandomString( int length ) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
