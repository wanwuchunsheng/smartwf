package com.smartwf.hm.config.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:消费者
 * @Date: 2018/8/9 14:51
 */

@Component
@Slf4j
public class Consumer implements CommandLineRunner {

    /**
     * 消费者
     */
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    /**
     * NameServer 地址
     */
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    /**
     * 初始化RocketMq的监听信息，渠道信息
     */
    public void messageListener() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            // 订阅PushTopic下Tag为push的消息,都订阅消息
            consumer.subscribe(topic, "*");
            // 程序第一次启动从消息队列头获取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //可以修改每次消费消息的数量，默认设置是每次消费八条
            consumer.setConsumeMessageBatchMaxSize(8);
            consumer.setConsumeThreadMin(8);//设置最小线程
            consumer.setConsumeThreadMax(16);//设置最大线程
            //在此监听中消费信息，并返回消费的状态信息
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                // 会把不同的消息分别放置到不同的队列中
                for (Message msg : msgs) {
                    log.info("进入消息队列  " + msg.getTopic() + "    " + msg.getTags() + "  " + new String(msg.getBody()));
                    if (topic.equals(msg.getTopic())) {
                        switch (msg.getTags()) {
                            case "IOT_SMARTWF_HM_TAG":  
                               log.info("进入IOT消费 ++++++++++++++++++++++++++++++++++++++++++++++++++", new String(msg.getBody()));
                                break;
                            default:
                               
                                break;
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            log.info("消费者启动了------------》");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.messageListener();
    }
}