package com.smartwf.hm.modules.healthstatistics.service;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.healthstatistics.pojo.FanStatus;

public interface FanStatusService {

	/**
	 * @Description: 统计风机运行状态
	 *    统计结果信息
	 * @param tenantDomain
	 * @param windFarm
	 * @author WCH
	 * @Datetime 2021-3-26 10:26:07
	 * @return
	 */
	Result<?> selectFanRunStatusByCount(FanStatus bean);

}
