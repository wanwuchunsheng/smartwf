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
	 *  故障解决方案批量录入
	 * @author WCH
	 * @param bean
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	void saveFaultSolutionInfo(FaultSolutionInfo bean);

	/**
     *  故障解决方案删除
     * @param faultCode
     * @param bean
     * @return
     */
	void deleteFaultSolutionInfo(FaultSolutionInfo bean);

	/**
     * 故障代码库(主键)删除
     *   主键删除
     * @param bean
     * @return
     */
	void deleteFaultSolutionInfoById(FaultCodeBase bean);

	/**
     * 故障解决方案(故障代码)查询
     * @author WCH
     * @param bean
     * @Datetime 2020-5-14 15:38:29
     * @return
     */
	Result<?> selectFaultSolInfoByFaultCode(FaultSolutionInfo bean);

}
