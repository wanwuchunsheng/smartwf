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
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.User;
import com.smartwf.common.wso2.Wso2Config;

import cn.hutool.core.convert.Convert;
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
     * wso2请求-api接口权限验证
     *  sb.append(" <xsd:subject>").append("admin").append("</xsd:subject>");
		sb.append(" <xsd:resource>").append("_system/local/repository/components/org.wso2.carbon.registry/mount/-_system-governance").append("</xsd:resource>");
		sb.append(" <xsd:action>").append("read").append("</xsd:action>");
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
     * wso2请求-api接口权限验证
     * @param 
     * @return boolean
     * */
    public static Boolean entitlementApiReqTest(HttpServletRequest request,Wso2Config wso2Config,User user,String subject,String resource,String action) {
    	try {
        	//封装wso2参数规范
    		StringBuffer sb=new StringBuffer();
    		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://org.apache.axis2/xsd\">");
    		sb.append(" <soapenv:Header/> <soapenv:Body> <xsd:getBooleanDecision> ");
    		sb.append(" <xsd:subject>").append(subject).append("</xsd:subject>");
    		sb.append(" <xsd:resource>").append(resource).append("</xsd:resource>");
    		sb.append(" <xsd:action>").append(action).append("</xsd:action>");
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
     * 功能说明:返回前端用户基础信息，过滤铭感信息
     * @param 
     * @return obj
     * */
	public static User resUserInfo( User user) {
		user.setAccessToken(null);
    	return user;
	}
	
	/**
     * 功能说明:获得授权码
     *    	response_type：表示授权类型，必选项，此处的值固定为"code"
	        client_id：表示客户端的ID，必选项
	        redirect_uri：表示重定向URI，可选项
	        scope：表示申请的权限范围，可选项
	        state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值
     * @param 
     * @return obj
     * */
	public static Object reqWso2AuthoriztionCode() {
		
		/**
        OAuthClientRequest oauthResponse = OAuthClientRequest
                                           .authorizationLocation(ConstantKey.OAUTH_CLIENT_AUTHORIZE)
                                           .setResponseType(OAuth.OAUTH_CODE)
                                           .setClientId(ConstantKey.OAUTH_CLIENT_ID)
                                           .setRedirectURI(ConstantKey.OAUTH_CLIENT_CALLBACK)
                                           .setScope(ConstantKey.OAUTH_CLIENT_SCOPE)
                                           .buildQueryMessage();
        return "redirect:"+oauthResponse.getLocationUri();
        */
       
		return null;
	}
	
	/**
     * 功能说明:oauth2客户端调用wso2
     *    code换取accessToken信息
     * @param 
     * @return 
     * */
	public static OAuthClientResponse reqWso2Token2(Wso2Config wso2Config,Map<String,Object> idtmap, String code,String redirectUri) {
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
	        final String accessToken = oAuthResponse.getParam("access_token");
	        log.info(accessToken);
	        
	        
	        
	        /**
	        oauthAuthzResponse = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            String code = oauthAuthzResponse.getCode();
            OAuthClientRequest oauthClientRequest = OAuthClientRequest
                                                    .tokenLocation(ConstantKey.OAUTH_CLIENT_ACCESS_TOKEN)
                                                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                                                    .setClientId(ConstantKey.OAUTH_CLIENT_ID)
                                                    .setClientSecret(ConstantKey.OAUTH_CLIENT_SECRET)
                                                    .setRedirectURI(ConstantKey.OAUTH_CLIENT_CALLBACK)
                                                    .setCode(code)
                                                    .buildQueryMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
 
            //Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            //application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            //Custom response classes are an easy way to deal with oauth providers that introduce modifications to
            //OAuth 2.0 specification
 
            //获取access token
            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oauthClientRequest, OAuth.HttpMethod.POST);
            String accessToken = oAuthResponse.getAccessToken();
            String refreshToken= oAuthResponse.getRefreshToken();
            Long expiresIn = oAuthResponse.getExpiresIn();
            //获得资源服务
            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(ConstantKey.OAUTH_CLIENT_GET_RESOURCE)
                                                     .setAccessToken(accessToken).buildQueryMessage();
            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String resBody = resourceResponse.getBody();
            logger.info("accessToken: "+accessToken +" refreshToken: "+refreshToken +" expiresIn: "+expiresIn +" resBody: "+resBody);
            model.addAttribute("accessToken",  "accessToken: "+accessToken + " resBody: "+resBody);
	         * */
	        
	        
	        return oAuthResponse;
		} catch (Exception e) {
			log.error("code换取accessToken异常！{}",e.getMessage(),e);
		}
       return null;
	}
}
