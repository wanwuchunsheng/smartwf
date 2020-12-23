package com.smartwf.hm.modules.admin.service.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.smartwf.hm.modules.admin.service.IotRealTimeDataService;

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
				for(Entry<String, Object> m:map.entrySet()) {
					System.out.println(m.getKey()+"     "+m.getValue());
				}
			}
		} catch (Exception e) {
			log.error("底层实时数据接入异常！{}-{}",e,e.getMessage());
		}
	}

}
