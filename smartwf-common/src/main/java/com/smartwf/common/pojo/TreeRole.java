package com.smartwf.common.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description: 角色
 */
@Data
public class TreeRole implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	private Integer id;
   
    /**
	 * 角色编号
	 */
	private String roleCode;
	/**
	 * 角色名称
	 */
	private String roleName;
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
	 * 排序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String remark;
	
}
