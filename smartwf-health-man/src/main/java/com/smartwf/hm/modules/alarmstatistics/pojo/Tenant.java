package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 租户表
 * @author WCH
 */
@Data
@TableName("sys_tenant")
public class Tenant extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
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
	 * 默认选中
	 *   0-默认
	 *   1-选中
	 */
	private Integer sel; 
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
