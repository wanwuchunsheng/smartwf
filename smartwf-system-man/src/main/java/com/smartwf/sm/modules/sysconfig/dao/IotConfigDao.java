package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.IotConfig;
import com.smartwf.sm.modules.sysconfig.vo.IotConfigVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 久层接口
 * @author WCH
 */
@Repository
public interface IotConfigDao extends BaseMapper<IotConfig> {

	/**
	 * @Description: 查询设备物联配置分页
	 * @param bean
	 * @return
	 */
	List<IotConfig> selectIotConfigByPage(Page<IotConfig> page, @Param("bean") IotConfigVO bean);

	/**
     * @Description: 主键查询设备物联配置
     * @param bean
     * @return
     */
	IotConfigVO selectIotConfigById(@Param("bean") IotConfig bean);

	/**
     * @Description： 删除设备物联配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteIotConfigByIds(@Param("list") List<String> list);

	/**
	 * @Description: 查询全部设备物联配置
	 * @param bean
	 * @return
	 */
	List<IotConfig> selectIotConfigByAll(@Param("bean") IotConfigVO bean);

	

    
}
