package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 系统用户表
 */
@Data
@Table(name = "sys_tenant")
public class Tenant extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	/**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
	private Integer id;
   
    /**
	 * 租户代码
	 */
	private String tenantCode;
	/**
	 * 租户名称
	 */
	private String tenantName;
	/**
	 * 是否有效
            0有效  
            1无效
            
	 */
	private Integer enable;
	/**
	 * 备注
	 */
	private String remark;
	
}
