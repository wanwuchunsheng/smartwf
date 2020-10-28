package com.smartwf.sm.modules.sysconfig.service;

import java.util.List;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;

public interface WeatherConfigService {

	/**
     * @Description: 添加天气配置
     */
	void saveWeatherConfig(WeatherConfig bean);

	
	List<WeatherConfig> initWeatherDatas();

	/**
     * @Description: 查询天气配置
     * @return
     */
	Result<?> selectWeatherConfig(WeatherConfig bean);

}
