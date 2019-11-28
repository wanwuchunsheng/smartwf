package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 用户资源持久层接口
 */
@Repository
public interface UserInfoDao extends Mapper<UserInfo> {

	/**
	 * @Description:查询用户资源分页
	 * @result:
	 */
	List<UserInfo> selectUserInfoByPage(@Param("bean") UserInfo bean, Page<Object> objectPage);

	/**
	 * @Description: 批量删除
	 * @result:
	 */
	void deleteUserInfoByIds(@Param("list") List<String> list);

	/**
	 * @Description: 单个删除
	 * @result:
	 */
	void deleteUserInfoById(@Param("bean") UserInfoVO bean);


    
}
