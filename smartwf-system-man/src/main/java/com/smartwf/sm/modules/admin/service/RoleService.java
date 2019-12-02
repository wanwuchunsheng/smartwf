package com.smartwf.sm.modules.admin.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.vo.RoleVO;

/**
 * @Description: 角色业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface RoleService {

	/**
	 * @Description: 查询角色分页
	 * @result: 
	 */
	Result<?> selectRoleByPage(Page<Object> page, RoleVO bean);

	/**
     * @Description: 主键查询角色
     * @return
     */
	Result<?> selectRoleById(Role bean);

	/**
     * @Description： 添加角色
     * @return
     */
	void saveRole(Role bean);
	
	/**
     * @Description： 修改角色
     * @return
     */
	void updateRole(Role bean);

	/**
     * @Description： 删除角色
     * @return
     */
	void deleteRole(RoleVO bean);
	
	/**
     * @Description： 初始化角色
     * @return
     */
	List<Role> queryRoleAll();
	

}
