package com.smartwf.sm.modules.admin.dao;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.LoginRecord;

/**
 * 登录记录表
 * 
 * @author wch
 * @date 2020-04-21 14:43:59
 */
@Repository
public interface LoginRecordDao extends BaseMapper<LoginRecord> {
	
}
