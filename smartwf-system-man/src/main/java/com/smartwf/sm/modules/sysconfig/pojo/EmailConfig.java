package com.smartwf.sm.modules.sysconfig.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 邮件短信表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-10-22 20:42:36
 */
@Data
@TableName("basic_email_config")
public class EmailConfig implements Serializable {
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
	 * 端口
	 */
	private Integer port;
	/**
	 * 登录账号
	 */
	private String loginCode;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 类型 0邮件 1短信 
	 */
	private Integer type;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
