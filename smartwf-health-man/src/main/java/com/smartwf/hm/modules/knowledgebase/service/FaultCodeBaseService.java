package com.smartwf.hm.modules.knowledgebase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.vo.FaultCodeBaseVO;
/**
 * @author WCH
 * 
 * */
public interface FaultCodeBaseService {

	/**
	 *  故障代码批量录入
	 * @author WCH
	 * @param bean
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	void saveFaultCodeBase(FaultCodeBase bean);

	/**
     *  删除故障代码库
     *   id删除
     * @param bean
     * @return
     */
	void deleteFaultCodeBase(FaultCodeBase bean);

	/**
     *  故障代码库主键删除
     *   主键删除
     * @param bean
     * @return
     */
	void deleteFaultCodeBaseById(FaultCodeBase bean);
	
	/**
     *  故障代码库(主键)查询
     *   主键查询
     * @param bean
     * @return
     */
	Result<?> selectFaultCodeBaseById(FaultCodeBase bean);

	/**
     *  故障代码库分页查询
     * @author WCH
     * @param bean
     * @param page
     * @Datetime 2020-5-14 15:55:37
     * @return
     */
	Result<?> selectFaultCodeBaseByPage(Page<FaultCodeBase> page, FaultCodeBaseVO bean);

}
