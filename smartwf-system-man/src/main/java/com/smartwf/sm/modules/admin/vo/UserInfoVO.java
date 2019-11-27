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

}
