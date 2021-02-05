package com.smartwf.hm.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.smartwf.common.service.RedisService;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.service.SecurityIncidentsService;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;

/**
 * @author WCH
 * 
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class SmartwfHealthTest {
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SecurityIncidentsService securityIncidentsService;
	
	@Test
	public void initBaseData() {
		try {
    		this.alarmInboxService.selectFaultInformationByAll();
    		this.defectService.initDefectAll();
    		//初始化安全生产天数
    		this.securityIncidentsService.initSecurityIncidentsService();
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
	}
	


}
