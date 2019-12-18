package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.Resouce;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResouceVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 资源持久层接口
 */
@Repository
public interface ResouceDao extends BaseMapper<Resouce> {

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
	 * @Description: 查询删除模块下的所有子模块
	 * @result:
	 */
	List<Resouce> selectResouceById(@Param("bean") Resouce bean);
	/**
	 * @Description: 资源与用户操作查询
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	List<ResouceVO> selectResouceUserActByPage(@Param("bean") PermissionVO bean);

}
