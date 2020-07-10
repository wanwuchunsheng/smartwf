package com.smartwf.hm.modules.knowledgebase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;

/**
 * @Deprecated 故障基础代码数据层
 * @author WCH
 * */

@Repository
public interface FaultCodeBaseDao extends BaseMapper<FaultCodeBase> {
	/**
     * @Description： 查询要删除的故障代码
     * @author WCH
     * @return
     */
	List<FaultCodeBase> selectFaultCodeBase(@Param("bean") FaultCodeBase bean);
	/**
     * @Description： 删除故障代码
     * @author WCH
     * @return
     */
	void deleteFaultCodeBase(@Param("bean") FaultCodeBase bean);
	/**
     * @Description： 删除故障代码对应的解决方案
     *    级联删除
     * @author WCH
     * @return
     */
	void deleteFaultSolutionInfo(@Param("bean") FaultCodeBase fcb);

}
