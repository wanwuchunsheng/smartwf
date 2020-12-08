package com.smartwf.sm.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import com.smartwf.common.service.RedisService;
import com.smartwf.sm.config.redis.StreamProducer;
import com.smartwf.sm.config.util.HS256Utils;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.DictionaryService;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.service.PostService;
import com.smartwf.sm.modules.admin.service.RoleService;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.wso2.pojo.IdentityConfig;
import com.smartwf.sm.modules.wso2.service.IdentityConfigService;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * @author WCH
 * 
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class SmartwfSystemTest {
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
    private PostService postService;
	
	@Autowired
    private RoleService roleService;
	
	@Autowired
    private DictionaryService dictionaryService;
	
	@Autowired
    private IdentityConfigService identityConfigService;
	
	@Autowired
	private UserInfoService userService;
	
	@Autowired
	private StreamProducer streamProducer;
	
	@Test
	public void initBaseData() {
		try {
			/*
    		//租户初始化数据
    		List<Tenant> tenantList=this.tenantService.initTenantDatas();
    		if(tenantList!=null && tenantList.size()>0) {
    			//租户
    			this.redisService.set("initTenant", JSONUtil.toJsonStr(tenantList));
    			//组织机构
    			this.redisService.set("initOrganization",JSONUtil.toJsonStr(this.organizationService.initOrganizationDatas(tenantList)));
    			//职务
    			this.redisService.set("initPost", JSONUtil.toJsonStr(this.postService.initPostDatas(tenantList)));
    			//角色
            	this.redisService.set("initRole", JSONUtil.toJsonStr(this.roleService.initRoleDatas(tenantList)));
            	//数据字典
            	this.redisService.set("initDictionary", JSONUtil.toJsonStr(this.dictionaryService.initDictionaryDatas(tenantList)));
            	log.info("租户数据{}",redisService.get("initTenant"));
            	log.info("组织机构数据{}",redisService.get("initOrganization"));
            	log.info("职务基础数据{}",redisService.get("initPost"));
            	log.info("角色基础数据{}",redisService.get("initRole"));
            	log.info("数据字典数据{}",redisService.get("initDictionary"));
    		}
    		//wos2配置初始化数据
    		List<IdentityConfig> idtconfig=this.identityConfigService.initIdentityConfig();
    		if(idtconfig!=null && idtconfig.size()>0) {
    			for(IdentityConfig idt: idtconfig) {
            		this.redisService.set(idt.getClientKey(), JSONUtil.toJsonStr(idt));
            		log.info("wso2配置信息{}",idt.getClientKey(), JSONUtil.toJsonStr(idt));
    			}
    		}
    		//初始化排班人员信息
    		this.userService.selectUserInfoByShift();
    		
			Map<String,String> map = new HashMap<String, String>();
			for(int i=1996;i<2000;i++) {
				map.put("system", "消息是："+i);
				System.out.println(JSONUtil.toJsonStr(map));
				streamProducer.sendMsg("topic:smartwf_sys_backend2", map);
				//Thread.sleep(15000);
			}
			**/
			
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
	}

}
