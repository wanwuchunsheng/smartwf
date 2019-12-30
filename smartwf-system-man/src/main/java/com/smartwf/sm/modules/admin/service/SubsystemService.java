package com.smartwf.sm.modules.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Resouce;
import com.smartwf.sm.modules.admin.vo.ResouceVO;

/**
 * @Description: 子系统业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface SubsystemService {

	/**
	 * @Description: 查询子系统分页
	 * @result: 
	 */
	Result<?> selectSubsystemByPage(Page<Resouce> page, ResouceVO bean);

	/**
     * @Description: 主键查询子系统
     * @return
     */
	Result<?> selectSubsystemById(Resouce bean);

	/**
     * @Description： 添加子系统
     * @return
     */
	void saveSubsystem(Resouce bean);
	
	/**
     * @Description： 修改子系统
     * @return
     */
	void updateSubsystem(Resouce bean);

	/**
     * @Description： 删除子系统
     * @return
     */
	void deleteSubsystem(ResouceVO bean);
	
	
	

}
