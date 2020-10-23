package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.RouteConfig;
import com.smartwf.sm.modules.sysconfig.vo.RouteConfigVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 久层接口
 * @author WCH
 */
@Repository
public interface RouteConfigDao extends BaseMapper<RouteConfig> {

	/**
	 * @Description: 查询设备物联配置分页
	 * @param bean
	 * @return
	 */
	List<RouteConfig> selectRouteConfigByPage(Page<RouteConfig> page, @Param("bean") RouteConfigVO bean);

	/**
     * @Description: 主键查询设备物联配置
     * @param bean
     * @return
     */
	RouteConfigVO selectRouteConfigById(@Param("bean") RouteConfig bean);

	/**
     * @Description： 删除设备物联配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteRouteConfigByIds(@Param("list") List<String> list);

	

    
}
