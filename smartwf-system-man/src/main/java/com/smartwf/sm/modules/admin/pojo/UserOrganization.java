package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @Description: 用户组织架构表
 */
@Data
@TableName("sys_user_organization")
public class UserOrganization implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
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
