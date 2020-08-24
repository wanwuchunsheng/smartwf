package com.smartwf.hm.config.redis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartwf.common.service.RedisService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:生产者
 * @Date: 2020-8-19 10:22:11
 */

@Component
@Slf4j
public class StreamProducer {
	
	@Autowired
	private RedisService redisService;
	
	
	/**
	 * 说明：消息发布
	 * @author WCH
	 * @param topic
	 * @param map
	 * @Date 2020-8-18 16:58:10
	 * 
	 * */
    public void sendMsg(String topic, Map<String,String> map) {
    	 String res= redisService.getRedisStream().xadd(topic, map);
         log.info("频道发布消息{} - {} - 返回：{}", topic,map,res);
    }

}