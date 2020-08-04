package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.service.RedisService;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultDataDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.FaultDataService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;


/**
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 * @Description: 实时故障数据业务层实现
 */
@Service
public class FaultDataServiceImpl implements FaultDataService {
	
	@Autowired
	private FaultDataDao faultDataDao;
	
	@Autowired
    private RedisService redisService;
	
	/**
	 * @Description: 实时故障报警缺陷数据
	 *     故障、报警、缺陷实时数据
	 *     1)保存数据到mysql
	 *     2）保存数据到redis
	 *     保证两边数据同步
	 */
	@Override
	public void saveFaultInformation(FaultInformation bean) {
		bean.setCreateTime(new Date());
		bean.setUpdateTime(bean.getCreateTime());
		//未处理
		bean.setAlarmStatus(Constants.ZERO);
		//1故障类型 2缺陷类型
		bean.setIncidentType(Constants.ONE);
		//1）保存mysql
		this.faultDataDao.insert(bean);
		String tdDatas=this.redisService.get(DateUtil.today());
	    //今日更新数
		if(StringUtils.isNotBlank(tdDatas)) {
			this.redisService.set(DateUtil.today(), Convert.toStr(Convert.toInt(tdDatas)+1));
		}else {
			this.redisService.set(DateUtil.today(), Convert.toStr(1), 86400);
		}
	}
	
}
