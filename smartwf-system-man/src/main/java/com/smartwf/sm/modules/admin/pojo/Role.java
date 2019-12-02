package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 角色表
 */
@Data
@Table(name = "sys_role")
public class Role extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
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
