package com.smartwf.hm.modules.knowledgebase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultSolutionInfo;

@Repository
public interface FaultSolutionInfoDao extends BaseMapper<FaultSolutionInfo> {

	/**
     * @Description： 故障解决方案删除
     * @param faultCode
     * @return
     */
	void deleteFaultSolutionInfo(@Param("bean") FaultSolutionInfo bean);

	/**
     * @Description：故障解决方案(故障代码)查询
     * @author WCH
     * @datetime 2020-5-14 15:38:29
     * @return
     */
	List<FaultSolutionInfo> selectFaultSolInfoByFaultCode(@Param("bean") FaultSolutionInfo bean);

}
