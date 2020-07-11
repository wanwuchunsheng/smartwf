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
 * @author WCH
 */
@Repository
public interface RoleDao extends BaseMapper<Role> {

	/**
	 * 批量删除角色
	 * @param list
	 * @result:
	 */
	void deleteRoleByIds(@Param("list") List<String> list);

	/**
	 * 删除用户角色
	 * @param bean
	 * 
	 * */
	void deleteUserRoleById(@Param("bean") RoleVO bean);
	/**
	 * 删除角色权限
	 * @param bean
	 * 
	 * */
	void deleteRolePermissionById(@Param("bean") RoleVO bean);
	/**
	 * 通过用户ID查询角色
	 * @param userInfo
	 * @return
	 * */
	List<TreeRole> selectRoleByUserId(@Param("bean") User userInfo);
	/**
	 * 查询所有角色集合对象
	 * @param list
	 * @return
	 * */
	List<Role> selectRoleByIds(@Param("list") List<String> list);


    
}
