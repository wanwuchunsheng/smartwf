package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.TenantConfig;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 多租户配置持久层接口
 */
@Repository
public interface TenantConfigDao extends BaseMapper<TenantConfig> {

	/**
	 * @Description: 批量删除租户
	 * @result:
	 */
	void deleteTenantConfigByIds(@Param("list") List<String> list);

	

	
	

	

    
}
