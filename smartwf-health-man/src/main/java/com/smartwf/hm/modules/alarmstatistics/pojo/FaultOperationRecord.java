package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.annotation.ParamValidated;
import com.smartwf.common.annotation.ParamValidated.Add;
import com.smartwf.common.annotation.ParamValidated.Query;
import com.smartwf.common.annotation.ParamValidated.QueryParam;

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
	@NotNull(message = " 故障主键不能为空",groups = QueryParam.class)
	@NotNull(message = " 故障主键不能为空",groups = Add.class)
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
	@NotNull(message = " 操作说明不能为空",groups = Add.class)
	private String closureReason;
	/**
	 * 操作类型
	 * 1.处理记录
	 * 2.处理意见
	 */
	@NotNull(message = " 操作类型不能为空",groups = Add.class)
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
	 * 租户域
	 */
	@NotNull(message = " 租户域不能为空",groups = Add.class)
	private String tenantDomain;
	
}
