package com.smartwf.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Test {
	
	public static void main(String[] args) {
		String str="<?xml version='1.0' encoding='UTF-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><soapenv:Fault><faultcode>soapenv:Server</faultcode><faultstring>Domain is not available to register</faultstring><detail><ns:TenantMgtAdminServiceException xmlns:ns=\"http://services.mgt.tenant.carbon.wso2.org\"><ns:TenantMgtAdminServiceException><axis2ns24:Message xmlns:axis2ns24=\"http://services.mgt.tenant.carbon.wso2.org\">Domain is not available to register</axis2ns24:Message></ns:TenantMgtAdminServiceException></ns:TenantMgtAdminServiceException></detail></soapenv:Fault></soapenv:Body></soapenv:Envelope>";
	    Map<String,Object> map= XmlUtil.xmlToMap(str);
	    log.info(JSONUtil.toJsonStr(map));
	    
	    for(Entry<String, Object> m: map.entrySet()) {
	        System.out.println(m.getKey()+"     "+m.getValue());	
	    }
	    
	}

}
