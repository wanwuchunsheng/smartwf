package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Description: 用户职务表
 */
@Data
@Table(name = "sys_user_post")
public class UserPost implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
	private Integer id;
   
    /**
	 * 用户资源表id
	 */
	private Integer userId;
	/**
	 * 职务表id
	 */
	private Integer postId;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	
}
