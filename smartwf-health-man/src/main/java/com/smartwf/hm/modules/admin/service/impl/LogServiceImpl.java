package com.smartwf.hm.modules.admin.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.dto.LogDTO;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.admin.dao.LogDao;
import com.smartwf.hm.modules.admin.pojo.Log;
import com.smartwf.hm.modules.admin.service.LogService;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 日志业务层实现
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;
 

    /**
     * 保存日志
     * @param logDTOList
     * @return
     */
    @Transactional
    @Override
    public Integer saveLog(List<LogDTO> logDTOList) {
        return this.logDao.saveLog(logDTOList);
    }


    /**
     * 分页查询日志
     * @param page
     * @param queryPojo
     * @return
     */
    @Override
    public Result selectLogByPage(Page<Log> page) {
    	IPage<Log> logs = this.logDao.selectPage(page, null);
        return Result.data(logs.getTotal(), logs.getRecords());
    }


    /**
     * @Description: 主键查询操作日志
     * @return
     */
	@Override
	public Result<?> selectLogById(Log bean) {
		Log log= this.logDao.selectById(bean.getId());
		return Result.data(log);
	}

	/**
     * @Description： 删除操作日志
     * @param id 单个删除
     * @return
     */
	@Override
	public void deleteLog(Log bean) {
		this.logDao.deleteById(bean.getId());
	}
}
