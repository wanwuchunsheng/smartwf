package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author WCH
 * @deprecated 故障记录实体类
 * 
 * */

@Data
@TableName("fault_operation_record")
public class FaultOperationRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.UUID)
	private String id;
	/**
	 * 故障主键
	 */
	private String faultInfoId;
	/**
	 * 操作状态
	 * 5待审核
		6驳回
		0未处理
		1已转工单
		2处理中
		3已处理
		4已关闭
		7回收站
		8未解决
	 * 
	 */
	private Integer closureStatus;
	/**
	 * 操作说明
	 * 
	 */
	private String closureReason;
	/**
	 * 操作类型
	 * 1.处理记录
	 * 2.处理意见
	 */
	private Integer closureType;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 创建人id
	 */
	private String createUserId;
	
	/**
	 * 租户编码
	 */
	private String tenantCode;
	
}
