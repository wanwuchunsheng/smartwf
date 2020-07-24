package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @Description: 重点机位表
 * @author WCH
 */
@Data
@TableName("key_position")
public class KeyPosition implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
	
	/**
	 * 设备编码
	 */
	private String deviceCode;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 资产编码
	 */
	private String assetNumber;
	/**
	 * 租户域
	 */
	private String tenantDomain;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人id
	 */
	private Integer createUserId;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	
}
