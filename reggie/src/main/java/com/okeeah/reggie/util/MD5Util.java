package com.okeeah.reggie.util;

import org.springframework.util.DigestUtils;



/**
 * @Description MD5加盐工具类
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/24
 */
public class MD5Util {
    public static void main(String[] args) {
        String lisi = encode("123456", "admin");
        System.out.println(lisi);
    }

    public static String  encode(String password, String slat){
        //加盐
        return DigestUtils.md5DigestAsHex((slat + password).getBytes());

    }
}
