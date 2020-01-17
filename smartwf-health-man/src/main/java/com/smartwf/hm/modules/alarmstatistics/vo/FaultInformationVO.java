package com.smartwf.hm.modules.alarmstatistics.vo;

import java.io.Serializable;

import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;

import lombok.Data;
/**
 * @Description: 故障报警表
 */
@Data
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
	private String stime;
	/**
	 * 结束时间
	 */
	private String etime;
	
}
