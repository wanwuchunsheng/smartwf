package com.smartwf.sm.modules.wso2.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 
 * @author WCH
 * 
 * */
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
	 * 备注
	 */
	private String remark;
	
	/**
	 * 是否需要验证accessToken
	 * true 是
	 * flase 否
	 */
	private boolean flag;
	
	/**
	 * 是否有效
            0有效  
            1无效
            
	 */
	private Integer enable;
	

}
