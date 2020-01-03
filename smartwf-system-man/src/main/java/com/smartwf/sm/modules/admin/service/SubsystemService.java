package com.smartwf.sm.modules.admin.service;

import java.util.List;

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
	 * @Description: 查询子系统分页
	 * @result: 
	 */
	Result<?> selectSubsystemByPage(Page<Resource> page, ResourceVO bean);

	/**
     * @Description: 主键查询子系统
     * @return
     */
	Result<?> selectSubsystemById(Resource bean);

	/**
     * @Description： 添加子系统
     * @return
     */
	void saveSubsystem(Resource bean);
	
	/**
     * @Description： 修改子系统
     * @return
     */
	void updateSubsystem(Resource bean);

	/**
     * @Description： 删除子系统
     * @return
     */
	void deleteSubsystem(ResourceVO bean);
	
	
	

}
