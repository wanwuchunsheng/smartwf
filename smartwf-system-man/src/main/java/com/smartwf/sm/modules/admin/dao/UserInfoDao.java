package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 用户资源持久层接口
 */
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {

	/**
	 * @param page 
	 * @Description:查询用户资源分页
	 * @result:
	 */
	List<UserInfo> selectUserInfoByPage(@Param("bean") UserInfo bean, Page<UserInfo> page);

	/**
	 * @Description: 批量删除
	 */
	void deleteUserInfoByIds(@Param("list") List<String> list);

	/**
	 * @Description: 单个删除
	 */
	void deleteUserInfoById(@Param("bean") UserInfoVO bean);

	/**
	 * @Description: 单个删除用户组织架构
	 */
	void deleteUserOrgById(@Param("bean") UserInfoVO bean);

	/**
	 * @Description: 单个删除用户职务
	 */
	void deleteUserPostById(@Param("bean") UserInfoVO bean);

	/**
	 * @Description: 单个删除用户角色
	 */
	void deleteUserRoleById(@Param("bean") UserInfoVO bean);
	/**
	 * @Description: 主键查询用户资料
	 */
	UserInfo selectUserInfoById(@Param("bean") UserInfo bean);


    
}
