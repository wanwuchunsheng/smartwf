package com.smartwf.sm.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.smartwf.common.redis.RedisConfig;

import java.io.Serializable;

/**
 * 
 * @ClassName MyRedisConfig.java
 * @Description toDo
 * @author 黄毅
 * @date 2020/04/01
 * @version V1.0
 */
@Component
public class RedisPoolConfig   {
	
	
    /**
     * 初始化Redis
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory){
    	connectionFactory.setDatabase(2);
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<> ();
        redisTemplate.setConnectionFactory(connectionFactory);
        
        return  redisTemplate;
    }

}
