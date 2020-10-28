package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.RedisService;
import com.smartwf.sm.modules.sysconfig.dao.WeatherConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;
import com.smartwf.sm.modules.sysconfig.service.WeatherConfigService;

import cn.hutool.json.JSONUtil;
/**
 * @Description: 天气配置
 */
@Service
public class WeatherConfigServiceImpl implements WeatherConfigService{
	
	@Autowired
	private WeatherConfigDao weatherConfigDao;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private WeatherConfigService weatherConfigService;

	/**
     * @Description: 添加天气配置
     */
	@Override
	public void saveWeatherConfig(WeatherConfig bean) {
		//添加前清空数据
		this.weatherConfigDao.deleteWeatherConfig();
		bean.setCreateTime(new Date());
		//0-查询当天天气  3-查询3天天气
		bean.setApiType(0); 
		this.weatherConfigDao.insert(bean);
		//添加完成，刷新天气缓存
    	this.redisService.set("initWeatherConfig", JSONUtil.toJsonStr(this.weatherConfigService.initWeatherDatas()));
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

	/**
     * @Description: 查询天气配置
     * @return
     */
	@Override
	public Result<?> selectWeatherConfig(WeatherConfig bean) {
		WeatherConfig wc=this.weatherConfigDao.selectOne(new QueryWrapper<>());
		return Result.data(Constants.EQU_SUCCESS, wc);
	}

}
