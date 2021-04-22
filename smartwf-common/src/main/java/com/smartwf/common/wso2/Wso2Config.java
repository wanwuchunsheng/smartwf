package com.smartwf.common.wso2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * wso2配置
 * @author WCH
 */
@Component
@RefreshScope 
public class Wso2Config {
	
	/**
	 * user
	 * 
	 * */
	@Value("${spring.wso2.user.user-server-uri}")
	public String userServerUri;
	
	/**
	 * token
	 * 
	 * */
	@Value("${spring.wso2.token.token-refresh-time}")
	public Integer tokenRefreshTime;
	
	
	
}
