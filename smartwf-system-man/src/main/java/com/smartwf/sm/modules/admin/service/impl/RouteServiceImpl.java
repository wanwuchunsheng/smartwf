package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.common.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.sm.modules.admin.dao.RouteDao;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.dao.UserOrganizationDao;
import com.smartwf.sm.modules.admin.pojo.Route;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.service.RouteService;
import com.smartwf.sm.modules.sysconfig.dao.WindFarmConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;

import cn.hutool.core.util.StrUtil;
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
	
	@Autowired
	private WindFarmConfigDao windFarmConfigDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private UserOrganizationDao userOrganizationDao;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
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
     * 门户发电量统计数据 -发电量统计
     * @author WCH
     * @param bean
     * @return
     * 
     */
	@Override
	public Result<?> selectPortalPowerGenByParam(WindfarmConfig bean) {
		//查询日发电量，装机量，实时量
		WindfarmConfig ppg=this.windFarmConfigDao.selectPortalPowerGenByParam(bean);
		//返回
		return Result.data(Constants.EQU_SUCCESS,ppg);
	}

	/**
     * 门户状态统计 - 设备状态
     * @author WCH
     * @param bean
     * @return
     * 
     */
	@Override
	public Result<?> selectPortalStatusByParam(WindfarmConfig bean) {
		//查询日发电量，装机量，实时量
		List<WindfarmConfig> ppg=this.windFarmConfigDao.selectPortalStatusByParam(bean);
		//返回
		return Result.data(Constants.EQU_SUCCESS,ppg);
	}

	/**
     * 门户人员统计 -场站、风场
     * @author WCH
     * @param bean
     * @return
     * 
     */
	@Override
	public Result<?> selectPortalUserByParam(WindfarmConfig bean) {
		//默认场站，在线人数0
		Map<String,Integer> map=new HashMap<String, Integer>();
		//根据实际查询数据赋值
		if(null != bean.getWindFarm()) {
			//场站
			QueryWrapper<UserOrganization> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("tenant_id", bean.getTenantId());
			queryWrapper.eq("organization_id", bean.getWindFarm());
			int tenantNum= this.userOrganizationDao.selectCount(queryWrapper);
			map.put("tenant", tenantNum);
			//在线
            int online=0;
			Set<String> vallist= this.redisTemplate.keys("*");
			if(null !=vallist && vallist.size()>0) {
				//保存所有用户id
				List<Integer> list=new ArrayList<>();
				for(String s : vallist) {
					String res=this.redisService.get(s);
					boolean str=res.contains("\"tenantDomain\":\""+bean.getTenantDomain()+"\",");
					if(str) {
						try {
							User user=JSONUtil.toBean(res, User.class);
							list.add(user.getId());
						} catch (Exception e) {
							log.error("ERROR：风场在线人数统计异常！");
						}
					}
				}
				queryWrapper.in("user_id", list);
			}
			online= this.userOrganizationDao.selectCount(queryWrapper);
			map.put("online", online);
		}else {
			//场站
			QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("tenant_id", bean.getTenantId());
			int tenantNum= this.userInfoDao.selectCount(queryWrapper);
			map.put("tenant", tenantNum);
			//在线
            int online=0;
			Set<String> vallist= this.redisTemplate.keys("*");
			if(null !=vallist && vallist.size()>0) {
				for(String s : vallist) {
					boolean str=this.redisService.get(s).contains("\"tenantDomain\":\""+bean.getTenantDomain()+"\",");
					if(str) {
						online ++;
					}
				}
			}
			map.put("online", online);
		}
		return Result.data(Constants.EQU_SUCCESS,JSONUtil.toJsonStr(map));
	}

	
	
	
	
}
