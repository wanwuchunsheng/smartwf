package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.hm.config.redis.RestConfig;
import com.smartwf.hm.modules.alarmstatistics.dao.AlarmInboxDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 报警收件箱业务层实现
 */
@Service
public class AlarmInboxServiceImpl implements AlarmInboxService {
	
	@Autowired
	private AlarmInboxDao alarmInboxDao;
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	
	/**
	 * @Description: 分页查询故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectAlarmInforByPage( Page<FaultInformation> page,FaultInformationVO bean) {
		List<FaultInformationVO> list=this.alarmInboxDao.selectAlarmInforByPage(page,bean);
		return Result.data(page.getTotal(), list);
	}
	
	
	/**
	 * @Description: 实时故障报警总数查询
	 * @return
	 */
	@Override
	public Integer selectAlarmsCountByAll() {
		Map<String, Object> maps=JsonUtil.jsonToMap(this.stringRedisTemplate.opsForValue().get("faultCount"));
		return maps.size();
	}
	
	/**
	 * @Description: 初始化未结束的故障
	 * @return
	 */
	@Override
	public void selectFaultInformationByAll() {
		//重置redis db
		RestConfig.setIndex(stringRedisTemplate, 1);
		//查询redis数据
		Map<String,FaultInformation> list = this.alarmInboxDao.selectFaultInformationByAll();
		//保存redis
		this.stringRedisTemplate.opsForValue().set("faultCount",JSON.toJSONString(list,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
