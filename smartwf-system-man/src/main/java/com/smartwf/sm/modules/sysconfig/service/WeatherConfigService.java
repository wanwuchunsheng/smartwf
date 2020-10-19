package com.smartwf.sm.modules.sysconfig.service;

import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;

public interface WeatherConfigService {

	/**
     * @Description: 添加天气配置
     */
	void saveWeatherConfig(WeatherConfig bean);

}
