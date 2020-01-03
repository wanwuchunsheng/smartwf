package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResourceVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 权限持久层接口
 */
@Repository
public interface PermissionDao extends BaseMapper<Permission> {

	/**
	 * @Description: 查询权限
	 * @result:
	 */
	List<ResourceVO> selectPermissionByAll(@Param("bean") PermissionVO bean);
	/**
	 * @Description: 批量删除权限
	 * @result:
	 */
	void deletePermissionByIds(@Param("list") List<String> list);
	/**
	 * @Description: 查询全部可用资源
	 * @result:
	 */
	List<ResourceVO> selectResourceByAll(@Param("bean") PermissionVO bean);
	
	
}
