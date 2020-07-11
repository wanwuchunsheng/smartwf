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
public interface UiSettingService {

	/**
	 * 查询前端配置分页
	 * @param page
	 * @param bean
	 * @return 
	 */
	Result<?> selectUiSettingByPage(Page<SysConfig> page, SysConfigVO bean);

	/**
     * 主键查询前端配置
     * @param bean
     * @return
     */
	Result<?> selectUiSettingById(SysConfig bean);

	/**
     * 添加前端配置
     * @param bean
     */
	void saveUiSetting(SysConfig bean);
	
	/**
     *  修改前端配置
     * @param bean
     */
	void updateUiSetting(SysConfig bean);

	/**
     * 删除前端配置
     * @param bean
     */
	void deleteUiSetting(SysConfigVO bean);
	
	
	

}
