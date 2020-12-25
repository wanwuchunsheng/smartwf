package com.smartwf.hm.modules.admin.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.service.RedisService;
import com.smartwf.hm.modules.admin.service.IotRealTimeDataService;
import com.smartwf.hm.modules.alarmstatistics.dao.AlarmInboxDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 说明：底层实时数据接入
 * @author WCH
 * */
@Service
@Slf4j
public class IotRealTimeDataServiceImpl implements IotRealTimeDataService{
	
	@Autowired
	private AlarmInboxDao alarmInboxDao;
	
	@Autowired
	private RedisService redisService;

	/**
	 * 说明：底层实时数据接入
	 * @param fault_type
	 *        0-系统故障  1-监控警告 2-人工提交 3-预警警告
	 * @author WCH
	 * */
	@Override
	public void saveIotRealTimeData(String value) {
		try {
			if(StrUtil.isNotEmpty(value)) {
				Map<String,Object> map=JSONUtil.parseObj(value);
				//problemType  0-系统故障  1-监控警告  2-人工提交  3-预警警告
				if(map.containsKey("problemType")) {
					for(Entry<String, Object> m:map.entrySet()) {
						System.out.println(m.getKey()+"     "+m.getValue());
					}
					FaultInformation fit=null;
					if(Constants.ONE==Convert.toInt(map.get("problemType"))) {
						//告警调用生产中心补全数据
						Thread.sleep(1500);
					}else {
						//预警直接入库
						fit=new FaultInformation();
						fit.setAlarmCode("");
						fit.setAlarmDescription("");
						fit.setAlarmLevel(1);
						fit.setAlarmLocation("");
						fit.setAlarmSource("");
						fit.setAlarmStatus(0);
						fit.setAssetNumber("");
						fit.setCreateTime(new Date());
						fit.setDeviceCode("");
						fit.setDeviceName("");
						fit.setDiscovererId("");
						fit.setDiscovererName("");
					    this.alarmInboxDao.insert(fit);
					}
				}
			}
		} catch (Exception e) {
			log.error("底层实时数据接入异常！{}-{}",e,e.getMessage());
		}
	}

}
