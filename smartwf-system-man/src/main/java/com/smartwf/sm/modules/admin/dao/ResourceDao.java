package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.common.pojo.TreeResource;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResourceVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 资源持久层接口
 * @author WCH
 */
@Repository
public interface ResourceDao extends BaseMapper<Resource> {

	/**
	 * 批量删除资源
	 * @param list
	 * @result:
	 */
	void deleteResourceByIds(@Param("list") List<String> list);
	/**
	 * 初始化资源
	 * @return
	 */
	List<Resource> queryResourceAll();
	/**
	 * 删除子系统下所有资源
	 * @param bean
	 * @return
	 */
	void deleteResourceByfid(@Param("bean") Resource bean);
	/**
	 * 查询模块下的所有子模块
	 * @param bean
	 * @return
	 */
	List<Resource> selectResourceById(@Param("bean") Resource bean);
	/**
	 * 资源与用户操作查询
	 * @param bean
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	List<ResourceVO> selectResourceUserActByPage(@Param("bean") PermissionVO bean);
	
	/**
	 * 删除模块下所有资源
	 * @param bean
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	void deleteResourceById(@Param("bean") Resource bean);
	/**
	 * 查询资源子系统
	 * @param bean
	 * @return
	 */
	List<Resource> selectResourceByPid(@Param("bean") ResourceVO bean);
	/**
	 * 查询所有资源，树形结构
	 * @param bean
	 * @return
	 */
	List<ResourceVO> selectResourceByAll(@Param("bean") ResourceVO bean);
	/**
     * 主键查询资源
     * @param bean
     * @return
     */
	ResourceVO selecResourcetById(@Param("bean") Resource bean);
	/**
     * 通过用户ID查询资源
     * @param userInfo
     * @return
     */
	List<TreeResource> selectResourceByUserId(@Param("bean") User userInfo);
	

}
