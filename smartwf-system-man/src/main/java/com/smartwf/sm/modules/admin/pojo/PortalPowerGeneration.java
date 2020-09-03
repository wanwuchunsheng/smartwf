package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("portal_power_generation")
public class PortalPowerGeneration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 租户域
	 */
	private String tenantDomain;
	/**
	 * 风场
	 */
	private String windFarm;
	/**
	 * 日发电量
	 */
	private Double dailyElectPg;
	
	/**
	 * 实时容量
	 */
	private Double realTimePg;
	/**
	 * 装机容量
	 */
	private Double installedCapacity;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	
	
}

