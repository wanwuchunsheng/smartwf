package com.smartwf.sm.config.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.common.utils.MD5Utils;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.DictionaryService;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.service.PostService;
import com.smartwf.sm.modules.admin.service.RoleService;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.wso2.pojo.IdentityConfig;
import com.smartwf.sm.modules.wso2.service.IdentityConfigService;
import com.smartwf.sm.modules.wso2.service.Wso2UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-11-29 10:29:44
 * 初始化基础数据
 * @author WCH
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
	
	@Autowired
    private DictionaryService dictionaryService;
	
	@Autowired
    private IdentityConfigService identityConfigService;
    

	/**
	 * @Description: 初始化基础数据
	 */
    public void initDataListener() {
    	try {
    		//租户初始化数据
    		List<Tenant> tenantList=this.tenantService.initTenantDatas();
    		if(tenantList!=null && tenantList.size()>0) {
    			this.redisService.set("initTenant", JSON.toJSONString(tenantList));
            	//组织机构
            	this.redisService.set("initOrganization",JSON.toJSONString(this.organizationService.initOrganizationDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
            	//职务
            	this.redisService.set("initPost", JSON.toJSONString(this.postService.initPostDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
            	//角色
            	this.redisService.set("initRole", JSON.toJSONString(this.roleService.initRoleDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
            	//数据字典
            	this.redisService.set("initDictionary", JSON.toJSONString(this.dictionaryService.initDictionaryDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
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
            		this.redisService.set(idt.getClientKey(), JsonUtil.objectToJson(idt));
            		log.info("wso2配置信息{}",idt.getClientKey(), JsonUtil.objectToJson(idt));
    			}
    		}
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
    }

    @Override
    public void run(String... args) throws Exception {
        this.initDataListener();
    }

}
