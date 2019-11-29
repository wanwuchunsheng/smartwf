package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.vo.TenantVO;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 租户持久层接口
 */
@Repository
public interface TenantDao extends Mapper<Tenant> {

	/**
	 * @Description: 批量删除租户
	 * @result:
	 */
	void deleteTenantByIds(@Param("list") List<String> list);

	/**
	 * @Description: 初始化租户
	 * @result:
	 */
	List<Tenant> queryTenantAll();

	/**
	 * @Description: 级联删除
	 * 
	 * */
	void deleteOrgByTeandId(@Param("bean") TenantVO bean);

	void deleteUserOrgByTeandId(@Param("bean") TenantVO bean);

	void deletePostByTeandId(@Param("bean") TenantVO bean);

	void deleteUserPostByTeandId(@Param("bean") TenantVO bean);

	void deleteRoleByTeandId(@Param("bean") TenantVO bean);

	void deleteUserRoleByTeandId(@Param("bean") TenantVO bean);

	void deletePermissionByTeandId(@Param("bean") TenantVO bean);

	/**
	 * @Description: 批量级联删除
	 * 
	 * */
	void deleteOrgByTeandIds(@Param("list") List<String> list);

	void deleteUserOrgByTeandIds(@Param("list") List<String> list);

	void deletePostByTeandIds(@Param("list") List<String> list);

	void deleteUserPostByTeandIds(@Param("list") List<String> list);

	void deleteRoleByTeandIds(@Param("list") List<String> list);

	void deleteUserRoleByTeandIds(@Param("list") List<String> list);

	void deletePermissionByTeandIds(@Param("list") List<String> list);

    
}
