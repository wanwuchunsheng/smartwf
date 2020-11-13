package com.smartwf.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.StreamEntryID;

/**
 * @author WCH
 * 通用的Redis工具类
 *
 */
@Service
public class RedisService {

    /**
     * 注入分片的连接池
     * */ 
    @Autowired
    private ShardedJedisPool pool;
    
    @Autowired
    private JedisPool jedisPool;
    

    /**
     * 执行set命令，添加字符串格式数据
     *
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value) {
        return execute(new Function<String>() {
            @Override
            public String commond(ShardedJedis jedis) {
                return jedis.set(key, value);
            }
        });
    }


    /**
     * 执行set命令，添加字符串格式数据，并且设置存活时间,单位是秒
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value, final Integer seconds) {
        return execute(new Function<String>() {
            @Override
            public String commond(ShardedJedis jedis) {
                String str = jedis.set(key, value);
                jedis.expire(key, seconds);
                return str;
            }
        });
    }


    /**
     * 执行get命令，查询字符串格式数据
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return execute(new Function<String>() {
            @Override
            public String commond(ShardedJedis jedis) {
                return jedis.get(key);
            }
        });
    }


    /**
     * 执行hset命令，添加字符串格式数据，并且设置存活时间,单位是秒
     * @param key
     * @param value
     * @return
     */
    public Long hset(final String key, final String field, final String value, final Integer seconds) {
        return execute(new Function<Long>() {
            @Override
            public Long commond(ShardedJedis jedis) {
                Long hset = jedis.hset(key, field, value);
                jedis.expire(key, seconds);
                return hset;
            }
        });
    }


    /**
     * 执行hget命令，查询字符串格式数据
     *
     * @param key
     * @return
     */
    public String hget(final String key, final String field) {
        return execute(new Function<String>() {
            @Override
            public String commond(ShardedJedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }


    /**
     * 执行hget命令，查询字符串格式数据
     *
     * @param key
     * @return
     */
    public Set<String> hget(final String key) {
        return execute(new Function<Set<String>>() {
            @Override
            public Set<String> commond(ShardedJedis jedis) {
                return jedis.hkeys(key);
            }
        });
    }


    /**
     * 执行del命令，删除数据
     * @param key
     * @return
     */
    public Long del(final String key){
        return execute(new Function<Long>() {
            @Override
            public Long commond(ShardedJedis jedis) {
                return jedis.del(key);
            }
        });
    }


    /**
     * 执行expire命令，设置存活时间
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key, final Integer seconds){
        return execute(new Function<Long>() {
            @Override
            public Long commond(ShardedJedis jedis) {
                return jedis.expire(key, seconds);
            }
        });
    }
    
    /**
     * 订阅/消费
     *
     * @param timeout 0永久阻塞   1000秒后断开
     * @param topic 主题 
     * @return
     */
    public List<String> brpop(final int time, final String topic) {
        return execute(new Function<List<String>>() {
            @Override
            public List<String> commond(ShardedJedis jedis) {
                return jedis.brpop(time,topic);
            }
        });
    }
    
    /**
     * 发布/生产
     *   
     * @param topic 主题
     * @param message 消息
     * @return 
     * @return
     */
    public Object lpush(final String topic, final String message) {
        return execute(new Function<Object>() {
            @Override
            public Object commond(ShardedJedis jedis) {
                return jedis.lpush(topic, message);
            }
        });
    }
    
    
    /**
     * 发布/生产
     *   创建消费组
     * 参数说明:
	 *	第一个参数:消息队列名称.
	 *	第二个参数:分组名称
	 *	第三个参数:消息起始ID.
	 *	第四个参数:暂不知.
     * @return
     */
    public String xgroupCreate(final String key,final String consumer,final StreamEntryID id,final boolean makeStream) {
        return execute(new Function<String>() {
            @Override
            public String commond(ShardedJedis jedis) {
                return jedis.xgroupCreate(key, consumer, id, makeStream);
            }
        });
    }
    
    
    /**
     * 发布/生产   xadd模式
     * 参数说明:
	 *	第一个参数:消息队列名称.
	 *	第二个参数:分组名称
	 *	第三个参数:消息起始ID.
	 *	第四个参数:暂不知.
     * @return
     */
    public StreamEntryID xadd(final String key,final StreamEntryID id,final Map<String,String> map) {
        return execute(new Function<StreamEntryID>() {
            @Override
            public StreamEntryID commond(ShardedJedis jedis) {
                return jedis.xadd(key, id, map);
            }
        });
    }
    
    /**
     * 获取jedis池数据源
     * 
     * */
    public Jedis getJedisPool() {
    	Jedis jedis = null;
        try {
            // 从池中获取连接
        	jedis = jedisPool.getResource();
            // 添加数据
            return jedis;
        }finally {
            if (null != jedis) {
            	jedis.close();
            }
        }
    }
    

    /**
     * 抽取公用部分代码
     * @param func
     * @param <R>
     * @return
     */
    public <R> R execute(Function<R> func) {
        ShardedJedis shardedJedis = null;
        try {
            // 从池中获取连接
            shardedJedis = pool.getResource();
            // 添加数据
            return func.commond(shardedJedis);
        } finally {
            if (null != shardedJedis) {
                shardedJedis.close();
            }
        }
    }


    /**
     * 用接口封装要执行的语句，把接口的实现类对象作为参数传递，等同于传递一段代码
     * @param <R>
     */
    private interface Function<R> {
        /** 
         * 包含要执行语句的方法，因为返回值未知，所以用泛型
         * @param jedis
         * @return R
         *  */ 
        R commond(ShardedJedis jedis);
    }
    
}
