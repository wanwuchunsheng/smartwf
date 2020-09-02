package com.smartwf.sm.modules.admin.vo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.sm.modules.admin.pojo.PortalPowerGeneration;

import lombok.Getter;
import lombok.Setter;


/**
 * 门户发电量统计表
 * 
 * @author WCH
 * @date 2020-09-01 10:23:51
 */

@Getter
@Setter
public class PortalPowerGenerationVO extends PortalPowerGeneration{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 总发电量
	 */
	private Double totalElectPg;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	
	
}

