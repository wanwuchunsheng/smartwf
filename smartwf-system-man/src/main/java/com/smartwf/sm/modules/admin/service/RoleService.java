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
	 * 查询角色分页
	 * @param page
	 * @param bean
	 * @return
	 */
	Result<?> selectRoleByPage(Page<Role> page, RoleVO bean);

	/**
     * 主键查询角色
     * @param bean
     * @return
     */
	Result<?> selectRoleById(Role bean);

	/**
     * 添加角色
     * @param bean
     * @return
     */
	Result<?> saveRole(Role bean);
	
	/**
     *  修改角色
     *  @param bean
     * @return
     */
	Result<?>  updateRole(Role bean);

	/**
     * 删除角色
     * @param bean
     */
	void deleteRole(RoleVO bean);
	
	/**
     * 初始化角色信息
     * @param list
     * @return
     */
	Map<Integer,List<Role>> initRoleDatas(List<Tenant> list);
	
	

}
