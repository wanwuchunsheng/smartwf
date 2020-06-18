package com.smartwf.sm.modules.wso2.service;

import java.util.Map;

import com.smartwf.sm.modules.admin.pojo.Role;

public interface Wso2RoleService {

	/**
	 * 说明：添加角色
	 * 
	 * */
	Map<String, Object> addRole(Role bean);
	/**
	 * 说明：删除角色
	 * 
	 * */
	void deleteRole(Role rl);
	/**
	 * 说明：修改角色
	 * 
	 * */
	Map<String,Object> updateRole(Role bean);

	
}
