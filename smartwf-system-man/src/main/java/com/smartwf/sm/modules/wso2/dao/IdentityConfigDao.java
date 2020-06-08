package com.smartwf.sm.modules.wso2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.wso2.pojo.IdentityConfig;

@Repository
public interface IdentityConfigDao  extends BaseMapper<IdentityConfig>{

	List<IdentityConfig> selectIdentityConfig();

}
