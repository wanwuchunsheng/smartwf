package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.RouteConfig;
import com.smartwf.sm.modules.sysconfig.vo.RouteConfigVO;

/**
 * @Description: 路由配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface RouteConfigService {

	/**
	 * @Description: 查询路由配置分页
	 * @param bean
	 * @return
	 */
	Result<?> selectRouteConfigByPage(Page<RouteConfig> page, RouteConfigVO bean);

	/**
     * @Description: 主键查询路由配置
     * @param bean
     * @return
     */
	Result<?> selectRouteConfigById(RouteConfig bean);

	/**
     * @Description: 添加路由配置
     * @param bean
     * @return
     */
	void saveRouteConfig(RouteConfig bean);

	/**
     * @Description： 修改路由配置
     * @param bean
     * @return
     */
	void updateRouteConfig(RouteConfig bean);

	/**
     * @Description： 删除路由配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteRouteConfig(RouteConfigVO bean);
	
	
	
	

}
