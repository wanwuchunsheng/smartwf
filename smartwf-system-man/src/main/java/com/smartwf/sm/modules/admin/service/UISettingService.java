package com.smartwf.sm.modules.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.SysConfig;
import com.smartwf.sm.modules.admin.vo.SysConfigVO;

/**
 * @Description: 前端配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface UISettingService {

	/**
	 * @Description: 查询前端配置分页
	 * @result: 
	 */
	Result<?> selectUISettingByPage(Page<SysConfig> page, SysConfigVO bean);

	/**
     * @Description: 主键查询前端配置
     * @return
     */
	Result<?> selectUISettingById(SysConfig bean);

	/**
     * @Description： 添加前端配置
     * @return
     */
	void saveUISetting(SysConfig bean);
	
	/**
     * @Description： 修改前端配置
     * @return
     */
	void updateUISetting(SysConfig bean);

	/**
     * @Description： 删除前端配置
     * @return
     */
	void deleteUISetting(SysConfigVO bean);
	
	
	

}
