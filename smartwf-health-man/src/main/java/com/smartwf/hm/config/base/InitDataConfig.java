package com.smartwf.hm.config.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.service.SecurityIncidentsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author WCH
 * @Date: 2019-11-29 10:29:44
 * @Description: 初始化基础数据
 */
@Component
@Slf4j
public class InitDataConfig implements CommandLineRunner{
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	@Autowired
	private DefectService defectService;
	@Autowired
    private SecurityIncidentsService securityIncidentsService;

	/**
	 * @Description: 初始化未处理的故障/缺陷
	 *    保证redis最新的数据和mysql一致
	 */
    public void initDataListener() {
    	try {
    		this.alarmInboxService.selectFaultInformationByAll();
    		this.defectService.initDefectAll();
    		//初始化安全生产多少天
    		this.securityIncidentsService.initSecurityIncidentsService();
		} catch (Exception e) {
			log.error("错误：初始化基础数据异常{}",e);
		}
    }

    @Override
    public void run(String... args) throws Exception {
        this.initDataListener();
    }
    


}
