package com.smartwf.hm.modules.alarmstatistics.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.hm.modules.alarmstatistics.pojo.SecurityIncidents;

import lombok.Getter;
import lombok.Setter;

/**
 * 安全事故表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-02-02 11:25:39
 */
@Setter
@Getter
@TableName("security_incidents")
public class SecurityIncidentsVO extends SecurityIncidents{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  起始时间
	 */
	private String startTime;
	/**
	 *  结束时间
	 */
	private String endTime;
	/**
	 *  文件路径
	 */
	private String filePath;
	/**
	 *  文件大小
	 */
	private long fileSize;

}
