package com.smartwf.sm.modules.admin.vo;

import java.util.Date;
import java.util.List;

import com.smartwf.sm.modules.admin.pojo.Organization;

import lombok.Data;
@Data
public class OrganizationVO extends Organization {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids; //id批量删除
	
	private Date startTime;//开始时间
	
	private Date endTime;//结束时间
	
	private String label;//组织架构名称，为前端方便，扩展字段 
	
	private List<OrganizationVO> children;//封装树形结构

}
