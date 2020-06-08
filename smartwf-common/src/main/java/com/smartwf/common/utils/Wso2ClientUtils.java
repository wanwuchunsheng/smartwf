package com.smartwf.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.smartwf.common.pojo.User;
import com.smartwf.common.wso2.Wso2Config;
/**
 * 
 * wso2身份验证授权控制器
 * @author WCH
 * 
 * */
public class Wso2ClientUtils {
	
	/**
     * 说明：wso2请求
     *   code换取token/请求用户信息
     * @param redUri 返回的路径
     * 
     * */
    public static String reqWso2Token(Wso2Config wso2Config,Map<String,Object> idtmap, Object reftoken) {
    	Map<String,String> headers=new HashMap<String,String>();
        headers.put("client_id",String.valueOf(idtmap.get("clientKey")));
        headers.put("client_secret",String.valueOf(idtmap.get("clientSecret")));
        headers.put("grant_type", "authorization_code");
        headers.put("code",String.valueOf(reftoken));//code换取token
        headers.put("redirect_uri",String.valueOf(idtmap.get("redirectUri")));
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
        headers.put("redirect_uri",user.getRedirectUrI());
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

}
