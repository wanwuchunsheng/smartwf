package com.smartwf.hm.modules.alarmstatistics.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.hm.modules.alarmstatistics.pojo.SecurityIncidents;
import com.smartwf.hm.modules.alarmstatistics.vo.SecurityIncidentsVO;


/**
 * 安全事故数据层
 * @author WCH
 * @param 
 * 
 * */
@Repository
public interface SecurityIncidentsDao extends BaseMapper<SecurityIncidents> {

	/**
	 * 功能说明： 统计事故距离今天总天数
	 * @author WCH
	 * @DateTime 2021-2-5 10:41:39
	 * @param bean
	 * 
	 * */
	Map<String,Object> selectSafetyProductionTotalDays(@Param("bean") SecurityIncidentsVO bean);

}
