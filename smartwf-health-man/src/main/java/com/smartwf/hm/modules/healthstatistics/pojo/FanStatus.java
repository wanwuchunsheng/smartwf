package com.smartwf.hm.modules.healthstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 风机状态表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-26 09:27:33
 */
@Getter
@Setter
@TableName("pms_fan_status")
public class FanStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 外主键 pms_asset_model表id
	 */
	private String amId;
	/**
	 * 资产编码 pms_asset_mode表
            device_code
	 */
	private String deviceCode;
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
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 风场
	 */
	private String windFarm;
	/**
	 * 租户域
	 */
	private String tenantDomain;
}
