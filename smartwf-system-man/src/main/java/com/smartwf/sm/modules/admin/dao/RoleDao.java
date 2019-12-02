package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.vo.RoleVO;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 角色持久层接口
 */
@Repository
public interface RoleDao extends Mapper<Role> {

	/**
	 * @Description: 批量删除角色
	 * @result:
	 */
	void deleteRoleByIds(@Param("list") List<String> list);

	/**
	 * @Description: 初始化角色
	 * @result:
	 */
	List<Role> queryRoleAll();

	/**
	 * @Deprecated 删除用户角色
	 * 
	 * */
	void deleteUserRoleById(@Param("bean") RoleVO bean);
	/**
	 * @Deprecated 删除角色权限
	 * 
	 * */
	void deleteRolePermissionById(@Param("bean") RoleVO bean);


    
}
