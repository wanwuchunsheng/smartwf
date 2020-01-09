package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultDataDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.FaultDataService;


/**
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
		//1）保存mysql
		this.faultDataDao.insert(bean);
		//2）获取redis缓存数据,并更新缓存数据，保存
		Map<String, Object> maps=JsonUtil.jsonToMap(this.redisService.get("faultCount"));
		if(maps==null) {
			maps=new HashMap<>();
		}
		maps.put(bean.getId(), bean);
		//3）将新数据保存redis
		this.redisService.set("faultCount",JSON.toJSONString(maps,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
	}
	
}
