package com.smartwf.hm.modules.knowledgebase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause.Entry;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.knowledgebase.dao.FaultSolutionInfoDao;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultSolutionInfo;
import com.smartwf.hm.modules.knowledgebase.service.FaultSolutionInfoService;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;
/**
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 * @Description: 故障解决方案业务层实现
 */
@Service
@Log4j2
public class FaultSolutionInfoServiceImpl implements FaultSolutionInfoService{
	
	@Autowired
	private FaultSolutionInfoDao faultSolutionInfoDao;
	
	
	/**
	 * @Description: 故障解决方案批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveFaultSolutionInfo(FaultSolutionInfo bean) {
		log.info(JSONUtil.toJsonStr(bean.getRemark()));
		//将json字符串强制装换成对象
		Map<String, Object> map=JSONUtil.parseObj(bean.getRemark());
		if(map!=null && map.size()>0) {
			FaultSolutionInfo fsi=null;
			for (Map.Entry<String, Object> m : map.entrySet()) {
				fsi= new FaultSolutionInfo();
				fsi.setFaultCode(bean.getFaultCode());
				fsi.setType(Convert.toInt(m.getKey()));
				//先删除
				this.faultSolutionInfoDao.deleteFaultSolutionInfo(fsi);
				//后添加
				fsi.setCreateTime(new Date());
				fsi.setEnable(Constants.ZERO);
				fsi.setStatus(Constants.ZERO);
				fsi.setSort(Constants.ZERO);
				fsi.setContent(Convert.toStr(m.getValue()));
			    this.faultSolutionInfoDao.insert(fsi);
			}
		}
	}

	/**
     * @Description： 故障解决方案删除
     * @param faultCode
     * @return
     */
	@Override
	public void deleteFaultSolutionInfo(FaultSolutionInfo bean) {
		this.faultSolutionInfoDao.deleteFaultSolutionInfo(bean);
	}

	/**
     * @Description： 故障代码库(主键)删除
     *   主键删除
     * @return
     */
	@Override
	public void deleteFaultSolutionInfoById(FaultCodeBase bean) {
		this.faultSolutionInfoDao.deleteById(bean);
	}

	
	/**
     * @Description：故障解决方案(故障代码)查询
     * @author WCH
     * @datetime 2020-5-14 15:38:29
     * @return
     */
	@Override
	public Result<?> selectFaultSolInfoByFaultCode(FaultSolutionInfo bean) {
		List<FaultSolutionInfo> list=this.faultSolutionInfoDao.selectFaultSolInfoByFaultCode(bean);
		return Result.data(Constants.EQU_SUCCESS,list);
	}

}
