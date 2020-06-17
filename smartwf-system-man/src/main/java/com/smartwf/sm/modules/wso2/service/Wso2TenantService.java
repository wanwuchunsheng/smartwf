package com.smartwf.sm.modules.wso2.service;

import java.util.Map;

import com.smartwf.sm.modules.wso2.pojo.Wso2Tenant;

public interface Wso2TenantService {

	Map<String, Object> addTenant(Wso2Tenant bean);

	/**
     * @Description：模拟wso2查询租户
     * @param code,session_state和state
     * @return
     */
	Map<String, Object> selectTenant(Wso2Tenant bean);
	/**
     * @Description：模拟wso2删除租户
     * @param code,session_state和state
     * @return
     */
	Map<String, Object> deleteTenant(Wso2Tenant bean);
	 /**
     * @Description：模拟wso2租户修改
     * @param code,session_state和state
     * @return
     */
	Map<String, Object> updateTenant(Wso2Tenant bean);
	/**
     * @Description：模拟wso2租户禁用
     * @param code,session_state和state
     * @return
     */
	Map<String, Object> deactivateTenant(Wso2Tenant bean);

	

}
