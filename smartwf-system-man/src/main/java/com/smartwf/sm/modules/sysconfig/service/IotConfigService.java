package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.IotConfig;
import com.smartwf.sm.modules.sysconfig.vo.IotConfigVO;

/**
 * @Description: 设备物联配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface IotConfigService {

	/**
	 * @Description: 查询设备物联配置分页
	 * @param bean
	 * @return
	 */
	Result<?> selectIotConfigByPage(Page<IotConfig> page, IotConfigVO bean);

	/**
     * @Description: 主键查询设备物联配置
     * @param bean
     * @return
     */
	Result<?> selectIotConfigById(IotConfig bean);

	/**
     * @Description: 添加设备物联配置
     * @param bean
     * @return
     */
	void saveIotConfig(IotConfig bean);

	/**
     * @Description： 修改设备物联配置
     * @param bean
     * @return
     */
	void updateIotConfig(IotConfig bean);

	/**
     * @Description： 删除设备物联配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteIotConfig(IotConfigVO bean);

	
	
	

}
