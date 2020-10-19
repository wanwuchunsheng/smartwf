package com.smartwf.sm.modules.sysconfig.dao;


import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.sysconfig.pojo.SysCity;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 多租户配置持久层接口
 * @author WCH
 */
@Repository
public interface CityDao extends BaseMapper<SysCity> {

	

    
}
