package com.smartwf.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;

import lombok.extern.slf4j.Slf4j;

/**

 * @Date: 2018/11/5 15:09
 * @Description: 登录工具类
 */
@Component
@Slf4j
public class LoginUtils {
    public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler, RedisService redisService) throws Exception{
    	 Map<String,String> headers =null;
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
        		//3.未授权重定向登录页面
        		log.warn("未登录：{}，用户请求uri：{}", token, request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "用户未登录！请登录！");
        	}
        	//4.授权后，通过code换取token
        	//String url="https://192.168.1.132:9443/oauth2/token";
        	String url="https://10.104.111.193:9443/oauth2/token";
            headers=new HashMap<String,String>();
            headers.put("client_id", "eKuGjrJu2egL_YGvbL3EALsObyca");
            headers.put("client_secret", "MthWlk6gQY3JrGu61r3swzuG0OIa");
            headers.put("code", code);
            headers.put("grant_type", "authorization_code");
            headers.put("redirect_uri", "http://10.105.227.93:8300/smartwf_sys_backend/globaldata/oauth2client");
        	String str= HttpClientUtil.doPost(url, headers,"utf-8");
        	Map<String,Object> map=JsonUtil.jsonToMap(str);
        	for(Entry<String, Object> m:map.entrySet()) {
        		System.out.println(m.getKey()+"    "+m.getValue());
        		log.error(m.getKey()+"    "+m.getValue());
        	}
        	//4.1验证code换取token是否成功
        	if(!map.containsKey("access_token")) {
        		throw new CommonException(Constants.BAD_REQUEST, "参数已过期！");
        	}
        	//5.生成新token，保存redis
        	String smartwfToken=MD5Utils.md5(code);
        	//封装存储
        	User user= new User();
        	user.setAccessToken(String.valueOf(map.get("access_token")));
        	user.setRefreshToken(String.valueOf(map.get("refresh_token")));
        	user.setSmartwfToken(smartwfToken);
        	user.setCode(code);
        	redisService.set(smartwfToken, JSONArray.toJSONString(user),3000);//过期时间50分钟
        	//6.刷新token过期时间
        	headers = new HashMap<String,String>();
        	headers.put("client_id", "nqenKoLbjFswa_PQY3CcQacsiqka");
        	headers.put("client_secret", "qMS_RXTSfS5NU8JkLuX0NG4opHAa");
        	headers.put("refresh_token", String.valueOf(map.get("refresh_token")));
        	headers.put("grant_type", "refresh_token");
            String str2= HttpClientUtil.doPost(url, headers,"utf-8");
        	Map<String,Object> map2=JsonUtil.jsonToMap(str2);
        	for(Entry<String, Object> m:map2.entrySet()) {
        		log.info("Code换取Token返回结果："+m.getKey()+"    "+m.getValue());
        	}
        }else {
	        /** 
	         * 已登录
	         * 
	         *  */
	        // 7.验证token是否失效
	        String mapStr = redisService.get(token);
	        if (StringUtils.isBlank(mapStr)) {
	            //8.登录失效，重新登录
	        	log.warn("token失效：{}，用户请求uri：{}", token, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "用户登录已失效！请重新登录！");
	        }
	    	//9.重新设置redis时间
	    	redisService.expire(token, 3000);
	        //10.重新设置wso2令牌时间
	    	User user=JsonUtil.jsonToPojo(mapStr, User.class);
	    	String url="https://192.168.1.132:9443/oauth2/token";
	    	headers = new HashMap<String,String>();
	    	headers.put("client_id", "nqenKoLbjFswa_PQY3CcQacsiqka");
	    	headers.put("client_secret", "qMS_RXTSfS5NU8JkLuX0NG4opHAa");
	    	headers.put("refresh_token", user.getRefreshToken());
	    	headers.put("grant_type", "refresh_token");
	        String str2= HttpClientUtil.doPost(url, headers,"utf-8");
	    	Map<String,Object> map2=JsonUtil.jsonToMap(str2);
	    	for(Entry<String, Object> m:map2.entrySet()) {
	    		log.info("Token刷新返回结果："+m.getKey()+"    "+m.getValue());
	    	}
	    	//10.1刷新失败，wso2令牌失效，重新登录
	    	/**
	    	if(StringUtils.isBlank(String.valueOf(map2.get("")))) {
	    		//8.登录失效，重新登录
	        	log.warn("token失效：{}，用户请求uri：{}", token, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "用户登录已失效！请重新登录！");
	    	}
	    	*/
        }
        return true;
    }
    
    
}
