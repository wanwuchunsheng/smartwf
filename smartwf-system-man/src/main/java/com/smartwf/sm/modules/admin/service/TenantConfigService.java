package com.smartwf.sm.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.TenantConfig;
import com.smartwf.sm.modules.admin.vo.TenantConfigVO;

/**
 * @Description: 多租户配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface TenantConfigService {

	/**
	 * @Description: 查询多租户配置分页
	 * @result: 
	 */
	Result<?> selectTenantConfigByPage(Page<TenantConfig> page, TenantConfigVO bean);

	/**
     * @Description: 主键查询多租户配置
     * @return
     */
	Result<?> selectTenantConfigById(TenantConfig bean);

	/**
     * @Description： 添加多租户配置
     * @return
     */
	void saveTenantConfig(TenantConfig bean);
	
	/**
     * @Description： 修改多租户配置
     * @return
     */
	void updateTenantConfig(TenantConfig bean);

	/**
     * @Description： 删除多租户配置
     * @return
     */
	void deleteTenantConfig(TenantConfigVO bean);

	

	
	
	
	

}
