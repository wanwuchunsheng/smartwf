package com.smartwf.hm.modules.alarmstatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.hm.modules.alarmstatistics.dao.FaultOverviewDao;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 报警收件箱业务层实现
 */
@Service
public class AlarmInboxServiceImpl implements AlarmInboxService {
	
	@Autowired
	FaultOverviewDao faultOverviewDao;
	

}
