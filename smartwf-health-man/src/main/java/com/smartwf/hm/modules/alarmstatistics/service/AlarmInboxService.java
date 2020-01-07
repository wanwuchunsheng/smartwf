package com.smartwf.hm.modules.alarmstatistics.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 报警收件箱业务层接口
 */
public interface AlarmInboxService {

	/**
	 * @Description: 分页查询故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Result<?> selectFaultInforByPage( Page<FaultInformation> page,FaultInformationVO bean);
	


}
