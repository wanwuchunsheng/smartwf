package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smartwf.common.dto.LogDTO;
import com.smartwf.sm.modules.admin.pojo.Log;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 用户资源持久层接口
 */
@Repository
public interface UserDao extends Mapper<Log> {


    /**
     * 保存日志
     * @param logDTOList
     * @return
     */
    Integer saveLog(@Param("saveLog") List<LogDTO> logDTOList);
}
