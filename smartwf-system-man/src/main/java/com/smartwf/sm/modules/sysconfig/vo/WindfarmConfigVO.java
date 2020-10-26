package com.smartwf.sm.modules.sysconfig.vo;

import java.util.Date;

import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;

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
public class WindfarmConfigVO extends WindfarmConfig{
	
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
	 * 省名称
	 */
	private String proName;
	/**
	 * 市名称
	 */
	private String cityName;
	/**
	 * 县/区名称
	 */
	private String areaName;
	
	private String unit;

}
