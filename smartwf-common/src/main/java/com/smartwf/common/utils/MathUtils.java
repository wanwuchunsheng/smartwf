package com.smartwf.common.utils;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.smartwf.common.constant.Constants;

/**
 * @author WCH
 * @Description: 通用工具类
 */
public class MathUtils {


    /**
     * 获取文件后缀
     * @param fileName 文件
     * @return
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."),fileName.length());
    }


    /**
     * 获取i位流水号
     *  i=7 位流水号
     * @return
     */
    public static String getSerialNumber(int i) {
        String s = String.valueOf(System.nanoTime());
        String nanoTime = StringUtils.substring(s, s.length() - i, s.length());
        return nanoTime;
    }



    /**
     * 获取真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && ! Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(Constants.CHAR)!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.equalsIgnoreCase(ip, "0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }


    /**
     * 获取文件名称
     * @return key
     */
    public static String getKey() {
        String timestamp = DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        String s = UUID.randomUUID().toString();
        // delete "-"
        String uuid = s.replace("-", "");
        return timestamp + uuid;
    }

}
