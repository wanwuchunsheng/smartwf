package com.smartwf.sm.config.redis;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author WCH
 * 
 * */
@SpringBootConfiguration
public class RestConfig {
	
	/**
	 * @LoadBalanced
	 * 
	 * */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
