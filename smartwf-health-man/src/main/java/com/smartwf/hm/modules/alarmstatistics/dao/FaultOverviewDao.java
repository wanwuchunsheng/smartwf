package com.smartwf.hm.modules.alarmstatistics.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import org.apache.ibatis.annotations.Param;


/**
 * @Date: 2019-10-25 16:05:30
 * @Description: 日志持久层接口
 * Mapper接口
 *      基于Mybatis:  在Mapper接口中编写CRUD相关的方法  提供Mapper接口所对应的SQL映射文件 以及 方法对应的SQL语句.
 *      基于MP:  让XxxMapper接口继承 BaseMapper接口即可.
 * 		   BaseMapper<T> : 泛型指定的就是当前Mapper接口所操作的实体类类型
 */
@Repository
public interface FaultOverviewDao extends BaseMapper<FaultInformation> {
	/**
	 * @Description: 故障
	 * @param faultType 0-故障  1-报警   2-缺陷
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FaultInformationVO> selectFaultTypeByFault(@Param("bean") FaultInformationVO bean);
	/**
	 * @Description: 报警
	 * @param faultType 0-故障  1-报警   2-缺陷
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FaultInformationVO> selectFaultTypeByAlarm(@Param("bean") FaultInformationVO bean);
	/**
	 * @Description: 缺陷
	 * @param faultType 0-故障  1-报警   2-缺陷
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FaultInformationVO> selectFaultTypeByDefect(@Param("bean") FaultInformationVO bean);
	/**
	 * @Description: 故障等级分布统计 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FaultInformationVO> selectFaultLevelByDate(@Param("bean") FaultInformationVO bean);


    
}
