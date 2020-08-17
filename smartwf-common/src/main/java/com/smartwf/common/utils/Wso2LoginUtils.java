package com.smartwf.common.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.wso2.Wso2Config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 登录工具类
 *    服务端不托管过期验证，由wso2授权服务器验证
 * @author WCH
 */ 
@Component
@Slf4j
public class Wso2LoginUtils {
    public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler, RedisService redisService,Wso2Config wso2Config) throws Exception{	
    	//1.判断是否登录
    	log.info("请求类型："+request.getMethod()+"===="+request.getRequestURI());
    	 final Optional<Cookie> appIdCookie = CommonUtils.getAppIdCookie(request);
         if (appIdCookie.isPresent()) {
        	/** 
        	 * 未登录
        	 * 
        	 *  */
        	//2.获取code验证是否授权
        	String code=request.getParameter("code");
        	if(StringUtils.isBlank(code)) {
        		log.warn("登录失败code：{}，用户请求uri：{}", code, request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "登录失败！参数code异常！");
        	}
        	//3.获取redirect_uri并验证
        	String redirectUri=request.getParameter("redirect_uri");
        	if(StringUtils.isBlank(redirectUri)) {
        		log.warn("登录失败redirect_uri：{}，用户请求uri：{}", code, request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "登录失败！参数redirect_uri异常！");
        	}
        	//4.获取client_id
        	String clientId=request.getParameter("client_id");
        	if(StringUtils.isBlank(clientId)) {
        		log.warn("登录失败client_id：{}，用户请求uri：{}", code, request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "登录失败！参数client_id异常！");
        	}
        	//5.获取state
        	//6.获取 session_state{用于注销用户}
        	String sessionState=request.getParameter("session_state");
        	if(StringUtils.isBlank(sessionState)) {
        		log.warn("登录失败session_state：{}，用户请求uri：{}", code, request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "登录失败！参数session_state异常！");
        	}
        	//7.授权后，通过code换取token
        	Map<String,Object> idtmap=JSONUtil.parseObj(redisService.get(clientId));
        	Map<String,Object> tkmap=JSONUtil.parseObj(Wso2ClientUtils.reqWso2Token(wso2Config,idtmap,code,redirectUri));
        	for(Entry<String, Object> m:tkmap.entrySet()) {
        		log.info("================"+m.getKey()+"    "+m.getValue());
        	}
        	//8.验证code换取token是否成功
        	if(!tkmap.containsKey(Constants.ACCESSTOKEN)) {
        		log.warn("参数已过期：{}，用户请求uri：{}", JSONUtil.toJsonStr(tkmap), request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "参数已过期！");
        	}
        	//9.存储值
        	User user= new User();
        	user.setDateTime(DateUtil.currentSeconds()+Convert.toLong(tkmap.get("expires_in")));
        	user.setClientKey(String.valueOf(idtmap.get("clientKey")));
        	user.setClientSecret(String.valueOf(idtmap.get("clientSecret")));
        	user.setRefreshToken(String.valueOf(tkmap.get("refresh_token")));
        	user.setAccessToken(String.valueOf(tkmap.get("access_token")));
        	user.setIdToken(String.valueOf(tkmap.get("id_token")));
        	user.setFlag( Convert.toBool(idtmap.get("flag")));
        	user.setRedirectUri(redirectUri);
        	user.setSmartwfToken(Md5Utils.md5(code));
        	user.setCode(code);
        	user.setSessionState(sessionState);
        	//向浏览器写入cookie
        	final String sessionId = UUID.randomUUID().toString();
        	final Cookie cookie = new Cookie("AppID",  URLEncoder.encode(JSONUtil.toJsonStr(user), "utf-8"));
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            response.addCookie(cookie);
            //10过期时间
        	redisService.set(sessionId,JSONUtil.toJsonStr(user) ,wso2Config.tokenRefreshTime);
        }else {
	        /** 
	         * 已登录
	         * 
	         * */
        	 System.out.println(URLDecoder.decode(appIdCookie.get().getValue(),"utf-8"));
        	 User user=JSONUtil.toBean(URLDecoder.decode(appIdCookie.get().getValue(),"utf-8"),User.class) ;
             log.info(JSONUtil.toJsonStr(user));
           
        	
        	
        	
        	/*
        	
	        //11.验证token是否失效
	        String mapStr = redisService.get(token);
	        if (StringUtils.isBlank(mapStr)) {
	        	log.warn("请求失败！token过期：{}，用户请求uri：{}", token, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "请求失败！token过期，请重新登录！");
	        }
	        //12.验证accessToken是否失效{可以由wso2帮忙验证，可以由过期时间验证，快过期前刷新下}
	        User user= JSONUtil.toBean(mapStr, User.class);
	    	if(!Wso2ClientUtils.reqWso2CheckToken(wso2Config,user)) {
	    		//long nowDateTimes=DateUtil.currentSeconds();
	    	    //if( (user.getDateTime()-nowDateTimes)< Constants.TOKEN_TIMEOUT ) {}
	    		//13.重置wso2令牌时间
		    	Map<String,Object> refmap=JSONUtil.parseObj(Wso2ClientUtils.reqWso2RefToken(wso2Config,user));
		    	for(Entry<String, Object> m:refmap.entrySet()) {
		    		log.info("Token刷新返回结果："+m.getKey()+"    "+m.getValue());
		    	}
		    	//14.验证刷新
		    	if(refmap.containsKey("error")) {
		    		log.warn("accesstoken刷新失败：{}，用户请求uri：{}", token, request.getRequestURI());
		        	throw new CommonException(Constants.UNAUTHORIZED, "accesstoken刷新失败！请重新登录！");
		    	}
	    		//15.刷新成功，更新之前保存的wso2相关信息
		    	user.setAccessToken(String.valueOf(refmap.get("access_token")));
		    	user.setRefreshToken(String.valueOf(refmap.get("refresh_token")));
		    	user.setIdToken(String.valueOf(refmap.get("id_token")));
		    	user.setDateTime(DateUtil.currentSeconds()+Convert.toLong(refmap.get("expires_in")));
	    	};
	    	//16.重置redis过期时间
	        redisService.set(token,JSONUtil.toJsonStr(user),wso2Config.tokenRefreshTime);
	        UserThreadLocal.setUser(user);
	        */
	        /**
	         * 通过登录验证后，继续验证api接口权限
	         *
	         *
	    	 if(!Wso2ClientUtils.entitlementApiReq(request,wso2Config,user)){
	    		 log.warn("accesstoken授权失败：{}，用户请求uri：{}", token, request.getRequestURI());
	        	 throw new CommonException(Constants.FORBIDDEN, "api接口访问无权限！");
	    	 };
	         */
        }
        return true;
    }
}
