package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 组织架构持久层接口
 */
@Repository
public interface OrganizationDao extends BaseMapper<Organization> {

	/**
	 * @Description: 批量删除组织架构
	 * @result:
	 */
	void deleteOrganizationByIds(@Param("list") List<String> list);
	/**
	 * @Description: 批量删除职务表
	 */
	void deletePostByOrgIds(@Param("list") List<String> list);
	/**
	 * @Description: 批量删除用户职务表
	 */
	void deleteUserPostByOrgIds(@Param("list") List<String> list);

	/**
	 * @Description: 初始化组织架构
	 * @result:
	 */
	List<Organization> queryOrganizationAll();
	/**
	 * @Description: 查询所有组织架构
	 * @return
	 */
	List<OrganizationVO> selectOrganizationByAll(@Param("bean") OrganizationVO bean);

	/**
	 * @Deprecated 删除用户组织结构
	 * 
	 * */
	void deleteUserOrgById(@Param("bean") OrganizationVO bean);
	/**
	 * @Description: 删除职务表
	 */
	void deletePostByOrgId(@Param("bean") OrganizationVO bean);
	/**
	 * @Description: 删除用户职务表
	 */
	void deleteUserPostByOrgId(@Param("bean") OrganizationVO bean);
	
	
	
	
	


    
}
