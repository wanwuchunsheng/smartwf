package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @Description: 用户职务表
 * @author WCH
 */
@Data
@TableName("sys_user_post")
public class UserPost implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
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
