package com.smartwf.hm.modules.knowledgebase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartwf.common.utils.JsonUtil;
import com.smartwf.hm.modules.knowledgebase.dao.FaultSolutionInfoDao;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultSolutionInfo;
import com.smartwf.hm.modules.knowledgebase.service.FaultSolutionInfoService;

import lombok.extern.log4j.Log4j2;
/**
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
	@Transactional
	@Override
	public void saveFaultSolutionInfo(FaultSolutionInfo bean) {
		//将json字符串强制装换成对象
				List<FaultSolutionInfo> fslist=JsonUtil.jsonToList(bean.getRemark(), FaultSolutionInfo.class);
				if(!fslist.isEmpty()) {
					for(FaultSolutionInfo fb :fslist) {
						faultSolutionInfoDao.insert(fb);
					}
				}
	}

}
