package com.smartwf.sm.modules.sysconfig.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 租户配置表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-10-19 10:24:50
 */
@Data
@TableName("basic_tenant_config")
public class TenantConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 登录名
	 */
	private String loginCode;
	/**
	 * 登录密码
	 */
	private String loginPwd;
	/**
	 * 租户logo
	 */
	private String logoUrl;
	/**
	 * 地址
	 */
	private String path;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 省编码
	 */
	private String proCode;
	/**
	 * 市编码
	 */
	private String cityCode;
	/**
	 * 县/区编码
	 */
	private String areaCode;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * GeoJson 数据
	 */
	private String geoJson;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
