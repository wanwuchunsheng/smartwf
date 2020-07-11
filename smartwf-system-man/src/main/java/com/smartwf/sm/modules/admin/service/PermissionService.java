package com.smartwf.sm.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResourceVO;
import com.smartwf.sm.modules.admin.vo.UserActionVO;

/**
 * @Description: 权限业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface PermissionService {

	/**
	 * 查询权限分页
	 * @param bean
	 * @return 
	 */
	Result<?> selectPermissionByPage( PermissionVO bean);

	/**
     * 添加权限
     * @param bean
     */
	void savePermission(PermissionVO bean);
	
	/**
     *  删除权限
     * @param bean
     */
	void deletePermission(Permission bean);
	/**
	 * 资源与用户操作查询
	 * @param bean
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	Result<?> selectResourceUserActByPage(PermissionVO bean);
	/**
	 * 用户操作
	 * @param bean
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	Result<?> selectUserActAll(UserActionVO bean);
	/**
	 * 查询资源子系统
	 * @param bean
	 * @return
	 */
	Result<?> selectResourceByPid(ResourceVO bean);
	

}
