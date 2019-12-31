package com.smartwf.hm.modules.alarmstatistics.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.dto.LogDTO;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.admin.pojo.Log;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 日志业务层接口
 */
public interface LogService {


    /**
     * 保存日志
     * @param logDTOList
     * @return
     */
    Integer saveLog(List<LogDTO> logDTOList);


    /**
     * 分页查询日志
     * @param page
     * @param queryPojo
     * @return
     */
    Result selectLogByPage(Page<Log> page);

    /**
     * @Description: 主键查询操作日志
     * @return
     */
	Result<?> selectLogById(Log bean);

	/**
     * @Description： 删除操作日志
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteLog(Log bean);
}
