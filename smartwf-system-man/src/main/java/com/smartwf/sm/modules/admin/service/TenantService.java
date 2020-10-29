package com.smartwf.sm.modules.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.TenantVO;

/**
 * @Description: 租户业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface TenantService {

	/**
	 * 查询租户分页
	 * @param page
	 * @param bean
	 * @return
	 */
	Result<?> selectTenantByPage(Page<Tenant> page, TenantVO bean);

	/**
     * 主键查询租户
     * @param bean
     * @return
     */
	Result<?> selectTenantById(Tenant bean);

	/**
     * 添加租户
     * @param bean
     */
	void saveTenant(Tenant bean);
	
	/**
     *  修改租户
     * @param bean
     */
	void updateTenant(Tenant bean);

	/**
     *  删除租户
     * @param bean
     */
	void deleteTenant(TenantVO bean);

	/**
     * 初始化租户信息
     * @return
     */
	List<Tenant> initTenantDatas();
	/**
     * 查询用户是否属于租户域用户
     * @return
     */
	boolean selectTenantByParma(UserInfo uf);
	
	
	

}
