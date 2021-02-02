package com.smartwf.hm.modules.alarmstatistics.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.SecurityIncidents;
import com.smartwf.hm.modules.alarmstatistics.vo.SecurityIncidentsVO;

/**
 * 安全事故实现类
 * @author WCH
 * @Date: 2021年2月2日11:35:53
 */
public interface SecurityIncidentsService {
	
	
	/**
	 * @Description: 安全事故-分页查询
	 *    列表信息
	 * @param startTime,endTime
	 * @param  incidentCode,locality
	 * @return
	 */
	Result<?> selectAlarmInforByPage(Page<SecurityIncidents> page, SecurityIncidentsVO bean);

	/**
	 * @Description: 安全事故-主键查询
	 * @param id
	 * @return
	 */
	Result<?> selectSecurityIncidentsById(SecurityIncidents bean);

	/**
	 * @Description: 安全事故-附件查询
	 * @param id
	 * @return
	 */
	Result<?> selectSecurityIncidentsByFiles(SecurityIncidents bean);

	/**
	 * @Description: 安全事故-添加
	 * @param id
	 * @return
	 */
	Result<?> saveSecurityIncidents(SecurityIncidentsVO bean);

	 /**
	 * @Description: 安全事故-修改
	 * @param id
	 * @return
	 */
	Result<?> updateSecurityIncidents(SecurityIncidentsVO bean);

}
