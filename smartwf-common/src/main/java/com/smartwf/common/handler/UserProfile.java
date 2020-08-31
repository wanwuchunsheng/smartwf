package com.smartwf.common.handler;

import javax.servlet.http.HttpServletRequest;

import com.smartwf.common.pojo.User;

public class UserProfile {
	
	
	/**
	 * 获取用户基础信息
	 *    通用方法封装
	 * @author WCH
	 * @date 2020-8-31 17:31:58
	 * 
	 * */
	public static User getUser(HttpServletRequest request) {
		User userInfo=(User) request.getAttribute("userInfo");
		return userInfo;
	}

}
