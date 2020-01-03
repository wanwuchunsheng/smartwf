package com.smartwf.sm.modules.admin.vo;

import java.util.Date;
import java.util.List;

import com.smartwf.sm.modules.admin.pojo.Resource;

import lombok.Data;
@Data
public class ResourceVO extends Resource{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids; //id批量删除
	
	private Date startTime;//开始时间
	
	private Date endTime;//结束时间
	
	private List<ResourceVO> children;//封装树形结构

}
