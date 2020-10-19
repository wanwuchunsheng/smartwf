package com.smartwf.sm.modules.sysconfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.smartwf.sm.modules.sysconfig.dao.WeatherConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;
import com.smartwf.sm.modules.sysconfig.service.WeatherConfigService;
/**
 * @Description: 天气配置
 */
public class WeatherConfigServiceImpl implements WeatherConfigService{
	
	@Autowired
	private WeatherConfigDao weatherConfigDao;

	/**
     * @Description: 添加天气配置
     */
	@Override
	public void saveWeatherConfig(WeatherConfig bean) {
		this.weatherConfigDao.insert(bean);
	}

}
