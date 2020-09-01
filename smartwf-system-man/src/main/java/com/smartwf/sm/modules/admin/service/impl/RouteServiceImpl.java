package com.smartwf.sm.modules.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.dao.PortalPowerGenDao;
import com.smartwf.sm.modules.admin.dao.RouteDao;
import com.smartwf.sm.modules.admin.pojo.PortalPowerGeneration;
import com.smartwf.sm.modules.admin.pojo.Route;
import com.smartwf.sm.modules.admin.service.RouteService;

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
	
	@Autowired
	private PortalPowerGenDao portalPowerGenDao;
	
	/**
     * 门户菜单查询
     * @return
     */
	@Override
	public Result<?> selectRouteByAll() {
		List<Route> list=this.routeDao.selectList(null);
		return Result.data(Constants.EQU_SUCCESS,list);
	}

	
	/**
     * 门户发电量统计数据  - 删除
     * @author WCH
     * @param bean
     * @return
     */
	@Override
	public void deletePortalPowerGenById(PortalPowerGeneration bean) {
		this.portalPowerGenDao.deleteById(bean);
	}

	/**
     * 门户发电量统计数据 -列表
     * @author WCH
     * @param bean
     * @param page
     * @return
     * 
     */
	@Override
	public Result<?> selectPortalPowerGenByAll(Page<PortalPowerGeneration> page, PortalPowerGeneration bean) {
		QueryWrapper<PortalPowerGeneration> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("create_time");
  		//租户
  		if (null!=bean.getTenantId() ) { 
  			queryWrapper.eq("tenant_id", bean.getTenantId()); 
  		}
        //租户域
        if (!StringUtils.isEmpty(bean.getTenantDomain())) {
        	queryWrapper.eq("tenant_domain", bean.getTenantDomain()); 
        }
        //风场
  		if (null!=bean.getWindFarm()) { 
  			queryWrapper.eq("wind_farm", bean.getWindFarm()); 
  		}
        //时间
        if (bean.getStartTime() != null && bean.getEndTime() != null) {
        	queryWrapper.between("create_time", bean.getStartTime(), bean.getEndTime());
        }
		IPage<PortalPowerGeneration> list=this.portalPowerGenDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	
	
	
	
}
