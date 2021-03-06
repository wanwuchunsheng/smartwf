package com.smartwf.hm.modules.alarmstatistics.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;


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
public interface DefectDao extends BaseMapper<FaultInformation> {

	/**
	 * 初始化缺陷数据
	 * @return
	 */
	@MapKey("id")
	Map<String, FaultInformation> initDefectAll();

	/**
	 *  缺陷信息主键查询
	 *    缺陷详细
	 * @param bean
	 * @return
	 */
	FaultInformation selectDefectById(@Param("bean") DefectVO bean);

	/**
	 * 分页查询缺陷信息 
	 *    列表信息
	 * @param startTime
	 * @param endTime
	 * @param bean
	 * @param page
	 * @return
	 */
	List<DefectVO> selectDefectByPage(Page<DefectVO> page, @Param("bean") DefectVO bean);
	
	
	

}
