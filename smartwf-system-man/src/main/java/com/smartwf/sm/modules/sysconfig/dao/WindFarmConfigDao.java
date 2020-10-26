package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;
import com.smartwf.sm.modules.sysconfig.vo.WindfarmConfigVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 风场配置持久层接口
 * @author WCH
 */
@Repository
public interface WindFarmConfigDao extends BaseMapper<WindfarmConfig> {

	/**
	 * 批量删除租户
	 * @param list
	 * @result:
	 */
	void deleteWindFarmConfigByIds(@Param("list") List<String> list);

	/**
	 * 风场配置查询
	 * @param page ,bean
	 * @result
	 */
	List<WindfarmConfigVO> selectWindFarmConfigByPage(Page<WindfarmConfig> page,@Param("bean") WindfarmConfigVO bean);

	
	/**
     * 门户发电量统计数据 -发电量统计
     * @author WCH
     * @param bean
     * @return
     * 
     */
	WindfarmConfigVO selectPortalPowerGenByParam(@Param("bean") WindfarmConfig bean);

	/**
     * 门户状态统计 - 设备状态
     * @author WCH
     * @param bean
     * @return
     * 
     */
	List<WindfarmConfig> selectPortalStatusByParam(@Param("bean") WindfarmConfig bean);

	/**
	 * 门户天气 - 租户天气
	 * @param page,bean
	 * @result
	 */
	List<WindfarmConfigVO> selectWindfarmConfig(@Param("bean") TenantConfig bean);

	

	
	

	

    
}
