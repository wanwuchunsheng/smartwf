package com.smartwf.sm.modules.admin.vo;

import java.util.Date;

import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.pojo.SysConfig;

import lombok.Data;
@Data
public class SysConfigVO extends SysConfig{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids; //id批量删除
	
	private Date startTime;//开始时间
	
	private Date endTime;//结束时间

}
