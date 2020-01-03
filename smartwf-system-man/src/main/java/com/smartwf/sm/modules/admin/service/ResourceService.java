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
	 * @Description: 查询资源分页
	 * @result: 
	 */
	Result<?> selectResourceByPage(Page<Resource> page, ResourceVO bean);

	/**
     * @Description: 主键查询资源
     * @return
     */
	Result<?> selectResourceById(Resource bean);

	/**
     * @Description： 添加资源
     * @return
     */
	void saveResource(Resource bean);
	
	/**
     * @Description： 修改资源
     * @return
     */
	void updateResource(Resource bean);

	/**
     * @Description： 删除资源
     * @return
     */
	void deleteResource(ResourceVO bean);
	
	/**
     * @Description： 初始化资源
     * @return
     */
	List<Resource> queryResourceAll();
	/**
	 * @Description: 查询资源子系统
	 * @return
	 */
	Result<?> selectResourceByPid(ResourceVO bean);
	/**
	 * @Description: 查询所有资源，树形结构
	 * @return
	 */
	Result<?> selectResourceByAll(ResourceVO bean);
	

}
