package com.smartwf.sm.modules.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
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
	Result<?> selectRoleByPage(Page<Role> page, RoleVO bean);

	/**
     * @Description: 主键查询角色
     * @return
     */
	Result<?> selectRoleById(Role bean);

	/**
     * @Description： 添加角色
     * @return
     */
	Result<?> saveRole(Role bean);
	
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
     * @Description：初始化角色信息
     * @return
     */
	Map<Integer,List<Role>> initRoleDatas(List<Tenant> list);
	
	

}
