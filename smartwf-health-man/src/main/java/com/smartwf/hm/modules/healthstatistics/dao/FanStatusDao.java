package com.smartwf.hm.modules.healthstatistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.hm.modules.healthstatistics.pojo.FanStatus;
import com.smartwf.hm.modules.healthstatistics.vo.FanStatusVO;

/**
 * @author WCH
 * @Date: 2019-10-25 16:05:30
 * @Description: 持久层接口
 * Mapper接口
 *      基于Mybatis:  在Mapper接口中编写CRUD相关的方法  提供Mapper接口所对应的SQL映射文件 以及 方法对应的SQL语句.
 *      基于MP:  让XxxMapper接口继承 BaseMapper接口即可.
 * 		   BaseMapper<T> : 泛型指定的就是当前Mapper接口所操作的实体类类型
 */
@Repository
public interface FanStatusDao extends BaseMapper<FanStatus> {

	/**
	 * @Description: 统计风机运行状态
	 *    统计结果信息
	 * @param tenantDomain
	 * @param windFarm
	 * @author WCH
	 * @Datetime 2021-3-26 10:26:07
	 * @return
	 */
	List<FanStatusVO> selectFanRunStatusByCount(@Param("bean") FanStatus bean);

	/**
	 * @Description: 资产状态泳状图统计
	 *    泳状图
	 * @param tenantDomain
	 * @param windFarm
	 * @author WCH
	 * @Datetime 2021-3-26 10:26:07
	 * @return
	 */
	List<Map<String, Object>> selectAssetModelByCount(@Param("bean") FanStatus bean);

}
