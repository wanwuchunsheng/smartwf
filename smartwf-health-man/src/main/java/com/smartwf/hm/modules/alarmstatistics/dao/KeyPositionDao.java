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

	/**
	 * 重点机位查询编码
	 * @author WCH
 	 * @param keyPost
 	 * @date 2020-04-07
 	 * @return
 	 */
	KeyPosition selectByDeviceCode(@Param("bean") KeyPosition keyPost);
	
	/**
	 * 重点机位已添加机位数据查询接口
	 * @author WCH
 	 * @author wch
 	 * @param bean
 	 * @date 2020-04-07
 	 * @return
 	 */
	List<KeyPosition> selectKeyPosition(@Param("bean") KeyPosition bean);
}
