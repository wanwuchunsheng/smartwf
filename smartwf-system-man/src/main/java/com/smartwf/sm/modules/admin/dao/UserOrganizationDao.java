package com.smartwf.sm.modules.admin.dao;


import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.UserOrganization;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 用户组织架构持久层接口
 */
@Repository
public interface UserOrganizationDao extends Mapper<UserOrganization> {

	


    
}
