package com.smartwf.sm.modules.admin.service;

import com.smartwf.sm.modules.admin.pojo.ExceptionLog;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 异常日志业务层接口
 * @author WCH
 */
public interface ExceptionLogService {

	/**
	 * 插入日志信息
	 * @Date: 2018/12/18 15:43
	 * @param excepLog
	 * 
	 */
	void insert(ExceptionLog excepLog);
	


}
