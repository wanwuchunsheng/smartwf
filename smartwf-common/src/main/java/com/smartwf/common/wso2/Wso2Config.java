package com.smartwf.common.wso2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * wso2配置
 * @author WCH
 */
@Component
public class Wso2Config {
	
	/**
	 * user
	 * 
	 * */
	@Value("${spring.wso2.user.user-server-uri}")
	public String userServerUri;
	
	@Value("${spring.wso2.user.user-authorization}")
	public String userAuthorization;
	
	/**
	 * token
	 * 
	 * */
	@Value("${spring.wso2.token.token-server-uri}")
	public String tokenServerUri;
	
	@Value("${spring.wso2.token.token-refresh-time}")
	public Integer tokenRefreshTime;
	
	
	
}
