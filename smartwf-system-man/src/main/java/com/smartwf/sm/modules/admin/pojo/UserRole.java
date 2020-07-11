package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @Description: 用户角色表
 * @author WCH
 */
@Data
@TableName( "sys_user_role")
public class UserRole implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	private Integer id;
   
    /**
	 * 角色id
	 */
	private Integer roleId;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	
}
