package com.smartwf.common.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.wso2.Wso2Config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/

@Slf4j
public class CommonUtils {
	
	/**
	 * 未登录
	 *    code换取token
	 * 
	 * */
	public static boolean getAccessTokenByCode(HttpServletRequest request , RedisService redisService,Wso2Config wso2Config) {
    	//获取code验证是否授权
    	String code=request.getParameter("code");
    	if(StringUtils.isBlank(code)) {
    		log.warn("未登录！参数code为空异常{}，请求uri：{}", code, request.getRequestURI());
    		throw new CommonException(Constants.UNAUTHORIZED, "未登录！参数code异常！")  ;
    	}
    	//获取redirect_uri并验证
    	String redirectUri=request.getParameter("redirect_uri");
    	if(StringUtils.isBlank(redirectUri)) {
    		log.warn("未登录！参数redirect_uri异常{}，请求uri：{}", code, request.getRequestURI());
    		throw new CommonException(Constants.UNAUTHORIZED, "未登录！参数redirect_uri异常！");
    	}
    	//获取client_id
    	String clientId=request.getParameter("client_id");
    	if(StringUtils.isBlank(clientId)) {
    		log.warn("未登录！参数client_id异常{}，请求uri：{}", code, request.getRequestURI());
    		throw new CommonException(Constants.UNAUTHORIZED, "未登录！参数client_id异常！");
    	}
    	//验证client_id是否有效
    	String isres=redisService.get(clientId);
    	if(StringUtils.isBlank(isres)) {
    		log.warn("未登录！参数clientId异常，请求uri：{}", clientId, request.getRequestURI());
    		throw new CommonException(Constants.UNAUTHORIZED, "未登录！参数clientId异常！");
    	}
    	//获取 session_state{用于注销用户}
    	String sessionState=request.getParameter("session_state");
    	if(StringUtils.isBlank(sessionState)) {
    		log.warn("未登录！参数session_state异常，请求uri：{}", code, request.getRequestURI());
    		throw new CommonException(Constants.UNAUTHORIZED, "未登录！参数session_state异常！");
    	}
    	//调用wso2换取access_token
    	Map<String,Object> idtmap=JSONUtil.parseObj(isres);
    	OAuthClientResponse oAuthResponse=Wso2ClientUtils.getOauthClientToAccessToken(wso2Config,idtmap,code,redirectUri);
    	//验证code换取access_token是否成功
    	if(oAuthResponse==null || StringUtils.isBlank(oAuthResponse.getParam(Constants.ACCESSTOKEN))) {
    		log.warn("未登录！code换取accessToken失败{}，请求uri：{}", JSONUtil.toJsonStr(oAuthResponse), request.getRequestURI());
    		throw new CommonException(Constants.UNAUTHORIZED, "未登录！code换取accessToken异常！");
    	}
    	//封装对象，存储值
    	User user= new User();
    	final String sessionId = UUID.randomUUID().toString();
    	user.setDateTime(DateUtil.currentSeconds()+Convert.toLong(oAuthResponse.getParam("expires_in")));
    	user.setClientKey(String.valueOf(idtmap.get("clientKey")));
    	user.setClientSecret(String.valueOf(idtmap.get("clientSecret")));
    	user.setRefreshToken(String.valueOf(oAuthResponse.getParam("refresh_token")));
    	user.setAccessToken(String.valueOf(oAuthResponse.getParam("access_token")));
    	user.setIdToken(String.valueOf(oAuthResponse.getParam("id_token")));
    	user.setSessionId(sessionId);
    	user.setSessionState(sessionState);
    	//设置过期时间
    	UserThreadLocal.setUser(user);
    	redisService.set(sessionId,JSONUtil.toJsonStr(user) ,wso2Config.tokenRefreshTime);
    	return true;
	}
	
	
	/**
	 * 已登录
	 *   验证sessionId，
	 *    accessToken是否有效
	 * 
	 * */
	public static User verifyAccessToken(HttpServletRequest request , RedisService redisService,Wso2Config wso2Config,String sessionId) {
        //1验证sessionId是否失效
        String mapStr = redisService.get(sessionId);
        if (StringUtils.isBlank(mapStr)) {
        	log.warn("请求失败！sessionId过期：{}，用户请求uri：{}", sessionId, request.getRequestURI());
        	throw new CommonException(Constants.UNAUTHORIZED, "请求失败！sessionId过期，请重新登录！");
        }
        //2验证accessToken是否失效{可以由wso2帮忙验证，可以由过期时间验证，快过期前刷新下}
        User user= JSONUtil.toBean(mapStr, User.class);
    	long nowDateTimes=DateUtil.currentSeconds();
        if( (user.getDateTime()-nowDateTimes)< Constants.TOKEN_TIMEOUT ) {
    	    //if(!Wso2ClientUtils.reqWso2CheckToken(wso2Config,user)) {}
    		//重置wso2令牌时间
        	OAuthClientResponse oAuthResponse =Wso2ClientUtils.refreshAccessToken(wso2Config, user);
        	//验证刷新
        	if(oAuthResponse==null || StringUtils.isBlank(oAuthResponse.getParam(Constants.ACCESSTOKEN))) {
        		log.warn("accesstoken刷新失败：{}，用户请求uri：{}", sessionId, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "accesstoken刷新失败！请重新登录！");
        	}
    		//刷新成功，更新之前保存的wso2相关信息
	    	user.setAccessToken(oAuthResponse.getParam("access_token"));
	    	user.setRefreshToken(oAuthResponse.getParam("refresh_token"));
	    	user.setIdToken(oAuthResponse.getParam("id_token"));
	    	user.setDateTime(DateUtil.currentSeconds()+Convert.toLong(oAuthResponse.getParam("expires_in")));
    	};
    	//3重置redis过期时间
    	UserThreadLocal.setUser(user);
        redisService.set(sessionId,JSONUtil.toJsonStr(user),wso2Config.tokenRefreshTime);
        return user;
	}

}
 