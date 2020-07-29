package com.smartwf.hm.modules.alarmstatistics.vo;

import java.io.Serializable;
import java.util.Date;

import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * @Description: 故障报警表
 * @author WCH
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class FaultInformationVO extends FaultInformation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String fname;
	/**
	 * 值
	 */
	private String fvalue;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 关闭原因
	 */
	private String closureReason;
	/**
	 * 开始时间
	 */
	private Date stime;
	/**
	 * 结束时间
	 */
	private Date etime;
	
	/**
	 * 未处理
	 * 
	 * */
	private Integer untreated;
	/**
	 * 故障类型-天
	 * 
	 * */
	private Integer faultTypeDay;
	/**
	 * 故障类型-月
	 * 
	 * */
	private Integer faultTypeMouth;
	/**
	 * 故障类型-所有
	 * 
	 * */
	private Integer faultTypeAll;
	
	/**
	 * 故障主键
	 * 
	 * */
	private Integer faultId;
	
	/**
	 * 最近时间
	 * 
	 * */
	private String recentTime;
	
	
	
}
