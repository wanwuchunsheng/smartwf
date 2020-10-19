package com.smartwf.sm.modules.sysconfig.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 风场配置表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-10-19 10:24:50
 */
@Data
@TableName("basic_windfarm_config")
public class WindfarmConfig implements Serializable {
	
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
	 * 租户域
	 */
	private String tenantDomain;
	/**
	 * 风场
	 */
	private Integer windFarm;
	/**
	 * 场站标题
	 */
	private String windFarmTitle;
	/**
	 * kks编码
	 */
	private String kks;
	/**
	 * 实时容量
	 */
	private Double realTimeCapacity;
	/**
	 * 装机容量
	 */
	private Double installedCapacity;
	/**
	 * 日发电量
	 */
	private Double dailyGeneration;
	/**
	 * 累计发电量
	 */
	private Double cumulativeGeneration;
	/**
	 * 机组
	 */
	private Integer generatingSet;
	/**
	 * 发电
	 */
	private Integer powerGeneration;
	/**
	 * 待机
	 */
	private Integer standBy;
	/**
	 * 故障
	 */
	private Integer malfunctions;
	/**
	 * 维护
	 */
	private Integer maintenance;
	/**
	 * 限电
	 */
	private Integer powerRestriction;
	/**
	 * 离线
	 */
	private Integer offLine;
	/**
	 * 状态（是否启用人工更新数据） 0否  1是
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
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
	

}
