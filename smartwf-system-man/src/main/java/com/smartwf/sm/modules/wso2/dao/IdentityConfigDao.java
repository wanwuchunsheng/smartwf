package com.smartwf.sm.modules.wso2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.wso2.pojo.IdentityConfig;
/**
 * 
 * @author WCH
 * 
 * */
@Repository
public interface IdentityConfigDao  extends BaseMapper<IdentityConfig>{

	/**
	 * 
	 * 查询wso2 初始化数据
	 * @return
	 * */
	List<IdentityConfig> selectIdentityConfig();

}
