package com.smartwf.hm.modules.alarmstatistics.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;


/**
 * @deprecated 重点机位
 * @author WCH
 * 
 * */
@Repository
public interface KeyPositionDao extends BaseMapper<KeyPosition> {

	KeyPosition selectByDeviceCode(@Param("bean") KeyPosition keyPost);
	
	/**
	 * @author WCH
 	 * @Description: 重点机位已添加机位数据查询接口
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	List<KeyPosition> selectKeyPosition(@Param("bean") KeyPosition bean);
}
