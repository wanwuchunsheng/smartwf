package com.smartwf.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;

import com.smartwf.common.annotation.RequiresPermissions;
import com.smartwf.common.pojo.User;
import com.smartwf.common.wso2.Wso2Config;

import lombok.extern.log4j.Log4j2;
/**
 * 
 * wso2身份验证授权控制器
 * @author WCH
 * 
 * */
@Log4j2
public class Wso2ClientUtils {
	
	/**
     * 说明：wso2请求
     *   code换取token/请求用户信息
     * @param redUri 返回的路径
     * 
     * */
    public static String reqWso2Token(Wso2Config wso2Config,Map<String,Object> idtmap, Object reftoken,String redirectUri) {
    	Map<String,String> headers=new HashMap<String,String>();
        headers.put("client_id",String.valueOf(idtmap.get("clientKey")));
        headers.put("client_secret",String.valueOf(idtmap.get("clientSecret")));
        headers.put("grant_type", "authorization_code");
        headers.put("code",String.valueOf(reftoken));//code换取token
        headers.put("redirect_uri",redirectUri);
        String url=new StringBuffer().append(wso2Config.tokenServerUri).append("/oauth2/token").toString();
    	return HttpClientUtil.doPost(url , headers,"utf-8");
    }
    
    /**
     * wso2请求-刷新token
     * @param type 验证刷新/换取token/请求用户信息
     * @param redUri 返回的路径
     * @param code 用户编码
     * */
    public static String reqWso2RefToken(Wso2Config wso2Config,User user) {
    	Map<String,String> headers=new HashMap<String,String>();
        headers.put("client_id",user.getClientKey());
        headers.put("client_secret",user.getClientSecret());
        headers.put("grant_type", "refresh_token"); 
        headers.put("refresh_token", user.getRefreshToken()); //token刷新
        headers.put("redirect_uri",user.getRedirectUri());
        String url=new StringBuffer().append(wso2Config.tokenServerUri).append("/oauth2/token").toString();
    	return HttpClientUtil.doPost(url, headers,"utf-8");
    }
    
    /**
     * wso2请求-查询详细
     *    登录成功后， 查询用户信息
     * @param accesstoken 
     * */
    public static String reqWso2UserInfo(Wso2Config wso2Config,User user) {
    	Map<String,String> headers=new HashMap<String,String>();
        headers.put("Authorization",new StringBuffer().append("Bearer ").append(user.getAccessToken()).toString());
        String url=new StringBuffer().append(wso2Config.tokenServerUri).append("/oauth2/userinfo").toString();
    	return HttpClientUtil.get(url,headers);
    }
    
    /**
     * wso2请求-api接口权限验证
     * @param 
     * @return boolean
     * */
    public static Boolean entitlementApiReq(HttpServletRequest request,Wso2Config wso2Config,User user,Object handler) {
    	try {
    		HandlerMethod handlerMethod = (HandlerMethod)handler;
            RequiresPermissions annotation = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            String[] value = annotation.value();
            //验证本地api是否提供参数
            if( null!=value && value.length>0 ) {
            	//封装wso2参数规范
        		StringBuffer sb=new StringBuffer();
        		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://org.apache.axis2/xsd\">");
        		sb.append(" <soapenv:Header/> <soapenv:Body> <xsd:getBooleanDecision> ");
        		sb.append(" <xsd:subject>").append(user.getLoginCode()).append("</xsd:subject>");
        		sb.append(" <xsd:resource>").append(request.getRequestURI()).append("</xsd:resource>");
        		sb.append(" <xsd:action>").append(value[0]).append("</xsd:action>");
        		sb.append(" </xsd:getBooleanDecision> </soapenv:Body> ");
        		sb.append(" </soapenv:Envelope> ");
            	log.info("soap start："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
            	String res=HttpClientUtil.doPostApiEntitlement(String.valueOf(new StringBuffer().append(wso2Config.tokenServerUri).append("/services/EntitlementService?wsdl")), String.valueOf(sb),user.getTenantCode());
            	log.info("soap end："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
            	log.info("res="+res);
            }
		} catch (Exception e) {
			log.info("api权限验证请求异常{}",e.getMessage(),e);
		}
    	return false;
    }
    
    /**
     * wso2请求-api接口Token验证
     * @param 
     * @return boolean
     * */
	public static boolean reqWso2CheckToken(Wso2Config wso2Config, User user) {
		try {
			//拼接url
	        String postUrl= new StringBuffer().append(wso2Config.userServerUri).append("/oauth2/introspect?token=").append(user.getAccessToken()).toString();
	        //发送请求
	        String str=HttpClientUtil.expVerification(postUrl, wso2Config.userAuthorization);
	        if(StringUtils.isNotBlank(str)) {
	        	//转换map
	        	Map<String,Object> map=JsonUtil.jsonToMap(str);
	            if(map.containsKey("active")) {
	            	return Boolean.valueOf(map.get("active").toString());
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
	}
	
	/**
     * 功能说明:返回前端用户基础信息，过滤铭感信息
     * @param 
     * @return obj
     * */
	public static User resUserInfo( User user) {
		user.setAccessToken(null);
    	return user;
	}
}
