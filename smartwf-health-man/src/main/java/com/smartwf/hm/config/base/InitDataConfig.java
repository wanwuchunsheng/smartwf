package com.smartwf.hm.config.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-11-29 10:29:44
 * @Description: 初始化基础数据
 */
@Component
@Slf4j
public class InitDataConfig implements CommandLineRunner{
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	
	/**
	 * @Description: 初始化未结束的故障
	 *    保证redis最新的数据和mysql一致
	 */
    public void InitDataListener() {
    	try {
    		this.alarmInboxService.selectFaultInformationByAll();
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
    }

    @Override
    public void run(String... args) throws Exception {
        this.InitDataListener();
    }

}
