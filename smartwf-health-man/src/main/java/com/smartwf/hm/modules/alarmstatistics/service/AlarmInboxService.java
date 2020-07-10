package com.smartwf.hm.modules.alarmstatistics.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 报警收件箱业务层接口
 * @author WCH
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
	/**
   	 * @Description: 查询所有故障报警记录信息 
   	 *   故障操作记录
   	 * @param faultInfoId
   	 * @param tenantCode
   	 * @return
   	 */
	Result<?> selectFaultRecordByAll(FaultOperationRecord bean);
	/**
 	 * @Description: 重点机位添加
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	void addKeyPosition(KeyPosition bean);
	/**
 	 * @Description: 重点机位删除
 	 *  通过重点机位表主键ID删除
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	void deleteKeyPosition(KeyPosition bean);
	/**
 	 * @Description: 重点机位统计数据-图表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	Result<?> selectKeyPositionByCount(KeyPosition bean);
	/**
 	 * @Description: 重点机位统计数据-列表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	Result<?> selectKeyPositionByList(KeyPosition bean);
	/**
 	 * @Description: 重点机位已添加机位数据查询接口
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	Result<?> selectKeyPosition(KeyPosition bean);
	/**
 	 * @Description: 单个重点机位所有故障报警数据
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	Result<?> selectKeyPositionByDeviceCode(KeyPosition bean);
	
	


}
