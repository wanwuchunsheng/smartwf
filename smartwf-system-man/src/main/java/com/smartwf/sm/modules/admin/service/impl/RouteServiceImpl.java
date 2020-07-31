package com.smartwf.sm.modules.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.dao.RouteDao;
import com.smartwf.sm.modules.admin.pojo.Route;
import com.smartwf.sm.modules.admin.service.RouteService;

import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j;
/**
 * @Description: 门户业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class RouteServiceImpl implements RouteService{
	
	@Autowired
	private RouteDao routeDao;
	
	/**
     * 门户菜单查询
     * @return
     */
	@Override
	public Result<?> selectRouteByAll() {
		List<Route> list=this.routeDao.selectList(null);
		return Result.data(Constants.EQU_SUCCESS,list);
	}
	
	
}
