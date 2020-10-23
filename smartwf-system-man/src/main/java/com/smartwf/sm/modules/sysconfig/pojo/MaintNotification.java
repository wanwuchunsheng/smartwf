package com.smartwf.sm.modules.sysconfig.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 维护通知表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-10-22 20:42:36
 */
@Data
@TableName("basic_maint_notification")
public class MaintNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 租户域名
	 */
	private String tenantDomain;
	/**
	 * 风场id
	 */
	private Integer windFarm;
	/**
	 * 服务地址
	 */
	private String serviceAddress;
	/**
	 * 状态 0运行  1停止
	 */
	private Integer status;
	/**
	 * 通告消息
	 */
	private String msg;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

}
