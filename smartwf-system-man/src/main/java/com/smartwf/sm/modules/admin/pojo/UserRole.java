package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Description: 用户角色表
 */
@Data
@Table(name = "sys_user_role")
public class UserRole implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
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
