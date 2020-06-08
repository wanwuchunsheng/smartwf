package com.smartwf.common.utils;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.wso2.Wso2Config;

import lombok.extern.slf4j.Slf4j;

/**

 * @Date: 2018/11/5 15:09
 * @Description: 登录工具类
 */
@Component
@Slf4j
public class LoginUtils {
    public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler, RedisService redisService,Wso2Config wso2Config) throws Exception{	
    	//1.判断是否登录
        String token = request.getHeader(Constants.SMARTWF_TOKEN);
        if (StringUtils.isBlank(token)) {
        	/** 
        	 * 未登录
        	 * 
        	 *  */
        	//2.获取code验证是否授权
        	String code=request.getParameter("code");
        	if(StringUtils.isBlank(code)) {
        		log.warn("登录失败code：{}，用户请求uri：{}", token, request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "登录失败！参数code异常！");
        	}
        	//3.获取redirect_uri并验证
        	String redirectUri=request.getParameter("redirect_uri");
        	if(StringUtils.isBlank(redirectUri)) {
        		log.warn("登录失败redirect_uri：{}，用户请求uri：{}", token, request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "登录失败！参数redirect_uri异常！");
        	}
        	//4.授权后，通过code换取token
        	Map<String,Object> idtmap=JsonUtil.jsonToMap(redisService.get(MD5Utils.md5(redirectUri)));
        	Map<String,Object> tkmap=JsonUtil.jsonToMap(Wso2ClientUtils.reqWso2Token(wso2Config,idtmap,code));
        	for(Entry<String, Object> m:tkmap.entrySet()) {
        		log.info("================"+m.getKey()+"    "+m.getValue());
        	}
        	//5验证code换取token是否成功
        	if(!tkmap.containsKey("access_token")) {
        		log.warn("参数已过期：{}，用户请求uri：{}", JsonUtil.objectToJson(tkmap), request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "参数已过期！");
        	}
        	//6刷新token
        	User user= new User();
        	user.setRedirectUrI(redirectUri);
        	user.setClientKey(String.valueOf(idtmap.get("clientKey")));
        	user.setClientSecret(String.valueOf(idtmap.get("clientSecret")));
        	user.setRefreshToken(String.valueOf(tkmap.get("refresh_token")));
        	Map<String,Object> refmap=JsonUtil.jsonToMap(Wso2ClientUtils.reqWso2RefToken(wso2Config,user));
        	for(Entry<String, Object> m:refmap.entrySet()) {
        		log.info(m.getKey()+"    "+m.getValue());
        	}
	    	//7.验证刷新
	    	if(StringUtils.isBlank(String.valueOf(refmap.get("refresh_token")))) {
	        	log.warn("token失效：{}，用户请求uri：{}", token, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "用户登录已失效！请重新登录！");
	    	}
        	//8.生成新token用户子系统通信，保存redis
        	String smartwfToken=MD5Utils.md5(code);
        	user.setClientKey(String.valueOf(idtmap.get("clientKey")));
        	user.setClientSecret(String.valueOf(idtmap.get("clientSecret")));
        	user.setRedirectUrI(redirectUri);
        	user.setAccessToken(String.valueOf(refmap.get("access_token")));
        	user.setRefreshToken(String.valueOf(refmap.get("refresh_token")));
        	user.setIdToken(String.valueOf(refmap.get("id_token")));
        	user.setSmartwfToken(smartwfToken);
        	user.setCode(code);
        	redisService.set(smartwfToken,JsonUtil.objectToJson(user) ,wso2Config.tokenRefreshTime);//过期时间50分钟
        }else {
	        /** 
	         * 已登录
	         * 
	         *  */
	        //9.验证token是否失效
	        String mapStr = redisService.get(token);
	        if (StringUtils.isBlank(mapStr)) {
	        	log.warn("token失效：{}，用户请求uri：{}", token, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "用户登录已失效！请重新登录！");
	        }
	        //10.重置wso2令牌时间
	    	User user=JsonUtil.jsonToPojo(mapStr, User.class);
	    	Map<String,Object> refmap=JsonUtil.jsonToMap(Wso2ClientUtils.reqWso2RefToken(wso2Config,user));
	    	for(Entry<String, Object> m:refmap.entrySet()) {
	    		log.info("Token刷新返回结果："+m.getKey()+"    "+m.getValue());
	    	}
	    	//11.验证刷新
	    	if(StringUtils.isBlank(String.valueOf(refmap.get("refresh_token")))) {
	        	log.warn("token失效：{}，用户请求uri：{}", token, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "用户登录已失效！请重新登录！");
	    	}
	    	//12.刷新成功，更新之前保存的wso2相关信息
	    	user.setAccessToken(String.valueOf(refmap.get("access_token")));
	    	user.setRefreshToken(String.valueOf(refmap.get("refresh_token")));
	    	user.setIdToken(String.valueOf(refmap.get("id_token")));
	    	redisService.set(token,JsonUtil.objectToJson(user) ,wso2Config.tokenRefreshTime);//过期时间50分钟
        }
        return true;
    }
}
