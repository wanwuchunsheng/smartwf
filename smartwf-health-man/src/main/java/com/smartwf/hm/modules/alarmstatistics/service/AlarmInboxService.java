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
	Result<?> selectAlarmInforByPage( Page<FaultInformation> page,FaultInformationVO bean);
	/**
	 * @Description: 查询所有故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Result<?> selectAlarmInforByAll(FaultInformationVO bean);
	/**
	 * @Description: 实时故障报警总数查询
	 * @return
	 */
	Integer selectAlarmsCountByAll();
	/**
	 * @Description:故障报警修改
	 * @param id
	 */
	void updateAlarmInforById(FaultInformationVO bean);
	/**
	 * @Description: 主键查询
	 * @param id
	 */
	Result<?> selectAlarmInforById(FaultInformationVO bean);
	/**
	 * @Description: 初始化未结束的故障
	 * @return
	 */
	void selectFaultInformationByAll();
	
	


}
