package com.smartwf.sm.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.vo.PermissionVO;

/**
 * @Description: 权限业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface PermissionService {

	/**
	 * @Description: 查询权限分页
	 * @result: 
	 */
	Result<?> selectPermissionByPage(Page<Permission> page, PermissionVO bean);

	/**
     * @Description： 添加权限
     * @return
     */
	void savePermission(Permission bean);
	
	/**
     * @Description： 删除权限
     * @return
     */
	void deletePermission(Permission bean);
	/**
	 * @Description: 资源与用户操作查询
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	Result<?> selectResouceUserActByPage(PermissionVO bean);
	

}
