package com.smartwf.hm.modules.knowledgebase.service;

import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;

public interface FaultCodeBaseService {

	/**
	 * @Description: 故障代码批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	void saveFaultCodeBase(FaultCodeBase bean);

}
