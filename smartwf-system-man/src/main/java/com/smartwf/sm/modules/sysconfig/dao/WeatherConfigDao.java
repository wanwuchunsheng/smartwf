package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 风场配置持久层接口
 * @author WCH
 */
@Repository
public interface WeatherConfigDao extends BaseMapper<WeatherConfig> {

	/**
     * @Description: 初始化天气配置
     * @return
     */
	List<WeatherConfig> initWeatherDatas();
	/**
     * @Description: 情况表数据
     */
	void deleteWeatherConfig();

	

    
}
