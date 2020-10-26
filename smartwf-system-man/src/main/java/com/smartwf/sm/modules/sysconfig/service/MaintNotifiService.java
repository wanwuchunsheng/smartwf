package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.MaintNotification;
import com.smartwf.sm.modules.sysconfig.vo.MaintNotifiVO;

/**
 * @Description: 设备系统维护业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface MaintNotifiService {

	/**
	 * @Description: 查询设备系统维护分页
	 * @param bean
	 * @return
	 */
	Result<?> selectMaintNotifiByPage(Page<MaintNotification> page, MaintNotifiVO bean);

	/**
     * @Description: 主键查询设备系统维护
     * @param bean
     * @return
     */
	Result<?> selectMaintNotifiById(MaintNotification bean);

	/**
     * @Description: 添加设备系统维护
     * @param bean
     * @return
     */
	Result<?> saveMaintNotifi(MaintNotification bean);

	/**
     * @Description： 修改设备系统维护
     * @param bean
     * @return
     */
	Result<?> updateMaintNotifi(MaintNotification bean);

	/**
     * @Description： 删除设备系统维护
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteMaintNotifi(MaintNotifiVO bean);

	
	
	

}
