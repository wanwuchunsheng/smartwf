package com.smartwf.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.User;
import com.smartwf.common.wso2.Wso2Config;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
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
    	Map<String,String> headers=new HashMap<String,String>(16);
        headers.put("client_id",String.valueOf(idtmap.get("clientKey")));
        headers.put("client_secret",String.valueOf(idtmap.get("clientSecret")));
        headers.put("grant_type", "authorization_code");
        //code换取token
        headers.put("code",String.valueOf(reftoken));
        headers.put("redirect_uri",redirectUri);
        String url=new StringBuffer().append(wso2Config.userServerUri).append("/oauth2/token").toString();
    	return HttpClientUtil.doPost(url , headers,"utf-8");
    }
    
    /**
     * wso2请求-刷新token
     * @param type 验证刷新/换取token/请求用户信息
     * @param redUri 返回的路径
     * @param code 用户编码
     * */
    public static String reqWso2RefToken(Wso2Config wso2Config,User user) {
    	Map<String,String> headers=new HashMap<String,String>(16);
        headers.put("client_id",user.getClientKey());
        headers.put("client_secret",user.getClientSecret());
        headers.put("grant_type", "refresh_token"); 
        //token刷新
        headers.put("refresh_token", user.getRefreshToken()); 
        headers.put("redirect_uri",user.getRedirectUri());
        String url=new StringBuffer().append(wso2Config.userServerUri).append("/oauth2/token").toString();
    	return HttpClientUtil.doPost(url, headers,"utf-8");
    }
    
    /**
     * wso2请求-查询详细
     *    登录成功后， 查询用户信息
     * @param accesstoken 
     * */
    public static String reqWso2UserInfo(Wso2Config wso2Config,User user) {
    	Map<String,String> headers=new HashMap<String,String>(16);
        headers.put("Authorization",new StringBuffer().append("Bearer ").append(user.getAccessToken()).toString());
        String url=new StringBuffer().append(wso2Config.userServerUri).append("/oauth2/userinfo").toString();
    	return HttpClientUtil.get(url,headers);
    }
    
    /**
     * wso2请求-api接口授权
     * @param user
     * @param wso2Config
     * @return boolean
     * */
    public static Boolean entitlementApiReq(HttpServletRequest request,Wso2Config wso2Config,User user) {
    	try {
        	//封装wso2参数规范
    		StringBuffer sb=new StringBuffer();
    		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://org.apache.axis2/xsd\">");
    		sb.append(" <soapenv:Header/> <soapenv:Body> <xsd:getBooleanDecision> ");
    		sb.append(" <xsd:subject>").append(user.getLoginCode()).append("@").append(user.getTenantDomain()).append("</xsd:subject>");
    		sb.append(" <xsd:resource>").append(request.getRequestURI()).append("</xsd:resource>");
    		sb.append(" <xsd:action>").append(request.getMethod()).append("</xsd:action>");
    		sb.append(" </xsd:getBooleanDecision> </soapenv:Body> ");
    		sb.append(" </soapenv:Envelope> ");
        	log.info("soap start："+DateFormatUtils.formatUTC(new Date(), "yyyy-MM-dd HH:mm:ss"));
        	String res=HttpClientUtil.doPostApiEntitlement(String.valueOf(new StringBuffer().append(wso2Config.userServerUri).append("/services/EntitlementService?wsdl")), String.valueOf(sb),user);
        	log.info("soap end："+ DateFormatUtils.formatUTC(new Date(), "yyyy-MM-dd HH:mm:ss"));
        	log.info("res="+res);
        	//解析xml字符串
        	Document doc= DocumentHelper.parseText(res);
            Element roots=doc.getRootElement();
            String flag = roots.element("Body").getStringValue();
            //返回结果
            return Boolean.valueOf(flag);
		} catch (Exception e) {
			log.error("api权限验证请求异常{}",e.getMessage(),e);
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
	        String postUrl= new StringBuffer().append(wso2Config.userServerUri).append("/t/").append(user.getTenantDomain()).append("/oauth2/introspect?token=").append(user.getAccessToken()).toString();
	        //发送请求
	        String str=HttpClientUtil.expVerification(postUrl, "Basic " + Base64.encodeBase64String(new StringBuffer().append(user.getTenantCode()).append("@").append(user.getTenantDomain()).append(":").append(user.getTenantPw()).toString().getBytes()));
	        if(StringUtils.isNotBlank(str)) {
	        	//转换map
				Map<String,Object> map=JSONUtil.parseObj(str);
	            if(map.containsKey(Constants.ACTIVE )) {
	            	return Boolean.valueOf(map.get("active").toString());
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
	}
	
	/**
     * 功能说明:oauth2客户端调用wso2
     *    刷新accessToken
     * @param wso2Config,user
     * @return 
     * */
    public static OAuthClientResponse refreshAccessToken(Wso2Config wso2Config,User user) {
    	final OAuthClientRequest.TokenRequestBuilder oAuthTokenRequestBuilder = 
				new OAuthClientRequest.TokenRequestBuilder(new StringBuffer().append(wso2Config.userServerUri).append("/oauth2/token").toString());
    	try {
			final OAuthClientRequest accessRequest = oAuthTokenRequestBuilder.setGrantType(GrantType.AUTHORIZATION_CODE)
			        .setClientId(user.getClientKey())
			        .setClientSecret(user.getClientSecret())
			        .setGrantType(GrantType.REFRESH_TOKEN)
			        .setRefreshToken(user.getRefreshToken())
			        .setRedirectURI(user.getRedirectUri())
			        .buildBodyMessage();
			//create OAuth client that uses custom http client under the hood
	        final OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
	        final OAuthClientResponse oAuthResponse = oAuthClient.accessToken(accessRequest);
	        return oAuthResponse;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
	
	
	/**
     * 功能说明:oauth2客户端调用wso2
     *    code换取accessToken信息
     * @param 
     * @return 
     * */
	public static OAuthClientResponse getOauthClientToAccessToken(Wso2Config wso2Config,Map<String,Object> idtmap, String code,String redirectUri) {
		try {
			final OAuthClientRequest.TokenRequestBuilder oAuthTokenRequestBuilder = 
					new OAuthClientRequest.TokenRequestBuilder(new StringBuffer().append(wso2Config.userServerUri).append("/oauth2/token").toString());
	        final OAuthClientRequest accessRequest = oAuthTokenRequestBuilder.setGrantType(GrantType.AUTHORIZATION_CODE)
	                .setClientId(Convert.toStr(idtmap.get("clientKey")))
	                .setClientSecret(Convert.toStr(idtmap.get("clientSecret")))
	                .setRedirectURI(redirectUri)
	                .setCode(code)
	                .buildBodyMessage();
	        //create OAuth client that uses custom http client under the hood
	        final OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
	        final OAuthClientResponse oAuthResponse = oAuthClient.accessToken(accessRequest);
	        return oAuthResponse;
		} catch (Exception e) {
			log.error("code换取accessToken异常！{}",e.getMessage(),e);
		}
       return null;
	}

	
	/**
     * 功能说明:隐藏关键数据，避免暴露前端
     * @param 
     * @return 
     * */
	public static User resUserInfo(User user) {
		user.setClientSecret(null);
		user.setPwd(null);
		user.setRefreshToken(null);
		user.setTenantPw(null);
		user.setRedirectUri(null);
		//user.setSessionState(null);
		//user.setIdToken(null);
		return user;
	}
	
	
	/**
     * 统一注销
     * @param accesstoken 
     * */
    public static String userLogout(Wso2Config wso2Config,User user) {
    	Map<String,Object> map=new HashMap<String,Object>(16);
    	map.put("post_logout_redirect_uri","http://localhost:8080");
    	map.put("session_state",user.getSessionState());
    	map.put("id_token_hint",user.getIdToken());
        String url=new StringBuffer().append(wso2Config.userServerUri).append("/oidc/logout").toString();
        String res=HttpRequest.get(url).header("Authorization",new StringBuffer().append("Bearer ").append(user.getAccessToken()).toString()).form(map).timeout(60000).execute().body();
		log.info("统一注销返回{}",res);
    	return res;
    }
   
    /**
     * wso2请求-密码修改
     * @Author WCH
     * @param wso2Config ,user ,newPwd, oldPwd
     * return 
     * */
    public static String updateUserPwd(Wso2Config wso2Config,User user,String newPwd,String oldPwd) {
    	try {
        	//封装wso2参数规范
    		StringBuffer sb=new StringBuffer();
    		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.ws.um.carbon.wso2.org\"> ");
    		sb.append(" <soapenv:Header/> <soapenv:Body>  <ser:updateCredential> ");
    		sb.append(" <ser:userName>").append(user.getLoginCode()).append("</ser:userName> ");
    		sb.append(" <ser:newCredential>").append(newPwd).append("</ser:newCredential>");
    		sb.append(" <ser:oldCredential>").append(oldPwd).append("</ser:oldCredential>");
    		sb.append(" </ser:updateCredential>  </soapenv:Body> ");
    		sb.append(" </soapenv:Envelope> ");
        	return HttpClientUtil.doPostApiEntitlement(String.valueOf(new StringBuffer().append(wso2Config.userServerUri).append("/services/RemoteUserStoreManagerService?wsdl")), String.valueOf(sb),user);
		} catch (Exception e) {
			log.error("wso2密码修改异常{}",e.getMessage(),e);
		}
    	return Constants.ERROR;
    }
    
}
