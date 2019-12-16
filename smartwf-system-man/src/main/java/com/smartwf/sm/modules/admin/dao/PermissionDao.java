package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResouceVO;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 权限持久层接口
 */
@Repository
public interface PermissionDao extends Mapper<Permission> {

	/**
	 * @Description: 查询权限
	 * @result:
	 */
	List<Permission> selectPermissionByAll(@Param("bean") PermissionVO bean);
	/**
	 * @Description: 批量删除权限
	 * @result:
	 */
	void deletePermissionByIds(@Param("list") List<String> list);
	
	
	
}
