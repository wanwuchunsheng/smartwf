package com.smartwf.sm.modules.admin.service;

import java.util.Map;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;

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
     * 门户发电量统计数据 -发电量统计
     * @author WCH
     * @param bean
     * @return
     * 
     */
	Result<?> selectPortalPowerGenByParam(WindfarmConfig bean);

	/**
     * 门户状态统计 - 设备状态
     * @author WCH
     * @param bean
     * @return
     * 
     */
	Result<?> selectPortalStatusByParam(WindfarmConfig bean);

	/**
     * 门户人员统计 -场站、风场
     * @author WCH
     * @param bean
     * @return
     * 
     */
	Result<?> selectPortalUserByParam(WindfarmConfig bean);

	/**
     * 门户天气查询
     * @author WCH
     * @param bean
     * @return
     * 
     */
	Result<?> selectPortalWeatherByParam(TenantConfig bean);

	/**
     * 门户-省份风场统计
     * @author WCH
     * @param bean
     * @return
     */
	Result<?> selectWindfarmConfigByProCode(WindfarmConfig bean);

	/**
     * 健康中心 -人员/角色查询
     * @author WCH
     * @param tenantDomain
     * @param windFarm
     * @return
     */
	Map<String, Object> selectWindfarmUserAndRole(String tenantDomain, String windFarm);

	
}
