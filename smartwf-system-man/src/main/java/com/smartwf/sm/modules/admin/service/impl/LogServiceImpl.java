package com.smartwf.sm.modules.admin.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.smartwf.common.dto.LogDTO;
import com.smartwf.common.pojo.PageVO;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.LogDao;
import com.smartwf.sm.modules.admin.pojo.Log;
import com.smartwf.sm.modules.admin.service.LogService;

import tk.mybatis.mapper.entity.Example;

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
    public Result selectLogByPage(PageVO page) {
    	com.github.pagehelper.Page<Object> objectPage = PageHelper.startPage(page.getPage(), page.getLimit());
        Example example = new Example(Log.class);
        //Example.Criteria criteria = example.createCriteria();
        List<Log> logs = this.logDao.selectByExample(example);
        return Result.data(objectPage.getTotal(), logs);
    }


    /**
     * @Description: 主键查询操作日志
     * @return
     */
	@Override
	public Result<?> selectLogById(Log bean) {
		Log log= this.logDao.selectByPrimaryKey(bean);
		return Result.data(log);
	}

	/**
     * @Description： 删除操作日志
     * @param id 单个删除
     * @return
     */
	@Override
	public void deleteLog(Log bean) {
		this.logDao.deleteByPrimaryKey(bean);
	}
}
