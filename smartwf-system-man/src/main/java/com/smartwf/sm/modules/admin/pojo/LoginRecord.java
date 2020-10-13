package com.smartwf.sm.modules.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 登录记录表
 * 
 * @author wch
 * @date 2020-04-21 14:43:59
 */
@Data
@TableName("sys_login_record")
public class LoginRecord implements Serializable {
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
	 * 登录账号
	 */
	private String loginCode;
	/**
	 * 登录方式（Android/IOS/PC）
	 */
	private String loginType;
	/**
	 * 登录IP
	 */
	private String ipAddress;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 状态(0登录  1注销)
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
