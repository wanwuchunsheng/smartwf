package com.smartwf.hm.modules.alarmstatistics.service;

import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;

/**
 * @author WCH
 * @Date: 2018/12/18 15:43
 * @Description: 实时故障数据业务层接口
 */
public interface FaultDataService {
	/**
	 * 实时故障报警缺陷数据
	 *     故障、报警、缺陷实时数据
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	void saveFaultInformation(FaultInformation bean);


}
