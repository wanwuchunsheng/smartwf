package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smartwf.sm.modules.admin.pojo.Resouce;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 资源持久层接口
 */
@Repository
public interface ResouceDao extends Mapper<Resouce> {

	/**
	 * @Description: 批量删除资源
	 * @result:
	 */
	void deleteResouceByIds(@Param("list") List<String> list);
	/**
	 * @Description: 初始化资源
	 * @result:
	 */
	List<Resouce> queryResouceAll();
	/**
	 * @Description: 删除子系统下所有资源
	 * @result:
	 */
	void deleteResouceByfid(@Param("bean") Resouce bean);
	/**
	 * @Description: 删除模块下所有资源
	 * @result:
	 */
	void selectResouceByfidLevel(@Param("bean") Resouce res);

}
