package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;
/**
 * @Description: 故障报警表
 */
@Data
@TableName("fault_information")
public class FaultInformation extends BasePojo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	/**
	 * 主键
	 */
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 故障最后时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 事变类型
	 *  1-	故障类型
		2-	缺陷类型
	 */
	private Integer IncidentType;
	/**
	 * 报警级别
	   0红：危急
		1橙：严重
		2紫：一般
		3灰：未知
  
	 */
	private Integer alarmLevel;
	/**
	 * 故障类型
	 * 0-系统报警
		1-预警信息
		2-人工提交

	 */
	private String faultType;
	/**
	 * 厂家
	 */
	private String manufacturers;
	/**
	 * 设备编码
	 */
	private String deviceCode;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 风场
	 */
	private String windFarm;
	/**
	 * 资产编码
	 */
	private String assetNumber;
	/**
	 * 工单号
	 */
	private String orderNumber;
	/**
	 * 故障状态
	 * 5待审核
		6驳回
		0未处理
		1已转工单
		2处理中
		3已处理
		4已关闭
		7回收站
		8未解决
	 */
	private Integer alarmStatus;
	/**
	 * 操作状态
	 *  1-重点关注
	 */
	private Integer operatingStatus;
	/**
	 * 发现人姓名
	 */
	private String discovererName;
	/**
	 * 发现人ID
	 */
	private String discovererId;
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	/**
	 * 租户表code
	 */
	private String tenantCode;
	
}
