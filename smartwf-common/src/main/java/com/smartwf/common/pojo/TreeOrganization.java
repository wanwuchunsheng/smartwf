package com.smartwf.common.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description: 组织架构
 */
@Data
public class TreeOrganization implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	
	private Integer id;
   
    /**
	 * 父级id
	 */
	private Integer pid;
	/**
	 * 上级id
	 */
	private Integer uid;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 机构编码
	 */
	private String orgCode;
	/**
	 * 机构名称
	 */
	private String orgName;
	/**
	 * 机构类型
	 */
	private Integer orgType;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 是否有效
       0有效  
       1无效   
	 */
	private Integer enable;
	/**
	 * 层次级别
	 */
	private Integer level;
}
