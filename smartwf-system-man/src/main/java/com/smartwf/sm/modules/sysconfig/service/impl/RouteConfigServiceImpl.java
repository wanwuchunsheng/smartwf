package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.sysconfig.dao.RouteConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.RouteConfig;
import com.smartwf.sm.modules.sysconfig.service.RouteConfigService;
import com.smartwf.sm.modules.sysconfig.vo.RouteConfigVO;
/**
 * @Description:数据中心- 路由配置业务实现层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class RouteConfigServiceImpl implements RouteConfigService{
	
	@Autowired
	private RouteConfigDao routeConfigDao;

	/**
	 * @Description: 查询路由配置分页
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectRouteConfigByPage(Page<RouteConfig> page, RouteConfigVO bean) {
		List<RouteConfig> list=this.routeConfigDao.selectRouteConfigByPage(page,bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}

	/**
     * @Description: 主键查询路由配置
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectRouteConfigById(RouteConfig bean) {
		RouteConfig icfg=this.routeConfigDao.selectRouteConfigById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加路由配置
     * @param bean
     * @return
     */
	@Override
	public void saveRouteConfig(RouteConfig bean) {
		bean.setCreateTime(new Date());
		this.routeConfigDao.insert(bean);
	}

	/**
     * @Description： 修改路由配置
     * @param bean
     * @return
     */
	@Override
	public void updateRouteConfig(RouteConfig bean) {
		bean.setCreateTime(new Date());
		this.routeConfigDao.updateById(bean);
	}

	/**
     * @Description： 删除路由配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteRouteConfig(RouteConfigVO bean) {
		if( null!=bean.getId()) {
			//删除
			this.routeConfigDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				this.routeConfigDao.deleteRouteConfigByIds(list);
			}
		}
	}
	

}
