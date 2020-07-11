package com.smartwf.sm.modules.wso2.service;

import java.util.List;

import com.smartwf.sm.modules.wso2.pojo.IdentityConfig;
/**
 * 
 * @author WCH
 * 
 * */
public interface IdentityConfigService {
	/**
	 * 
	 * 查询wso2 初始化数据
	 * @return
	 * 
	 * */
	List<IdentityConfig> initIdentityConfig();

}
