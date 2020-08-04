package com.smartwf.hm.modules.alarmstatistics.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;
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
public interface AlarmInboxDao extends BaseMapper<FaultInformation> {
	
	
	/**
	 * 故障部位，数量统计
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @param page
	 * @return
	 */
	List<FaultInformationVO> selectAlarmInforByPage(Page<FaultInformation> page,@Param("bean") FaultInformationVO bean);
	/**
	 * 查询所有故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @return
	 */
	List<FaultInformationVO> selectAlarmInforByAll(@Param("bean") FaultInformationVO bean);
	/**
	 * 查询所有未处理的故障报警数据
	 * @return
	 */
	@MapKey("id")
	Map<String, FaultInformation> selectFaultInformationByAll();
	
	/**
 	 * 重点机位统计数据-图表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	List<FaultInformationVO> selectKeyPositionByCount(@Param("bean") KeyPosition bean);
	
	/**
 	 *  重点机位统计数据-列表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @param bean
 	 * @return
 	 */
	List<FaultInformationVO> selectKeyPositionByList(@Param("bean") KeyPosition bean);
	
	
	

}
