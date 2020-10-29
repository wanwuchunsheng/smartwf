package com.smartwf.hm.modules.admin.service;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;

public interface GlobalService {

	/**
   	 *  故障、警告、缺陷 主键查询{生产中心提供接口}
   	 *     1）根据id查询对象
   	 *     2）根据对象类型判断调用哪个接口返回
   	 * @author WCH
   	 * @return
   	 */
	Result<?> selectfaultInformationById(String id);

	/**
	 * @Description: 故障、警告、缺陷处理意见 {生产中心提供接口}
	 * @author WCH
	 * @dateTime 2020-7-20 17:55:35
	 * @param bean
	 * @return
	 */
	Result<?> selectFaultRecordByAll(FaultOperationRecord bean);

}
