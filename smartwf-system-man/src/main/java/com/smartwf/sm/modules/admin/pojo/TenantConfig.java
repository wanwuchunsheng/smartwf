package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.annotation.ParamValidated.Add;
import com.smartwf.common.annotation.ParamValidated.Query;
import com.smartwf.common.annotation.ParamValidated.Update;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 租户表
 */
@Data
@TableName("sys_tenant_config")
public class TenantConfig extends BasePojo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 /**
	  * 主键
	  * */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "id不能为空", groups = Query.class)
	@NotNull(message = "id不能为空", groups = Update.class)
    private String id;
    /**
	  * 租户ID
	  * */
	@NotNull(message = "租户ID不能为空！", groups = Add.class)
	private String tenantId;
	/**
	  * 租户登录名
	  * */
	@NotNull(message = "登录名不能为空！", groups = Add.class)
	private String loginName;
	/**
	  * 登录名密码
	  * */
	@NotNull(message = "登录密码不能为空！", groups = Add.class)
	private String loginPwd;
	/**
	  * 路径地址
	  * */
	private String pathUrl;
	/**
	  * 类型
	  * 1- IOT信息
	  * */
	@NotNull(message = "类型不能为空！", groups = Add.class)
	private String type;
	/**
	  * 备注
	  * */
	private String remark;
	/**
	  * 是否启用
	  * 0-启用
	  * 1-禁用
	  * */
	@NotNull(message = "启用状态不能为空！", groups = Add.class)
	private String enable;
}