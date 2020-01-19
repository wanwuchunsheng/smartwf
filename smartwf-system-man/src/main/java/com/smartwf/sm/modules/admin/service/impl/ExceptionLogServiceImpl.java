package com.smartwf.sm.modules.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.sm.modules.admin.dao.ExceptionLogDao;
import com.smartwf.sm.modules.admin.pojo.ExceptionLog;
import com.smartwf.sm.modules.admin.service.ExceptionLogService;
/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 异常日志业务层实现
 */
@Service
public class ExceptionLogServiceImpl implements ExceptionLogService{

	@Autowired
	private ExceptionLogDao exceptionLogDao;
	
	/**
	 * 保存异常日志记录
	 * 
	 * */
	@Override
	public void insert(ExceptionLog excepLog) {
		this.exceptionLogDao.insert(excepLog);
	}

}
