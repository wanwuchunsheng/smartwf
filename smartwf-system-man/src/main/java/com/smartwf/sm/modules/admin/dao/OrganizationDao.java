package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 组织架构持久层接口
 */
@Repository
public interface OrganizationDao extends Mapper<Organization> {

	/**
	 * @Description: 批量删除组织架构
	 * @result:
	 */
	void deleteOrganizationByIds(@Param("list") List<String> list);

	/**
	 * @Description: 初始化组织架构
	 * @result:
	 */
	List<Organization> queryOrganizationAll();

	/**
	 * @Deprecated 删除用户组织结构
	 * 
	 * */
	void deleteUserOrgById(@Param("bean") OrganizationVO bean);


    
}
