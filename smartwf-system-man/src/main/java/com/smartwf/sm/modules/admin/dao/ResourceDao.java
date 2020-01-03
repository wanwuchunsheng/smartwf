package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResourceVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 资源持久层接口
 */
@Repository
public interface ResourceDao extends BaseMapper<Resource> {

	/**
	 * @Description: 批量删除资源
	 * @result:
	 */
	void deleteResourceByIds(@Param("list") List<String> list);
	/**
	 * @Description: 初始化资源
	 * @result:
	 */
	List<Resource> queryResourceAll();
	/**
	 * @Description: 删除子系统下所有资源
	 * @result:
	 */
	void deleteResourceByfid(@Param("bean") Resource bean);
	/**
	 * @Description: 查询模块下的所有子模块
	 * @result:
	 */
	List<Resource> selectResourceById(@Param("bean") Resource bean);
	/**
	 * @Description: 资源与用户操作查询
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	List<ResourceVO> selectResourceUserActByPage(@Param("bean") PermissionVO bean);
	
	/**
	 * @Description: 删除模块下所有资源
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	void deleteResourceById(@Param("bean") Resource bean);
	/**
	 * @Description: 查询资源子系统
	 * @return
	 */
	List<Resource> selectResourceByPid(@Param("bean") ResourceVO bean);
	/**
	 * @Description: 查询所有资源，树形结构
	 * @return
	 */
	List<ResourceVO> selectResourceByAll(@Param("bean") ResourceVO bean);
	/**
     * @Description: 主键查询资源
     * @return
     */
	ResourceVO selecResourcetById(@Param("bean") Resource bean);
	

}
