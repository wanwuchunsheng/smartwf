package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.common.pojo.TreeRole;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.vo.RoleVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 角色持久层接口
 */
@Repository
public interface RoleDao extends BaseMapper<Role> {

	/**
	 * @Description: 批量删除角色
	 * @result:
	 */
	void deleteRoleByIds(@Param("list") List<String> list);

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
	/**
	 * @Deprecated 通过用户ID查询角色
	 * 
	 * */
	List<TreeRole> selectRoleByUserId(@Param("bean") User userInfo);
	/**
	 * @Deprecated 查询所有角色集合对象
	 * 
	 * */
	List<Role> selectRoleByIds(@Param("list") List<String> list);


    
}
