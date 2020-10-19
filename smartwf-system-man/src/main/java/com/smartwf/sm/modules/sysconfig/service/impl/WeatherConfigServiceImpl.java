package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.sm.modules.sysconfig.dao.WeatherConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;
import com.smartwf.sm.modules.sysconfig.service.WeatherConfigService;
/**
 * @Description: 天气配置
 */
@Service
public class WeatherConfigServiceImpl implements WeatherConfigService{
	
	@Autowired
	private WeatherConfigDao weatherConfigDao;

	/**
     * @Description: 添加天气配置
     */
	@Override
	public void saveWeatherConfig(WeatherConfig bean) {
		bean.setCreateTime(new Date());
		this.weatherConfigDao.insert(bean);
	}

	/**
     * @Description: 初始化天气配置
     * @return
     */
	@Override
	public List<WeatherConfig> initWeatherDatas() {
		List<WeatherConfig> list= this.weatherConfigDao.initWeatherDatas();
		return list;
	}

}
