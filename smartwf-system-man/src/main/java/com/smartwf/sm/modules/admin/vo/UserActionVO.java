package com.smartwf.sm.modules.admin.vo;

import java.util.Date;

import com.smartwf.sm.modules.admin.pojo.UserAction;

import lombok.Data;
@Data
public class UserActionVO extends UserAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids; //id批量删除
	
	private Integer mgrType;//0普通  1管理员  2超级管理员
	
	private Date startTime;//开始时间
	
	private Date endTime;//结束时间

}
