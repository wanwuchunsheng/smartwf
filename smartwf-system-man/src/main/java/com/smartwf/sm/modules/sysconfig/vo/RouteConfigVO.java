package com.smartwf.sm.modules.sysconfig.vo;

import java.util.Date;

import com.smartwf.sm.modules.sysconfig.pojo.RouteConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * @author WCH
 * 
 * */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RouteConfigVO extends RouteConfig{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id批量删除
	 */
	private String ids; 
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 租户名称
	 */
	private String tenantName;
	/**
	 * 风场名称
	 */
	private String windFarmName;

}
