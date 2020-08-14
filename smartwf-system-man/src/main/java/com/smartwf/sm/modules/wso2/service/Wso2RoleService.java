package com.smartwf.sm.modules.wso2.service;

import java.util.List;
import java.util.Map;

import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
/**
 * 
 * @author WCH
 * 
 * */
public interface Wso2RoleService {

	/**
	 * 说明：添加角色
	 * @param bean
	 * @return
	 * */
	Map<String, Object> addRole(Role bean);
	/**
	 * 说明：删除角色
	 * @param rl
	 * 
	 * */
	void deleteRole(Role rl);
	/**
	 * 说明：修改角色
	 * @param bean
	 * @return
	 * 
	 * */
	Map<String,Object> updateRole(Role bean);
	/**
     * 模拟wso2用户角色绑定
     * @param code,session_state和state
     *  @param bean
     *  @param ur
	 * @return
     */
	Map<String,Object> addRoleOrUser(Role ur, UserInfoVO bean);
	/**
     * 模拟wso2用户角色解绑
     * @param code,session_state和state
     * @param bean
     * @param listRole
     * @return
     */
	Map<String, Object> updateRoleOrUser(List<Role> listRole, UserInfoVO bean);
	
	
}
