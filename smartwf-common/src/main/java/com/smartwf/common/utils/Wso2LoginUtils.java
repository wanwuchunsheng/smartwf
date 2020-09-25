package com.smartwf.common.utils;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.handler.BodyReaderHttpServletRequestWrapper;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.wso2.Wso2Config;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 拦截器
 *    服务端不托管过期验证，由wso2授权服务器验证
 *    1)各子系统使用sessionId调用接口
 *    2)三方应用使用accessToken令牌调用接口
 * @author WCH
 */ 
@Component
@Slf4j
public class Wso2LoginUtils {
	
    public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler, RedisService redisService,Wso2Config wso2Config) throws Exception{	
    	log.info("进入拦截器"+request.getMethod()+"{}"+request.getRequestURI());
    	//1判断是否accessToken令牌请求
    	String accessToken = request.getHeader(Constants.ACCESS_TOKEN);
    	if (StringUtils.isNotBlank(accessToken)) {
    		//获取id_token,并解析id_token获取userId
    		//通过userId查询用户表关联租户管理员账号
    		//请求wso2验证accessToken是否有效
    		//api鉴权
    		return true;
    	}
    	//2判断是否sessionId请求
        String sessionId = request.getHeader(Constants.SESSION_ID);
        if (StringUtils.isBlank(sessionId)) {
        	//未登录{code换取accessToken}
        	return CommonUtils.getAccessTokenByCode(request,redisService,wso2Config);
        }
    	//已登录{认证令牌是否有效，api鉴权}
        User user= CommonUtils.verifyAccessToken(request,redisService,wso2Config,sessionId);
        /**
    	if(!Wso2ClientUtils.entitlementApiReq(request,wso2Config,user)){
    		log.warn("api接口访问无权限：{}，请求uri：{}", sessionId, request.getRequestURI());
        	throw new CommonException(Constants.FORBIDDEN, "api接口访问无权限！");
    	};
    	*/
        //修改请求参数
        request.setAttribute("userInfo",user);
        return true;
    }
}
