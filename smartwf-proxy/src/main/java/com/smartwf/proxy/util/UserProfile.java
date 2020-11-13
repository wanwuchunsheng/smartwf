package com.smartwf.proxy.util;

import javax.servlet.http.HttpServletRequest;

import com.smartwf.proxy.pojo.User;


public class UserProfile {
	
	
	/**
	 * 获取用户基础信息
	 *    通用方法封装
	 * @author WCH
	 * @date 2020-8-31 17:31:58
	 * 
	 * */
	public static User getUser(HttpServletRequest request) {
		try {
			//获取租户id
			String atTennentId = request.getHeader("atTennentId");
			//获取租户域
			String atTennentDomain = request.getHeader("atTennentDomain");
			//获取风场
			String atWindFarm = request.getHeader("atwindFarm");
			User userInfo=(User) request.getAttribute("userInfo");
			userInfo.setAtTennentId(atTennentId);
			userInfo.setAtTennentDomain(atTennentDomain);
			userInfo.setAtWindFarm(atWindFarm);
			return resUserInfo(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 功能说明:隐藏关键数据，避免暴露前端
     * @param 
     * @return 
     * */
	public static User resUserInfo(User user) {
		user.setClientSecret(null);
		user.setPwd(null);
		user.setRefreshToken(null);
		user.setTenantPw(null);
		return user;
	}

}
