package com.smartwf.sm.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.LoginRecord;
import com.smartwf.sm.modules.admin.vo.LoginRecordVO;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 登录记录业务层接口
 */
public interface LoginRecordService {

	/**
	 * @Description: 查询所有所有登录记录
	 * @return
	 */
	Result<?> selectLoginRecordByPage(Page<LoginRecord> page,LoginRecordVO bean);

	/**
	 * @Description: 保存登录记录
	 * @author WCH
	 * @return
	 */
	void addLoginRecordByPage(LoginRecord bean);


}
