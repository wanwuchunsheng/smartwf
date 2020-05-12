package com.smartwf.hm.config.redis;

import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class RestConfig {
	
	@Bean
	//@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
     * 切换redis数据库
     * @param redisTemplate springboot封装的redis对象
     * @param index  数据库下标
     */
    public static void setIndex(RedisTemplate redisTemplate, int index){
        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        if(lettuceConnectionFactory != null){
            lettuceConnectionFactory.setDatabase(index);
            redisTemplate.setConnectionFactory(lettuceConnectionFactory);
            lettuceConnectionFactory.resetConnection();
        }
    }

}
