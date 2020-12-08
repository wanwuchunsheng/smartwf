package com.smartwf.sm.modules.admin.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.smartwf.sm.modules.admin.pojo.Organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * @author WCH
 * 
 * */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class OrganizationVO extends Organization {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * id批量删除
	 * */
	private String ids; 
	/**
	 * 
	 * 开始时间
	 * */
	private Date startTime;
	/**
	 * 结束时间
	 * 
	 * */
	private Date endTime;
	/**
	 * 组织架构名称，为前端方便，扩展字段 
	 * 
	 * */
	private String label;
	/**
	 * 用户ID
	 * 
	 * */
	private Integer userId;
	
	private List<Map<String,Object>> lmap;
	
	/**
	 * 封装树形结构
	 * 
	 * */
	private List<OrganizationVO> children;

}
