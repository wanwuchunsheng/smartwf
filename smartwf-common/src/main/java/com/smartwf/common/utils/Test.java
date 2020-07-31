package com.smartwf.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Test {
	
	public static void main(String[] args) {
		
		//String str=
		
		
		
		/**
		String str="{\"9a1158154dfa42caddbd0694a4e9bdc8\":[{\"pwdQuestionAnswer2\":null,\"pwdQuestionAnswer3\":null,\"createUserId\":null,\"loginCode\":\"jack\",\"sign\":null,\"qqOpenid\":null,\"remark\":null,\"mgrType\":0,\"pwdQuestion\":null,\"createUserName\":null,\"userCode\":null,\"enable\":null,\"id\":175,\"email\":null,\"pwdQuestion2\":null,\"address\":null,\"updateUserId\":null,\"wxOpenid\":null,\"sex\":0,\"updateUserName\":null,\"mobile\":null,\"updateTime\":null,\"avatar\":null,\"userName\":\"jack\",\"pwdQuestionAnswer\":null,\"phone\":null,\"createTime\":null,\"tenantId\":52,\"pwd\":null,\"status\":null,\"pwdQuestion3\":null}],\"c4ca4238a0b923820dcc509a6f75849b\":[]}";
		
		Map<String,Object> map= JSONUtil.parseObj(str);
		List<Map> mapList=JSONUtil.toList(JSONUtil.parseArray(map.get("9a1158154dfa42caddbd0694a4e9bdc8")), Map.class); 
		for(Map<String,Object> v: mapList) {
			Map<String,Object> mp= JSONUtil.parseObj(v);
			for( Entry<String, Object>  m: mp.entrySet()) {
				System.out.println(m.getKey()+"    "+m.getValue());
			}
		}
		*/
		/**
		String str="<?xml version='1.0' encoding='UTF-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><soapenv:Fault><faultcode>soapenv:Server</faultcode><faultstring>Domain is not available to register</faultstring><detail><ns:TenantMgtAdminServiceException xmlns:ns=\"http://services.mgt.tenant.carbon.wso2.org\"><ns:TenantMgtAdminServiceException><axis2ns24:Message xmlns:axis2ns24=\"http://services.mgt.tenant.carbon.wso2.org\">Domain is not available to register</axis2ns24:Message></ns:TenantMgtAdminServiceException></ns:TenantMgtAdminServiceException></detail></soapenv:Fault></soapenv:Body></soapenv:Envelope>";
	    Map<String,Object> map= XmlUtil.xmlToMap(str);
	    log.info(JSONUtil.toJsonStr(map));
	    
	    for(Entry<String, Object> m: map.entrySet()) {
	        System.out.println(m.getKey()+"     "+m.getValue());	
	    }
	    */
		/**
		String res="{\"Response\":[{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/subsys/health/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/org/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/log/operate/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/subsys/monitor/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/subsys/asset/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/log/exception/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/iam/permission/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/base/dict/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/iam\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/position/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/log\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Permit\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/tenant/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/iam/action/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/iam/resource/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/iam/role/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/iam/user/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/subsys\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}},{\"Decision\":\"Deny\",\"Status\":{\"StatusCode\":{\"Value\":\"urn:oasis:names:tc:xacml:1.0:status:ok\"}},\"Action\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"ui.view\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"/base/subsys/list\",\"IncludeInResult\":\"true\",\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}}]}";
		System.out.println(JSONUtil.parseObj(res));
		*/
		/**
		System.out.println(DateUtil.currentSeconds());
		System.out.println(Convert.toLong(100));
		System.out.println(DateUtil.currentSeconds()+Convert.toLong(100));
		 
		 */
		
		
		
	}

}
