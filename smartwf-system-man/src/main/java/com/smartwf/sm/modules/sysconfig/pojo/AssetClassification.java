package com.smartwf.sm.modules.sysconfig.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 资产分类配置表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-10-22 20:42:37
 */
@Data
@TableName("basic_asset_classification")
public class AssetClassification implements Serializable {
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
	 * 租户域名
	 */
	private String tenantDomain;
	/**
	 * 风场id
	 */
	private Integer windFarm;
	/**
	 * 资产分类名称
	 */
	private String classifyName;
	/**
	 * svg图片
	 */
	private String svg;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
