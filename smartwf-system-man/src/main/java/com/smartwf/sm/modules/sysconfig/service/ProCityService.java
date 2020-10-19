package com.smartwf.sm.modules.sysconfig.service;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.SysProvince;

public interface ProCityService {

	/**
	 * @Description: 查询省市级联分页
	 * @param bean
	 * @return
	 */
	Result<?> selectProAll(SysProvince bean);

	/**
	 * @Description: 市查询 -通过省id或者市ID查询
	 * @param proId , cityId
	 * @return
	 */
	Result<?> selectCityByProId(String proCode, String cityCode);

	/**
	 * @Description: 县/区查询 -通过市Id，省ID
	 * @param proId , cityId
	 * @return
	 */
	Result<?> selectAreaByCityId(String cityCode);

}
