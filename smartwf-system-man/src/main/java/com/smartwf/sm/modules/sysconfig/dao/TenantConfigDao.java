package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 多租户配置持久层接口
 * @author WCH
 */
@Repository
public interface TenantConfigDao extends BaseMapper<TenantConfig> {

	/**
	 * 批量删除租户
	 * @param list
	 * @result:
	 */
	void deleteTenantConfigByIds(@Param("list") List<String> list);

	/**
	 * 分页查询租户配置信息
	 * @param page,bean
	 * @result
	 */
	List<TenantConfigVO> selectTenantConfigByParam(Page<TenantConfig> page,@Param("bean") TenantConfigVO bean);

	

	
	

	

    
}
