package com.smartwf.sm.modules.admin.dao;


import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.UserRole;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 用户角色持久层接口
 */
@Repository
public interface UserRoleDao extends Mapper<UserRole> {

	

    
}
