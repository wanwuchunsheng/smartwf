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
	
}
