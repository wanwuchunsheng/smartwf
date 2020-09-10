package com.smartwf.hm.modules.alarmstatistics.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;

/**
 * @author WCH
 * @Date: 2018/12/18 15:43
 * @Description: 缺陷业务层接口
 */
public interface DefectService {

	/**
	 * 初始化缺陷数据
	 * @return
	 */
	void initDefectAll();

	/**
	 * 缺陷工单录入接口
	 * @param bean
	 * @return
	 */
	void saveDefect(DefectVO bean);

	/**
	 * 缺陷工单处理
	 * @param id
	 * @param bean
	 * @param alarmStatus 5待审核  6驳回  0未处理  1已转工单  2处理中  3已处理  4已关闭  7回收站  8未解决
     *
	 */
	void updateDefectById(DefectVO bean);

	/**
	 * 缺陷信息主键查询
	 *    缺陷详细
	 * @param bean
	 * @param id
	 * @return
	 */
	Result<?> selectDefectById(DefectVO bean);

	/**
	 * 分页查询缺陷信息 
	 *    列表信息
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @param page
	 * @return
	 */
	Result<?> selectDefectByPage(Page<DefectVO> page, DefectVO bean);

	/**
	 * 实时缺陷总数查询
	 * @return
	 */
	Integer selectDefectCountByAll(String tenantDomain, String windFarm);
	/**
   	 * 查询所有缺陷记录信息 
   	 * @param faultInfoId
   	 * @param tenantDomain
   	 * @param bean
   	 * @return
   	 */
	Result<?> selectDefectByAll(FaultOperationRecord bean);

	


}
