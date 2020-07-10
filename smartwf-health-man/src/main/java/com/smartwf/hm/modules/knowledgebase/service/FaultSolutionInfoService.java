package com.smartwf.hm.modules.knowledgebase.service;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultSolutionInfo;
/**
 * @author WCH
 * 
 * */
public interface FaultSolutionInfoService {
	/**
	 * @Description: 故障解决方案批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	void saveFaultSolutionInfo(FaultSolutionInfo bean);

	/**
     * @Description： 故障解决方案删除
     * @param faultCode
     * @return
     */
	void deleteFaultSolutionInfo(FaultSolutionInfo bean);

	/**
     * @Description： 故障代码库(主键)删除
     *   主键删除
     * @return
     */
	void deleteFaultSolutionInfoById(FaultCodeBase bean);

	/**
     * @Description：故障解决方案(故障代码)查询
     * @author WCH
     * @datetime 2020-5-14 15:38:29
     * @return
     */
	Result<?> selectFaultSolInfoByFaultCode(FaultSolutionInfo bean);

}
