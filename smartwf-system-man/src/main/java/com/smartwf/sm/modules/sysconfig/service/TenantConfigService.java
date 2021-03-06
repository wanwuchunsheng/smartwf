package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;

/**
 * @Description: 多租户配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface TenantConfigService {

	/**
	 * 查询多租户配置分页
	 * @param page
	 * @param bean
	 * @return
	 */
	Result<?> selectTenantConfigByPage(Page<TenantConfig> page, TenantConfigVO bean);

	/**
     * 主键查询多租户配置
     * @param bean
     * @return
     */
	Result<?> selectTenantConfigById(TenantConfig bean);

	/**
     * 添加多租户配置
     * @param bean
     * 
     */
	void saveTenantConfig(TenantConfig bean);
	
	/**
     *  修改多租户配置
     *  @param bean
     * 
     */
	void updateTenantConfig(TenantConfig bean);

	/**
     *  删除多租户配置
     * @param bean
     */
	void deleteTenantConfig(TenantConfigVO bean);

	/**
	 * 说明： 租户样式上传（logo、样式json）
	 * @param tenantId
	 * @param remark  样式json
	 * @return
	 * 
	 * */
	Result<?> saveIndexStyleById(TenantConfig bean);

	

	
	
	
	

}
