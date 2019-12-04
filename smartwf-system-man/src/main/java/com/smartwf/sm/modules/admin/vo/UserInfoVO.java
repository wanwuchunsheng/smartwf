package com.smartwf.sm.modules.admin.vo;

import com.smartwf.sm.modules.admin.pojo.UserInfo;

import lombok.Data;
@Data
public class UserInfoVO extends UserInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids; //id批量删除
	
	private String organizationIds; //组织架构ids
	
	private String postIds; // 职务id
	
	private String roleIds; // 角色id

}
