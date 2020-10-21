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
@TableName("basic_weather_config")
public class WeatherConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * api url
	 */
	private String apiUrl;
	/**
	 * api key
	 */
	private String apiKey;
	
	/**
	 * API接口类型
	 * 0-实时
	 * 3-3天
	 * 7-七天
	 */
	private Integer apiType;
	

	private Date createTime;
}
