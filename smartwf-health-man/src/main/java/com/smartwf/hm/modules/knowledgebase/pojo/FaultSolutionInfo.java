package com.smartwf.hm.modules.knowledgebase.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 故障解决方案
 * 
 * @author wch
 * @date 2020-05-14 11:02:23
 */
@Data
@TableName("fault_solution_info")
public class FaultSolutionInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 故障代码表id
	 */
	private String faultCode;
	/**
	 * 类型
            1触发条件
            2故障原因
            3故障处理
	 */
	private Integer type;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否启用  0启用   1禁用
	 */
	private Integer enable;
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
	 * 租户域
	 */
	private String tenantDomain;
}
