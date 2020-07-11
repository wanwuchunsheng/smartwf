package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.TreeOrganization;
import com.smartwf.common.pojo.TreePost;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.UserPost;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 组织架构持久层接口
 * @author WCH
 */
@Repository
public interface OrganizationDao extends BaseMapper<Organization> {

	/**
	 * 查询所有组织架构
	 * @param bean
	 * @return
	 */
	List<OrganizationVO> selectOrganizationByAll(@Param("bean") OrganizationVO bean);

	/**
	 *  删除用户组织结构
	 * @param bean
	 * 
	 * */
	void deleteUserOrgById(@Param("bean") OrganizationVO bean);
	/**
	 * 删除职务表
	 * @param bean
	 */
	void deletePostByOrgId(@Param("bean") OrganizationVO bean);
	/**
	 *  批量删除用户职务表
	 *  @param list
	 */
	void deleteUserPostByOrgIds(@Param("list") List<Integer> list);
	/**
	 * 通过组织架构id，查询用户组织架构集合
	 * @param bean
	 * @return
	 * 
	 */
	List<UserPost> queryUserPostByOrgId(@Param("bean") OrganizationVO bean);
	/**
	 * 通过用户id，查询用户组织架构集合
	 * @param userInfo
	 * @return
	 * 
	 */
	List<TreeOrganization> selectOrganizationByUserId(@Param("bean") User userInfo);
	
	

    
}
