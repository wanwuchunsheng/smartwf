package com.smartwf.hm.modules.alarmstatistics.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;



@Repository
public interface KeyPositionDao extends BaseMapper<KeyPosition> {

	KeyPosition selectByDeviceCode(@Param("bean") KeyPosition keyPost);
	
}
