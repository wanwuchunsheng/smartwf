package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.Tenant;

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


    
}
