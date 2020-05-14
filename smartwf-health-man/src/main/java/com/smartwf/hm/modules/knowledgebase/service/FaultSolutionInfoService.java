package com.smartwf.hm.modules.knowledgebase.service;

import com.smartwf.hm.modules.knowledgebase.pojo.FaultSolutionInfo;

public interface FaultSolutionInfoService {
	/**
	 * @Description: 故障解决方案批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	void saveFaultSolutionInfo(FaultSolutionInfo bean);

}
