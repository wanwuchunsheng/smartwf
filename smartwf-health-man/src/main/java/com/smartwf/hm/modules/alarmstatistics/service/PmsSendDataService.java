package com.smartwf.hm.modules.alarmstatistics.service;

import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

/**
 * 发送数据到PMS
 * @author WCH
 * @Date: 2020-7-23 09:29:25
 *
 */
public interface PmsSendDataService {

	/**
	 * 故障报警信息转工单
	 * @author WCH
	 * @Date: 2020-7-23 09:29:25
	 * @param bean
	 *
	 */
	void faultWordOrder(FaultInformationVO bean);
	/**
	 * 缺陷转工单
	 * @author WCH
	 * @Date: 2020-7-23 09:29:25
	 * @param bean
	 *
	 */
	void faultWordOrder(DefectVO bean);
	

}
