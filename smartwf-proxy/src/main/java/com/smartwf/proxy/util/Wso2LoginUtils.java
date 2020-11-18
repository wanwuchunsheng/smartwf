package com.smartwf.proxy.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.smartwf.proxy.constant.Constants;
import com.smartwf.proxy.exception.CommonException;
import com.smartwf.proxy.pojo.Result;
import com.smartwf.proxy.pojo.User;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;


/**
 * @Description: 拦截器
 *    服务端不托管过期验证，由wso2授权服务器验证
 *    1)各子系统使用sessionId调用接口
 *    2)三方应用使用accessToken令牌调用接口
 * @author WCH
 */ 
@Component
public class Wso2LoginUtils {
	
	/**
	 * user
	 * https://portal.windmagics.com/smartwf_sys_endback/globaldata/oauth2client
	 * */
	@Value("${spring.smartwf.system-server-url}")
	public static String systemServerUrl;
	
	/**
	 * 内网拦截器
	 * 
	 * */
    public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{	
    	//获取sessionId
        String sessionId = request.getHeader(Constants.SESSION_ID);
        //验证登录
        if (StringUtils.isEmpty(sessionId)) {
    		throw new CommonException(Constants.UNAUTHORIZED, "未登录！参数sessionId为空！");
        }
        //验证sessionId
		String res = HttpRequest.get(systemServerUrl).header(Constants.SESSION_ID, sessionId).timeout(60000).execute().body();
		//验证返回值
		Result<?> result= JSONUtil.toBean(res, Result.class);
		if(StringUtils.isEmpty(result.getData())) {
			throw new CommonException(Constants.UNAUTHORIZED,  result.getMsg());
		}
		//获取头部其他信息{为门户选中的租户，风场信息。非当前登录人风场}
		String atTennentId = request.getHeader("atTennentId");
		String atTennentDomain = request.getHeader("atTennentDomain");
		String atWindFarm = request.getHeader("atwindFarm");
		User userInfo=(User) result.getData();
		userInfo.setAtTennentId(atTennentId);
		userInfo.setAtTennentDomain(atTennentDomain);
		userInfo.setAtWindFarm(atWindFarm);
		//增加用户信息
		request.setAttribute("userInfo",result.getData());
        return true;
    }
    
   
}
