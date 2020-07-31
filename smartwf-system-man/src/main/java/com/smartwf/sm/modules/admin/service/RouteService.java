package com.smartwf.sm.modules.admin.service;

import com.smartwf.common.pojo.Result;

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

	
}
