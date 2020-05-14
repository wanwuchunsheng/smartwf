package com.smartwf.hm.modules.knowledgebase.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 风机故障代码表
 * 
 * @author wch
 * @date 2020-05-14 11:02:23
 */
@Data
@TableName("fault_file_record")
public class FaultCodeBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.UUID)
	private String id;
	/**
	 * 风电机组模型号
	 */
	private String model;
	/**
	 * 协议号
	 */
	private String protocolNo;
	/**
	 * IEC路径
	 */
	private String iecPath;
	/**
	 * 故障代码
	 */
	private String faultCode;
	/**
	 * 故障名称
	 */
	private String faultName;
	/**
	 * 英文名称
	 */
	private String engName;
	/**
	 * 部件名称
	 */
	private String componentName;
	/**
	 * 资产型号ID
	 */
	private String pmsAmId;
	/**
	 * 风机部件ID
	 */
	private String pmsAiId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态 0默认  1审核通过
	 */
	private Integer status;
	/**
	 * 审批人
	 */
	private String checker;
	/**
	 * 审批时间
	 */
	private Date checkTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人ID
	 */
	private String createUserId;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 租户表code
	 */
	private String tenantCode;
}
