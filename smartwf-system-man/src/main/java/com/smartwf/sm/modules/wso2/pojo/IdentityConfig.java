package com.smartwf.sm.modules.wso2.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("sys_identity")
public class IdentityConfig implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 登录密码
	 */
	private String openId;
	/**
	 * wso2子系统注册配置
	 */
	private String clientKey;
	/**
	 * wso2子系统注册配置
	 */
	private String clientSecret;
	/**
	 * 子系统前端地址
	 */
	private String redirectUri;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否有效
            0有效  
            1无效
            
	 */
	private Integer enable;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人id
	 */
	private Integer createUserId;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改人id
	 */
	private Integer updateUserId;
	/**
	 * 修改人姓名
	 */
	private String updateUserName;

}
