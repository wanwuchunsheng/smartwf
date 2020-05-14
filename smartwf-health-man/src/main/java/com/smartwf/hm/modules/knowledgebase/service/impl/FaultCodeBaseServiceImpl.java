package com.smartwf.hm.modules.knowledgebase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartwf.common.utils.JsonUtil;
import com.smartwf.hm.modules.knowledgebase.dao.FaultCodeBaseDao;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.service.FaultCodeBaseService;

import lombok.extern.log4j.Log4j2;
/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 故障代码业务层实现
 */
@Service
@Log4j2
public class FaultCodeBaseServiceImpl implements FaultCodeBaseService{
	
	@Autowired
	private FaultCodeBaseDao faultCodeBaseDao;
	
	/**
	 * @Description: 故障代码批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	@Transactional
	@Override
	public void saveFaultCodeBase(FaultCodeBase bean) {
		//将json字符串强制装换成对象
		List<FaultCodeBase> fcblist=JsonUtil.jsonToList(bean.getRemark(), FaultCodeBase.class);
		if(!fcblist.isEmpty()) {
			for(FaultCodeBase fb :fcblist) {
				faultCodeBaseDao.insert(fb);
			}
		}
	}

}
