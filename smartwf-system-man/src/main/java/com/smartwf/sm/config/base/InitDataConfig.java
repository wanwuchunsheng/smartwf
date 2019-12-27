package com.smartwf.sm.config.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.smartwf.common.service.RedisService;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.service.PostService;
import com.smartwf.sm.modules.admin.service.RoleService;
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
	
	@Autowired
    private RoleService roleService;
    

	/**
	 * @Description: 初始化基础数据
	 */
    public void InitDataListener() {
    	try {
    		//租户
    		List<Tenant> tenantList=this.tenantService.InitTenantDatas();
    		if(tenantList!=null && tenantList.size()>0) {
    			this.redisService.set("initTenant", JSON.toJSONString(tenantList));
            	//组织机构
            	this.redisService.set("initOrganization", JSON.toJSONString(this.organizationService.initOrganizationDatas(tenantList)));
            	//职务
            	this.redisService.set("initPost", JSON.toJSONString(this.postService.initPostDatas(tenantList)));
            	//角色
            	this.redisService.set("initRole", JSON.toJSONString(this.roleService.initRoleDatas(tenantList)));
            	//数据字典
            	//this.redisService.set("initDict", JSON.toJSONString(this.roleService.initDictDatas(tenantList)));
            	
            	log.info("租户数据{}",redisService.get("initTenant"));
            	log.info("组织机构数据{}",redisService.get("initOrganization"));
            	log.info("职务基础数据{}",redisService.get("initPost"));
            	log.info("角色基础数据{}",redisService.get("initRole"));
    		}else {
    			log.info(">>>>>>>>租户信息为空，初始化信息失败！");
    		}
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
    }

    @Override
    public void run(String... args) throws Exception {
        this.InitDataListener();
    }

}
