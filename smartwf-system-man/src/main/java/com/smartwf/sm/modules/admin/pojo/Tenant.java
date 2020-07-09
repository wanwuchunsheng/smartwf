package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 租户表
 */
@Data
@TableName("sys_tenant")
public class Tenant extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "id不能为空", groups = Update.class)
	@NotNull(message = "id不能为空", groups = Query.class)
	private Integer id;
   
    /**
	 * 租户代码
	 */
	@NotNull(message = "租户名称不能为空！", groups = Add.class)
	private String tenantCode;
	
    /**
	 * wso2默认租户租户域
	 */
	@NotNull(message = "租户名称不能为空！", groups = Add.class)
	private String tenantDomain;
	
	/**
	 * wso2默认租户管理员密码
	 */
	@NotNull(message = "租户名称不能为空！", groups = Add.class)
	private String tenantPw;
	/**
	 * 租户名称
	 */
	@NotNull(message = "租户名称不能为空！", groups = Add.class)
	private String tenantName;
	/**
	 * 默认选中
	 *   0-默认
	 *   1-选中
	 */
	@NotNull(message = "默认租户不能为空！", groups = Add.class)
	private Integer sel; 
	/**
	 * 是否有效
     * 0有效  
     * 1无效   
	 */
	@NotNull(message = "启用状态不能为空！", groups = Add.class)
	private Integer enable;
	/**
	 * 备注
	 */
	private String remark;
	
}
