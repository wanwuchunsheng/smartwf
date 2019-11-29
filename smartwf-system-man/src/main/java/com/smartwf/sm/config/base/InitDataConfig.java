package com.smartwf.sm.config.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.smartwf.common.service.RedisService;
import com.smartwf.sm.modules.admin.service.TenantService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-11-29 10:29:44
 * @Description: 初始化基础数据
 */
@Component
@Slf4j
public class InitDataConfig implements CommandLineRunner{
	
	@Autowired
	TenantService tenantService;
	
	@Autowired
    private RedisService redisService;

	/**
	 * @Description: 初始化基础数据
	 */
    public void InitDataListener() {
    	try {
    		//租户
        	this.redisService.set("TenantAll", JSONArray.toJSONString(this.tenantService.queryTenantAll()));
        	//组织机构
        	//职务
        	//角色
        	//基础主数据
        	log.info("初始化基础数据完成{}",redisService.get("TenantAll"));
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
    }

    @Override
    public void run(String... args) throws Exception {
        this.InitDataListener();
    }

}
