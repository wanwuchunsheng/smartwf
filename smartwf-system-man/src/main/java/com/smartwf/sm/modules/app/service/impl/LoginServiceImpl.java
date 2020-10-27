package com.smartwf.sm.modules.app.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.utils.MathUtils;
import com.smartwf.common.utils.Wso2ClientUtils;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.pojo.LoginRecord;
import com.smartwf.sm.modules.admin.service.LoginRecordService;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.app.service.LoginService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private Wso2Config wso2Config;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
    private LoginRecordService loginRecordService;
	
	/**
     * @Description app登录认证
     * @return
     */
	@Override
	public Result<?> userLogin(HttpServletRequest request, User user) {
		String isres=redisService.get(user.getClientKey());
    	if(StringUtils.isBlank(isres)) {
    		log.warn("失败！参数clientKey异常，请求uri：{}", user.getClientKey(), request.getRequestURI());
    		throw new CommonException(Constants.UNAUTHORIZED, "失败！参数clientKey异常！");
    	}
		Map<String,Object> idtmap=JSONUtil.parseObj(isres);
		OAuthClientResponse oAuthResponse=getOauthClientToAccessToken(idtmap,user);
		log.info("手机app端登录返回信息：{}",JSONUtil.toJsonStr(oAuthResponse));
    	final String sessionId = UUID.randomUUID().toString();
    	user.setDateTime(DateUtil.currentSeconds()+Convert.toLong(oAuthResponse.getParam("expires_in")));
    	user.setClientKey(String.valueOf(idtmap.get("clientKey")));
    	user.setClientSecret(String.valueOf(idtmap.get("clientSecret")));
    	user.setRefreshToken(String.valueOf(oAuthResponse.getParam("refresh_token")));
    	user.setAccessToken(String.valueOf(oAuthResponse.getParam("access_token")));
    	user.setIdToken(String.valueOf(oAuthResponse.getParam("id_token")));
    	user.setSessionId(sessionId);
    	//根据accessToken查询用户ID
		String str=Wso2ClientUtils.reqWso2UserInfo(wso2Config, user);
		if(StringUtils.isBlank(str)) {
			throw new CommonException(Constants.UNAUTHORIZED,"授权参数异常，accessToken查询用户信息失败！");
		}
		Map<String,Object> resmap=JSONUtil.parseObj(str);
		//验证是否成功
		if(!resmap.containsKey(Constants.USERID)) {
			throw new CommonException(Constants.UNAUTHORIZED,"授权参数异常，WSO2 user_id为空！");
		}
		user.setUserCode(String.valueOf(resmap.get("user_id")));
		//通过user_id查询用户基础信息	
		User userInfo=this.userInfoService.selectUserInfoByUserCode(user);
		if(null==userInfo) {
			throw new CommonException(Constants.UNAUTHORIZED,"授权参数异常，user_id查询用户信息异常！");
		}
		try {
			//第一次登录，添加登录记录信息
    		String ip=MathUtils.getIpAddress(request);
    		String loginType=HttpClientUtil.getBrowserInfo(request);
    		String deviceName=HttpClientUtil.getDeviceName(request);
    		LoginRecord lr=new LoginRecord();
    		lr.setIpAddress(ip);
    		lr.setLoginType(loginType);
    		lr.setCreateTime(new Date());
    		lr.setLoginCode(userInfo.getLoginCode());
    		lr.setTenantId(userInfo.getTenantId());
    		lr.setLoginTime(lr.getCreateTime());
    		lr.setStatus(Constants.ZERO);
    		lr.setDeviceName(deviceName);
    		this.loginRecordService.addLoginRecord(lr);
		} catch (Exception e) {
			log.error("ERROR：插入登录记录错误！{}-{}",e.getMessage(),e);
		}
		//区分子系统和app端过期时间
		if(StringUtils.isNotBlank(userInfo.getSessionState())) {
			this.redisService.set(userInfo.getSessionId(), JSONUtil.toJsonStr(userInfo),wso2Config.tokenRefreshTime);
		}else {
			this.redisService.set(userInfo.getSessionId(), JSONUtil.toJsonStr(userInfo),Constants.APP_TIMEOUT);
		}
		//成功返回
		return Result.data(Constants.EQU_SUCCESS,Wso2ClientUtils.resUserInfo(userInfo));
	}


	/**
	 * 功能说明:用户名密码换取wso2登录信息
	 * 
	 * @param idtmap
	 * @param user
	 * @return 
	 * */
	public OAuthClientResponse getOauthClientToAccessToken(Map<String,Object> idtmap,User user) {
		try {
			final OAuthClientRequest.TokenRequestBuilder oAuthTokenRequestBuilder = 
					new OAuthClientRequest.TokenRequestBuilder(new StringBuffer().append(wso2Config.userServerUri).append("/oauth2/token").toString());
	        final OAuthClientRequest accessRequest = oAuthTokenRequestBuilder.setGrantType(GrantType.PASSWORD)
	                .setClientId(user.getClientKey())
	                .setUsername(user.getLoginCode())
	                .setPassword(user.getPwd())
	                .setScope("openid")
	                .setClientSecret(Convert.toStr(idtmap.get("clientSecret")))
	                .buildBodyMessage();
	        //create OAuth client that uses custom http client under the hood
	        final OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
	        final OAuthClientResponse oAuthResponse = oAuthClient.accessToken(accessRequest);
	        return oAuthResponse;
		} catch (Exception e) {
			log.error("密码模式换取wso2登录信息异常！{}",e.getMessage(),e);
		}
	   return null;
	}
	
	

}
