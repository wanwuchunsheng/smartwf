package com.smartwf.hm.modules.alarmstatistics.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;


/**
 * @author WCH
 * @Date: 2019-10-25 16:05:30
 * @Description: 持久层接口
 * Mapper接口
 *      基于Mybatis:  在Mapper接口中编写CRUD相关的方法  提供Mapper接口所对应的SQL映射文件 以及 方法对应的SQL语句.
 *      基于MP:  让XxxMapper接口继承 BaseMapper接口即可.
 * 		   BaseMapper<T> : 泛型指定的就是当前Mapper接口所操作的实体类类型
 */
@Repository
public interface FaultOverviewDao extends BaseMapper<FaultInformation> {
	
	
	/**
	 *  故障
	 * @param faultType 0-故障  1-报警   2-缺陷
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	List<FaultInformationVO> selectFaultTypeByFault(@Param("bean") FaultInformationVO bean);
	/**
	 * 报警
	 * @param faultType 0-故障  1-报警   2-缺陷
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	List<FaultInformationVO> selectFaultTypeByAlarm(@Param("bean") FaultInformationVO bean);
	/**
	 * 缺陷
	 * @param faultType 0-故障  1-报警   2-缺陷
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	List<FaultInformationVO> selectFaultTypeByDefect(@Param("bean") FaultInformationVO bean);
	/**
	 * 故障等级分布统计 
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	List<FaultInformationVO> selectFaultLevelByDate(@Param("bean") FaultInformationVO bean);
	/**
	 *  故障状态，数量统计
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	List<FaultInformationVO> selectFaultStatusByDate(@Param("bean") FaultInformationVO bean);
	/**
	 * @Description: 处理效率统计
	 * @param alarmStatus 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FaultInformationVO> selectFaultLocationByDate(@Param("bean") FaultInformationVO bean);
	/**
     * 说明：门户故障/缺陷/警告 统计  -统计
     *   1）查询所有信息状态 
     *   2）查询已处理的信息列表
     * @param bean
     * @return
     * */
	List<Map<String,String>> selectFaultByAlarmStatus(@Param("bean") FaultInformationVO bean);
	
	/**
     * 说明：门户故障/缺陷/警告 统计 -列表
     *   1）查询所有信息状态
     *   2）查询已处理的信息列表
     * @param bean
     * @return
     * */
	List<Map<String,String>> selectFaultInformationByPage(Page<FaultInformation> page, @Param("bean") FaultInformationVO bean);
	
	/**
   	 * @Description: (故障、缺陷、警告)未处理记录统计
   	 * @author WCH
   	 * @dateTime 2020-7-20 17:55:35
   	 * @param bean
   	 * @return
   	 */
	List<Map<String,Object>> selectFaultRecordByIncidentType(@Param("bean") FaultInformationVO bean);
	/**
	 * 故障，警告，缺陷全局未处理数统计
   	 * @author WCH
   	 * @dateTime 2020-7-20 17:55:35
   	 * @param bean
   	 * @return
	 * */
	List<Map<String, Integer>> selectFaultStatusByAll(@Param("bean") FaultInformationVO bean);
	

}
