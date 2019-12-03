package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Description: 用户组织架构表
 */
@Data
@Table(name = "sys_user_organization")
public class UserOrganization implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
	private Integer id;
   
    /**
	 * 用户资源id
	 */
	private Integer userId;
	/**
	 * 组织机构表id
	 */
	private Integer organizationId;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	
}
