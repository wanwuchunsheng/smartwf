package com.smartwf.sm.modules.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.vo.TenantVO;

/**
 * @Description: 租户业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface TenantService {

	/**
	 * @Description: 查询租户分页
	 * @result: 
	 */
	Result<?> selectTenantByPage(Page<Tenant> page, TenantVO bean);

	/**
     * @Description: 主键查询租户
     * @return
     */
	Result<?> selectTenantById(Tenant bean);

	/**
     * @Description： 添加租户
     * @return
     */
	void saveTenant(Tenant bean);
	
	/**
     * @Description： 修改租户
     * @return
     */
	void updateTenant(Tenant bean);

	/**
     * @Description： 删除租户
     * @return
     */
	void deleteTenant(TenantVO bean);
	
	/**
     * @Description： 初始化租户
     * @return
     */
	List<Tenant> queryTenantAll();
	

}
