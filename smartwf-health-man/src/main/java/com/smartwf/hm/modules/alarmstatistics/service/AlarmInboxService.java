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
	 * 分页查询故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @param page
	 * @return
	 */
	Result<?> selectAlarmInforByPage( Page<FaultInformation> page,FaultInformationVO bean);
	/**
	 * 查询所有故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	Result<?> selectAlarmInforByAll(FaultInformationVO bean);
	/**
	 * 实时故障报警总数查询
	 * @return
	 */
	Integer selectAlarmsCountByAll();
	/**
	 * 故障报警修改
	 * @param bean
	 * @param id
	 */
	void updateAlarmInforById(FaultInformationVO bean);
	/**
	 * 主键查询
	 * @param id
	 * @param bean
	 * @return
	 */
	Result<?> selectAlarmInforById(FaultInformationVO bean);
	/**
	 * 初始化未结束的故障
	 * @return
	 */
	void selectFaultInformationByAll();
	/**
   	 * 查询所有故障报警记录信息 
   	 *   故障操作记录
   	 * @param faultInfoId
   	 * @param tenantDomain
   	 * @param bean
   	 * @return
   	 */
	Result<?> selectFaultRecordByAll(FaultOperationRecord bean);
	/**
 	 * 重点机位添加
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	void addKeyPosition(KeyPosition bean);
	/**
 	 * 重点机位删除
 	 *  通过重点机位表主键ID删除
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	void deleteKeyPosition(KeyPosition bean);
	/**
 	 * 重点机位统计数据-图表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	Result<?> selectKeyPositionByCount(KeyPosition bean);
	/**
 	 * 重点机位统计数据-列表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	Result<?> selectKeyPositionByList(KeyPosition bean);
	/**
 	 * 重点机位已添加机位数据查询接口
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	Result<?> selectKeyPosition(KeyPosition bean);
	/**
 	 * 单个重点机位所有故障报警数据
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	Result<?> selectKeyPositionByDeviceCode(KeyPosition bean);
	/**
	 *  故障处理意见
	 *    添加
	 * @author WCH
	 * @dateTime 2020-7-20 17:55:35
	 * @param bean
	 */
	void addFaultOperationRecord(FaultOperationRecord bean);
	/**
   	 *  故障、缺陷{转工单}
   	 *      生产中心状态修改
   	 *      1）修改工单状态
   	 *      2）记录表插入修改记录
   	 * @author WCH
   	 * @param bean
   	 */
	void updateAlarmInByParam(FaultInformationVO bean);
	
	


}
