package com.smartwf.hm.modules.alarmstatistics.service;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FileUploadRecord;
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

	/**
 	 *  功能说明：安全生产多少天
 	 *    根据事故记录分析系统运行天数
 	 * @author WCH
 	 * @return
 	 */
	Result<?> selectSafetyProductionTime(SecurityIncidentsVO bean,HttpServletRequest request);

	/**
	 * @Description: 安全事故-删除附件
	 * @param id
	 * @return
	 */
	Result<?> deleteSecurityIncidentsByFiles(SecurityIncidentsVO bean);

	/**
	 * 功能说明：通过主键,路径查询附件信息
	 * @author WCH
	 * @Date 2021年2月4日13:59:17
	 * 
	 * */
	FileUploadRecord selectFileUploadRecord(SecurityIncidentsVO bean);
	
	/**
	 * 功能说明：初始化安全生产天数
	 * @author WCH
	 * @Date 2021年2月4日13:59:17
	 * 
	 * */
	void initSecurityIncidentsService();

}
