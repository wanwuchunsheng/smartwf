package com.smartwf.sm.modules.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.vo.ResourceVO;

/**
 * @Description: 资源业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface ResourceService {

	/**
	 * 查询资源分页
	 * @param page
	 * @param bean
	 * @return
	 */
	Result<?> selectResourceByPage(Page<Resource> page, ResourceVO bean);

	/**
     * 主键查询资源
     * @param bean
     * @return
     */
	Result<?> selectResourceById(Resource bean);

	/**
     *  添加资源
     * @param bean
     */
	void saveResource(Resource bean);
	
	/**
     * 修改资源
     * @param bean
     */
	void updateResource(Resource bean);

	/**
     * 删除资源
     * @param bean
     */
	void deleteResource(ResourceVO bean);
	
	/**
     *  初始化资源
     * @return
     */
	List<Resource> queryResourceAll();
	/**
	 * 查询资源子系统
	 * @param bean
	 * @return
	 */
	Result<?> selectResourceByPid(ResourceVO bean);
	/**
	 * 查询所有资源，树形结构
	 * @param bean
	 * @return
	 */
	Result<?> selectResourceByAll(ResourceVO bean);
	

}
