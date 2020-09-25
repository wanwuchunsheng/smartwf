package com.smartwf.common.handler;

import javax.servlet.http.HttpServletRequest;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.User;
import com.smartwf.common.utils.Wso2ClientUtils;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
			return Wso2ClientUtils.resUserInfo(userInfo);
		} catch (Exception e) {
			log.info("获取用户信息异常{}-{}",e,e.getMessage());
		}
		return null;
	}

}
