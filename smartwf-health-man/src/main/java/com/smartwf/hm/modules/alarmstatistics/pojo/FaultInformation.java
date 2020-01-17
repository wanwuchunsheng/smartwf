package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * @Description: 故障报警表
 */
@Data
@TableName("fault_information")
public class FaultInformation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	//@TableId
	@TableId(type = IdType.UUID)
	private String id;

	/**
	 * 报警码
	 */
	private String alarmCode;
	/**
	 * 报警源
	 *
	 */
	private String alarmSource;
	/**
	 * 报警描述
	 */
	private String alarmDescription;
	/**
	 * 报警部位
	 */
	private String alarmLocation;
	/**
	 * 故障开始时间
	 */
	private Date startTime;
	/**
	 * 故障最后时间
	 */
	private Date endTime;
	/**
	 * 报警级别
            0红：严重
            1橙：普通
            2紫：一般
            3灰：位置
            
	 */
	private Integer alarmLevel;
	/**
	 * 故障类型
	 */
	private String faultType;
	/**
	 * 厂家
	 */
	private String manufacturers;
	/**
	 * 设备
	 */
	private String device;
	/**
	 * 风场
	 */
	private String windFarm;
	/**
	 * 故障状态
	 *  0未处理
		1已转工单
		2处理中
		3已处理
		4已关闭

	 */
	private Integer alarmStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 租户表id
	 */
	private String tenantCode;
	
	/**
	 * 资产编码
	 */
	private String assetNumber;
	
	/**
	 * 操作状态
	 *  0-重点关注
	 */
	private Integer operatingStatus;
}
