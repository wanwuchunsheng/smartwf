package com.smartwf.hm.config.redis;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cn.hutool.json.JSONUtil;
import io.lettuce.core.Consumer;
import io.lettuce.core.RedisBusyException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XGroupCreateArgs;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:消费者
 * @Date: 2020-8-18 17:06:23
 */

@Component
@Slf4j
public class StreamConsumer implements CommandLineRunner {
	
	@Autowired
	private RedisClient redisClient;

	//主题，不同的子系统主题不要一样遵循{topic:子系统名称}
	public final static String STREAMS_KEY = "topic:smartwf_health";
	//组名称
	public final static String GROUP_NAME="application";
	//消费者
	public final static String CONSUMER_NAME="consumer";
	
	/**
     * 初始化监听信息，渠道信息
     *	
	 * 广播模式，阻塞读取
	 *  redisStream.xread(XReadArgs.Builder.count(0).block(Duration.ofSeconds(0)),StreamOffset.from("topic:smartwf_sys_backend", "$"));	
	 *  redisStream.xack(STREAMS_KEY, "application_1",  message.getId());
	 * 资源挣抢模式，阻塞读取
	 * @author WCH
	 * @date 2020-8-24 13:11:03
	 * 
     */
    public void messageListener() {
    	//获取源
    	StatefulRedisConnection<String, String> connection=redisClient.connect();
    	RedisCommands<String, String> redisStream = connection.sync();
    	try {
    		//创建组，重复创建组跳过异常
    		try {
    			redisStream.xgroupCreate( XReadArgs.StreamOffset.from(STREAMS_KEY, "0-0"), GROUP_NAME, XGroupCreateArgs.Builder.mkstream(true) );
            } catch (RedisBusyException redisBusyException) {
               log.warn( String.format("\t Group '%s' already exists", "application_1"));
            }
    		//阻塞读取，读取完成继续阻塞读取
            while(true) {
            	// 阻塞读取分组{无消息等待，有消息向下执行}
                @SuppressWarnings("unchecked")
				List<StreamMessage<String, String>> messages = redisStream.xreadgroup(Consumer.from(GROUP_NAME, CONSUMER_NAME),XReadArgs.Builder.count(1).block(Duration.ofSeconds(0)),XReadArgs.StreamOffset.lastConsumed(STREAMS_KEY));
                //判断消息是否为空{进入相关业务流程}
                if (!messages.isEmpty()) {
                    for (StreamMessage<String, String> message : messages) {
                        log.info(JSONUtil.toJsonStr(message));
                        //获取消息
            			Map<String,String> map=message.getBody();
            			//获取map键
            			String type=map.entrySet().iterator().next().getKey();
            			//进入相应的业务处理
            			switch (type) {
    					case "1":
    						log.info("msg - {}", map.get(type));
    						break;
    					case "2":
    						log.info("msg - {}", map.get(type));
    						break;
    					default:
    						break;
    				};
                    //处理完成后，删除MQ消息记录
                    redisStream.xdel(STREAMS_KEY, message.getId());
                    }
                }
            }
		} catch (Exception e) {
			log.error("消息消费发生异常{}{}",e.getMessage(),e);
			//异常关闭源
			connection.close();
			messageListener();
		}
    }
  
   
    @Override
    public void run(String... args) throws Exception {
    	//开启新线程监听MQ端口，避免初始化被阻塞
    	new Thread(new Runnable() {
			@Override
			public void run() {
				messageListener();
			}
		}).start();
    }
    
}