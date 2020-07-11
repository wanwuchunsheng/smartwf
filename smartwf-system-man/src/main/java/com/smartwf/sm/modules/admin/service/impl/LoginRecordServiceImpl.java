package com.smartwf.sm.modules.admin.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.dao.LoginRecordDao;
import com.smartwf.sm.modules.admin.pojo.LoginRecord;
import com.smartwf.sm.modules.admin.service.LoginRecordService;
import com.smartwf.sm.modules.admin.vo.LoginRecordVO;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 登录记录业务层实现
 * @author WCH
 */
@Service
public class LoginRecordServiceImpl implements LoginRecordService {
	
	@Autowired
	private LoginRecordDao loginRecordDao;

	@Override
	public Result<?> selectLoginRecordByPage(Page<LoginRecord> page,LoginRecordVO bean) {
		QueryWrapper<LoginRecord> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("create_time"); 
        //租户ID
        if (null !=bean.getTenantId()) {
        	queryWrapper.like("tenant_id", Constants.PER_CENT + bean.getTenantId() + Constants.PER_CENT);
        }
        //创建时间
        if (bean.getStartTime() != null && bean.getEndTime() != null) {
        	queryWrapper.between("create_time", bean.getStartTime(), bean.getEndTime());
        }
        //登录时间
        if(bean.getLoginTime() !=null){
        	queryWrapper.eq("login_time", bean.getLoginTime());
        }
        //登录账号
        if (!StringUtils.isEmpty(bean.getLoginCode())) {
        	queryWrapper.eq("login_code", bean.getLoginCode());
        }
        //登录类型
        if (!StringUtils.isEmpty(bean.getLoginType())) {
        	queryWrapper.eq("login_type", bean.getLoginType());
        }
        //ip地址
        if (!StringUtils.isEmpty(bean.getIpAddress())) {
        	queryWrapper.eq("ip_iddress", bean.getIpAddress());
        }
        //设备名称
        if (!StringUtils.isEmpty(bean.getDeviceName())) {
        	queryWrapper.eq("device_name", bean.getDeviceName());
        }
        //状态
        if (null != bean.getStatus()) {
        	queryWrapper.eq("status", bean.getStatus());
        }
		IPage<LoginRecord> list=this.loginRecordDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
	 * @Description: 保存登录记录
	 * @author WCH
	 * @return
	 */
	@Override
	public void addLoginRecordByPage(LoginRecord bean) {
		this.loginRecordDao.insert(bean);
	}
   
}
