package com.smartwf.sm.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.vo.ResourceVO;

/**
 * @Description: 子系统业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface SubsystemService {

	/**
	 * 查询子系统分页
	 * @param bean
	 * @param page
	 * @return
	 */
	Result<?> selectSubsystemByPage(Page<Resource> page, ResourceVO bean);

	/**
     * 主键查询子系统
     * @param bean
     * @return
     */
	Result<?> selectSubsystemById(Resource bean);

	/**
     * 添加子系统
     * @param bean
     */
	void saveSubsystem(Resource bean);
	
	/**
     *  修改子系统
     * @param bean
     */
	void updateSubsystem(Resource bean);

	/**
     *  删除子系统
     * @param bean
     */
	void deleteSubsystem(ResourceVO bean);
	
	
	

}
