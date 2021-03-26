package com.smartwf.hm.modules.healthstatistics.vo;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class FanStatusVO {
	
	
	/**
	 * 分析状态 二级状态
            #正常
            R001运行正常
            R002天气停机
            R003限功率运行
            R0004限功率停机
            #故障
            F001保护停机
            F002故障停机
            F003就地停机
            F004电网故障
            F005通信中断
            #缺陷
            ...
            #告警
            W001风机维护
            W002远程停机
            W003技术待命
            W004警告
            W005其他限电
	 */
	private String analysisState;
	/**
	 * 分析结果
	 */
	private String analysisResult;
	
	/**
	 * 统计值
	 * 
	 * */
	private Integer count;
	
	
	/**
	 * 运行状态 0正常
            1故障
            2缺陷
            3告警
	 */
	private Integer runStatus;
	
	
	
}
