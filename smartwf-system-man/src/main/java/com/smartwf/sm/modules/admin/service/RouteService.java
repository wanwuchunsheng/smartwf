package com.smartwf.sm.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.PortalPowerGeneration;

/**
 * @Description: 门户业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface RouteService {
	/**
     * 门户菜单查询
     * @return
     */
	Result<?> selectRouteByAll();

	/**
     * 门户发电量统计数据  - 删除
     * @author WCH
     * @param bean
     * @return
     */
	void deletePortalPowerGenById(PortalPowerGeneration bean);

	/**
     * 门户发电量统计数据 -列表
     * @author WCH
     * @param bean
     * @param page
     * @return
     * 
     */
	Result<?> selectPortalPowerGenByAll(Page<PortalPowerGeneration> page, PortalPowerGeneration bean);

	
}
