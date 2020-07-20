package com.smartwf.sm.modules.wso2.service.impl;

import org.springframework.stereotype.Service;

import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
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
		//封装wso2请求参数
		return null;
	}
	
	
	
}
