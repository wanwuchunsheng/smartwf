package com.smartwf.hm.config.base;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-11-29 10:29:44
 * @Description: 初始化基础数据
 */
@Component
@Slf4j
public class InitDataConfig implements CommandLineRunner{
	
	

	/**
	 * @Description: 初始化基础数据
	 */
    public void InitDataListener() {
    	try {
    		
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
    }

    @Override
    public void run(String... args) throws Exception {
        this.InitDataListener();
    }

}
