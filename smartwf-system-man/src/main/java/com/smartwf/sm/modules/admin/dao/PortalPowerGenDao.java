package com.smartwf.sm.modules.admin.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.PortalPowerGeneration;
import com.smartwf.sm.modules.admin.vo.PortalPowerGenerationVO;


/**
 * @Date: 2019-10-25 16:05:30
 * @Description: 日志持久层接口
 * Mapper接口
 *      基于Mybatis:  在Mapper接口中编写CRUD相关的方法  提供Mapper接口所对应的SQL映射文件 以及 方法对应的SQL语句.
 *      基于MP:  让XxxMapper接口继承 BaseMapper接口即可.
 * 		   BaseMapper<T> : 泛型指定的就是当前Mapper接口所操作的实体类类型
 * @author WCH
 */
@Repository
public interface PortalPowerGenDao extends BaseMapper<PortalPowerGeneration> {

	/**
     * 门户发电量统计数据 -发电量统计
     * @author WCH
     * @param bean
     * @return
     * 
     */
	PortalPowerGenerationVO selectPortalPowerGenByParam(@Param("bean") PortalPowerGenerationVO bean);

	PortalPowerGenerationVO selectPortalPowerGenByTotal(@Param("bean") PortalPowerGenerationVO bean);


}
