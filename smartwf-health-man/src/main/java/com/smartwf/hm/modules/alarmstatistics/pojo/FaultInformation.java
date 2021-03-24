package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Getter;
import lombok.Setter;
/**
 * @author WCH
 * @Description: 故障报警表
 */
@Getter
@Setter
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
	@NotNull(message = "id不能为空", groups = Update.class)
	@NotNull(message = "id不能为空", groups = Query.class)
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
	private String startTime;
	/**
	 * 故障最后时间
	 */
	private String endTime;
	/**
	 * 事变类型
	 *  1-故障类型
     *  2-缺陷类型
     *  3-告警类型
	 */
	private Integer incidentType;
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
	 * 0-系统故障
	 * 1-监控警告
	 * 2-人工提交
	 * 3-预警警告

	 */
	private Integer faultType;
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
	@NotNull(message = "风场不能为空", groups = QueryParam.class)
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
	 *  故障状态
		0未处理
		1已转工单
		2处理中
		3已处理
		4已关闭
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
	 * 租户域
	 */
	@NotNull(message = "租户域不能为空", groups = QueryParam.class)
	private String tenantDomain;
	
}
