package com.smartwf.common.utils;

/**
 * @author WCH
 * 
 * */
public class StrUtils {
	 /**
     * @ 去除前后逗号
     * @param clazz
     * @param xmlStr
     * @return
     */
    public static String regex(String str) {
    	String regex = "^,*|,*$";
    	return str.replaceAll(regex, "");
    }

   
   
}
