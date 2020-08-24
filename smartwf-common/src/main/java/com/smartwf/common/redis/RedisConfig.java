package com.smartwf.common.redis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.smartwf.common.constant.Constants;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisStreamCommands;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;


/**
 * @author WCH
 * redis配置
 */
@SpringBootConfiguration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {


    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.index}")
    private int index;

    @Value("${spring.redis.jedis.pool.max-total}")
    private int maxTotal;

    @Value("${spring.redis.jedis.pool.max-wait-millis}")
    private long maxWaitMillis;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.min-evictable-idle-time-millis}")
    private long minEvictableIdleTimeMillis;

    @Value("${spring.redis.jedis.pool.time-between-eviction-runs-millis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${spring.redis.jedis.pool.num-tests-per-eviction-run}")
    private int numTestsPerEvictionRun;
    

    /**
     * jedis分片连接池
     *   db0
     * @return
     */
    @Bean
    public ShardedJedisPool shardedJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        List<JedisShardInfo> jedisShardInfos = new ArrayList<>();
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port, timeout);
        // 设置db
        Class<? extends JedisShardInfo> clz = jedisShardInfo.getClass();
        Field declaredField = null;
        try {
            declaredField = clz.getDeclaredField("db");
            declaredField.setAccessible(true);
            declaredField.set(jedisShardInfo, index);
        } catch (NoSuchFieldException e) {
            log.error("异常信息：NoSuchFieldException，设置db错误：{}", e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error("异常信息：IllegalAccessException，设置db错误：{}", e.getMessage(), e);
        }
        jedisShardInfo.setPassword(password);
        jedisShardInfos.add(jedisShardInfo);
        return new ShardedJedisPool(jedisPoolConfig, jedisShardInfos);
    }


    /**
     * jedis连接池
     *   消息发布订阅库db6
     *   
     * @return
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, Constants.ORDERTYPE);
        return jedisPool;
    }
    
    /**
     * redis stream
     * 
     * */
    @Bean
    public RedisCommands<String, String> redisStream() {
    	RedisURI redisUri =RedisURI.Builder.redis(host, port)
    			.withPassword(password)
    			.withDatabase(Constants.ORDERTYPE)
    			.build();
    	RedisClient client = RedisClient.create(redisUri);
    	StatefulRedisConnection<String, String> connection = client.connect();
    	RedisCommands<String, String> streamCommands = connection.sync();
    	return streamCommands;
    }
  
}
