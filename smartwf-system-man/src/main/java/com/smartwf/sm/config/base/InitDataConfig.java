package com.smartwf.sm.config.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.MD5Utils;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.service.PostService;
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
    private RedisService redisService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
    private PostService postService;

	/**
	 * @Description: 初始化基础数据
	 */
    public void InitDataListener() {
    	try {
    		//租户
        	this.redisService.set("tenantAll", JSONArray.toJSONString(this.tenantService.queryTenantAll()));
        	//组织机构
        	this.redisService.set("organizationAll", JSONArray.toJSONString(this.organizationService.queryOrganizationAll()));
        	//职务
        	this.redisService.set("postAll", JSONArray.toJSONString(this.postService.queryPostAll()));
        	//角色
        	//基础主数据
        	log.info("租户数据{}",redisService.get("tenantAll"));
        	log.info("组织机构数据{}",redisService.get("organizationAll"));
        	log.info("职务基础数据{}",redisService.get("postAll"));
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
    }

    @Override
    public void run(String... args) throws Exception {
        this.InitDataListener();
    }

}
