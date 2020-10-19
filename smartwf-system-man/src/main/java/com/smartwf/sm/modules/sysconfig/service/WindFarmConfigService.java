package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;
import com.smartwf.sm.modules.sysconfig.vo.WindfarmConfigVO;

/**
 * @Description: 风场配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface WindFarmConfigService {

	/**
	 * 查询风场配置分页
	 * @param page
	 * @param bean
	 * @return
	 */
	Result<?> selectWindFarmConfigByPage(Page<WindfarmConfig> page, WindfarmConfigVO bean);

	/**
     * 主键查询风场配置
     * @param bean
     * @return
     */
	Result<?> selectWindFarmConfigById(WindfarmConfig bean);

	/**
     * 添加风场配置
     * @param bean
     * 
     */
	void saveWindFarmConfig(WindfarmConfig bean);
	
	/**
     *  修改风场配置
     *  @param bean
     * 
     */
	void updateWindFarmConfig(WindfarmConfig bean);

	/**
     *  删除风场配置
     * @param bean
     */
	void deleteWindFarmConfig(WindfarmConfigVO bean);

	

	


	
	
	
	

}
