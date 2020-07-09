package com.smartwf.common.utils;

import java.util.Date;
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
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.wso2.Wso2Config;

import lombok.extern.slf4j.Slf4j;

/**

 * @Date: 2018/11/5 15:09
 * @Description: 登录工具类
 *    服务端托管过期验证
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
        	Map<String,Object> idtmap=JsonUtil.jsonToMap(redisService.get(clientId));
        	Map<String,Object> tkmap=JsonUtil.jsonToMap(Wso2ClientUtils.reqWso2Token(wso2Config,idtmap,code,redirectUri));
        	for(Entry<String, Object> m:tkmap.entrySet()) {
        		log.info("================"+m.getKey()+"    "+m.getValue());
        	}
        	//8.验证code换取token是否成功
        	if(!tkmap.containsKey("access_token")) {
        		log.warn("参数已过期：{}，用户请求uri：{}", JsonUtil.objectToJson(tkmap), request.getRequestURI());
        		throw new CommonException(Constants.UNAUTHORIZED, "参数已过期！");
        	}
        	//9.存储值
        	User user= new User();
        	String day=DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        	//过期时间=当前时间+过期时间
        	user.setDateTime(Long.valueOf(tkmap.get("expires_in").toString()) + DateUtils.getDayTimeToLong(day));
        	user.setClientKey(String.valueOf(idtmap.get("clientKey")));
        	user.setClientSecret(String.valueOf(idtmap.get("clientSecret")));
        	user.setRefreshToken(String.valueOf(tkmap.get("refresh_token")));
        	user.setAccessToken(String.valueOf(tkmap.get("access_token")));
        	user.setIdToken(String.valueOf(tkmap.get("id_token")));
        	user.setRedirectUri(redirectUri);
        	user.setSmartwfToken(MD5Utils.md5(code));
        	user.setCode(code);
        	user.setSessionState(sessionState);
        	log.info("code= "+MD5Utils.md5(code));
        	redisService.set(MD5Utils.md5(code),JsonUtil.objectToJson(user) ,wso2Config.tokenRefreshTime);//过期时间50分钟
        }else {
	        /** 
	         * 已登录
	         * 
	         * */
	        //10.验证token是否失效
	        String mapStr = redisService.get(token);
	        if (StringUtils.isBlank(mapStr)) {
	        	log.warn("请求失败！token过期：{}，用户请求uri：{}", token, request.getRequestURI());
	        	throw new CommonException(Constants.UNAUTHORIZED, "请求失败！token过期，请重新登录！");
	        }
	        //11.验证wso2登录信息是否过期
	        User user=JsonUtil.jsonToPojo(mapStr, User.class);
	        long nowtime=DateUtils.getDayTimeToLong(DateUtils.parseDateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
	        //12.提前60s*15=900过期{过期时间-当前时间},刷新令牌
	        if( (user.getDateTime()-nowtime)<900 ) {
	        	//重置wso2令牌时间
		    	Map<String,Object> refmap=JsonUtil.jsonToMap(Wso2ClientUtils.reqWso2RefToken(wso2Config,user));
		    	for(Entry<String, Object> m:refmap.entrySet()) {
		    		log.info("Token刷新返回结果："+m.getKey()+"    "+m.getValue());
		    	}
		    	//13.验证刷新
		    	if(refmap.containsKey("error")) {
		    		log.warn("accesstoken刷新失败：{}，用户请求uri：{}", token, request.getRequestURI());
		        	throw new CommonException(Constants.FORBIDDEN, "accesstoken刷新失败！请重新登录！");
		    	}
	    		//14.刷新成功，更新之前保存的wso2相关信息
		    	user.setAccessToken(String.valueOf(refmap.get("access_token")));
		    	user.setRefreshToken(String.valueOf(refmap.get("refresh_token")));
		    	user.setIdToken(String.valueOf(refmap.get("id_token")));
		    	String day=DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
		    	user.setDateTime(Long.valueOf(refmap.get("expires_in").toString()) + DateUtils.getDayTimeToLong(day));
	        }
	        //15.重置redis过期时间
	        redisService.set(token,JsonUtil.objectToJson(user),wso2Config.tokenRefreshTime);
	        UserThreadLocal.setUser(user);
	        /**
	         * 通过登录验证后，继续验证api接口权限
	         * 
	    	 if(!Wso2ClientUtils.entitlementApiReq(request,wso2Config,user, handler)){
	    		 log.warn("accesstoken刷新失败：{}，用户请求uri：{}", token, request.getRequestURI());
	        	 throw new CommonException(Constants.FORBIDDEN, "api接口访问无权限！");
	    	 };
	    	 * */
        }
        return true;
    }
}
