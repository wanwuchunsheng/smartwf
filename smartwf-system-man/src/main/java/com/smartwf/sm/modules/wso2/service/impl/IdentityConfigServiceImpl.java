package com.smartwf.sm.modules.wso2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.sm.modules.wso2.dao.IdentityConfigDao;
import com.smartwf.sm.modules.wso2.pojo.IdentityConfig;
import com.smartwf.sm.modules.wso2.service.IdentityConfigService;

@Service
public class IdentityConfigServiceImpl implements IdentityConfigService{
	
	@Autowired
	private IdentityConfigDao identityConfigDao;

	@Override
	public List<IdentityConfig> initIdentityConfig() {
		return identityConfigDao.selectIdentityConfig();
	}

}
