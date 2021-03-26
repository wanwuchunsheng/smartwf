package com.smartwf.hm.modules.healthstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
/**
 * 风机机型表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-26 09:27:33
 */
@Setter
@Getter
@TableName("pms_asset_model")
public class AssetModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 资产编码
	 */
	private String deviceCode;
	/**
	 * 资产名称
	 */
	private String deviceName;
	/**
	 * KKS编码
	 */
	private String kksId;
	/**
	 * 主键编码 型号库主键编码
	 */
	private String modelId;
	/**
	 * 所属仓库(FK)
	 */
	private String wareroomId;
	/**
	 * 供应商
	 */
	private String provider;
	/**
	 * 制造商
	 */
	private String manufacturer;
	/**
	 * 资产状态
	 */
	private Integer status;
	/**
	 * 额定功率
	 */
	private String ratedPower;
	/**
	 * 层级
	 */
	private String level;
	/**
	 * 是否重点设备或部件
	 */
	private Integer isImportant;
	/**
	 * 资产类型 1测风塔
            2电计量表
            3光伏
            4气象站
            5机组
            6升压站
            7箱变
            8建筑物
            9集成线路
	 */
	private String assetsType;
	/**
	 * 运行状态 0正常
            1故障
            2缺陷
            3告警
	 */
	private Integer runStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 风场
	 */
	private String windFarm;
	/**
	 * 租户域
	 */
	private String tenantDomain;

}
