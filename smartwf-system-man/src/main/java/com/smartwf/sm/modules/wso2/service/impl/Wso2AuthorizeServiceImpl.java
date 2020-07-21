package com.smartwf.sm.modules.wso2.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.wso2.service.Wso2AuthorizeService;

import lombok.extern.log4j.Log4j2;

/**
 * 功能说明：wso2授权管理服务层
 * @author WCH
 * 
 * */
@Service
@Log4j2
public class Wso2AuthorizeServiceImpl implements Wso2AuthorizeService {
	

	@Autowired
	private Wso2Config wso2Config;

	/**
	 * 说明：UI批量授权服务器
	 * @author WCH
	 * @DateTime 2020-7-20 17:36:27
	 * @return
	 * */
	@Override
	public String batchUiAuthorization(String jsonStr) {
		//获取登录人信息
		User user = UserThreadLocal.getUser();
		//封装http请求头
		Map<String,String> headers=new HashMap<>(16);
		headers.put("content-type", "application/json");
		StringBuffer sb=new StringBuffer();
		sb.append(user.getTenantCode()).append("@").append(user.getTenantDomain()).append(":").append(user.getTenantPw());
		//sb.append(user.getTenantCode()).append(":").append(user.getTenantPw());
		headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
        //拼接uri
        sb=new StringBuffer();
        sb.append(wso2Config.userServerUri).append("/api/identity/entitlement/decision/pdp");
        String data="{\"Request\":{\"http://wso2.org/identity/user\":[{\"Attribute\":[{\"AttributeId\":\"http://wso2.org/identity/user/username\",\"Value\":\"adminUser\",\"IncludeInResult\":true,\"DataType\":\"string\"}]},{\"Attribute\":[{\"AttributeId\":\"http://wso2.org/identity/user/username\",\"Value\":\"publicUser\",\"IncludeInResult\":true,\"DataType\":\"string\"}]}],\"Resource\":{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\",\"Value\":\"index.jsp\",\"IncludeInResult\":true,\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},\"Action\":[{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"view-welcome\",\"IncludeInResult\":true,\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"view-status\",\"IncludeInResult\":true,\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"view-summary\",\"IncludeInResult\":true,\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]},{\"Attribute\":[{\"AttributeId\":\"urn:oasis:names:tc:xacml:1.0:action:action-id\",\"Value\":\"modify-welcome\",\"IncludeInResult\":true,\"DataType\":\"http://www.w3.org/2001/XMLSchema#string\"}]}]}}";
        //发送请求
        String str=HttpClientUtil.post(String.valueOf(sb), data,headers);
        log.info("UI批量授权返回："+str);
		return str;
	}
	
	
	
}
