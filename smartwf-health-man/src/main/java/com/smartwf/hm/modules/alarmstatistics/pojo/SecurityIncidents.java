package com.smartwf.hm.modules.alarmstatistics.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 安全事故表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-02-02 11:25:39
 */
@Setter
@Getter
@TableName("security_incidents")
public class SecurityIncidents extends BasePojo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.UUID)
	private String id;
	/**
	 * 租户域
	 */
	private String tenantDomain;
	/**
	 * 风场ID
	 */
	private String windFarm;
	/**
	 * 事故标题
	 */
	private String incidentTitle;
	/**
	 * 事故编码
	 */
	private String incidentCode;
	/**
	 * 发生时间
	 */
	private Date occurrenceTime;
	/**
	 * 完成期限
	 */
	private Date completionDeadlin;
	/**
	 * 发生地点
	 */
	private String locality;
	/**
	 * 简要经过
	 */
	private String afterBriefly;
	/**
	 * 人身伤害情况
	 */
	private String personalInjury;
	/**
	 * 电力设施损坏情况
	 */
	private String powerFacilitiesDamaged;
	/**
	 * 不良社会影响
	 */
	private String adverseSocialInfluence;
	/**
	 * 其他损失情况
	 */
	private String otherFacilitiesDamaged;
	/**
	 * 事故状态0-待审核 1-通过 2-不通过
	 */
	private Integer incidentStatus;
	
	/**
	 * 备注（其他情况）
	 */
	private String remark;
	

}
