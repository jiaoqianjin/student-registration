package com.marchsoft.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Description： 获取经纬坐标工具类
 *
 * @author jiaoqianjin
 * Date: 2021/7/29 15:55
 **/

public class GetLatAndLngByAddressUtils {
    final static String ADDRESS_TO_LONGITUDEA_URL = "http://restapi.amap.com/v3/geocode/geo?output=JSON";

    public static String getLatAndLngByAddress(String addr) {
        String address = "";
        String lat = "";
        String lng = "";
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = ADDRESS_TO_LONGITUDEA_URL + "&key=" + "ff9fd1da116dbced4c0ebce7fc44ece9" + "&address=" + address;
        URL myURL = null;
        URLConnection httpsConn = null;
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    return data;
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
