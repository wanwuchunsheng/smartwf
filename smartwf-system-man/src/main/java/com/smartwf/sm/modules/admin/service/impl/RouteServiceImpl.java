package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.handler.UserProfile;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.admin.dao.RouteDao;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.dao.UserOrganizationDao;
import com.smartwf.sm.modules.admin.pojo.Route;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.service.RouteService;
import com.smartwf.sm.modules.sysconfig.dao.TenantConfigDao;
import com.smartwf.sm.modules.sysconfig.dao.WindFarmConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;
import com.smartwf.sm.modules.sysconfig.vo.WindfarmConfigVO;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
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
	
	@Autowired
	private TenantConfigDao tenantConfigDao;
	
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
		WindfarmConfigVO ppg=this.windFarmConfigDao.selectPortalPowerGenByParam(bean);
		//构建返回数据
		Map<String,Map<String,Object>> maplist= new HashMap<>();
		Map<String,Object> map=null;
		//日发电量
		map=new HashMap<>();
		map.put("value", ppg.getDailyGeneration());
		map.put("unit", "MW·h");
		maplist.put("dailyGeneration", map);
		//月发电量
		map=new HashMap<>();
		map.put("value", ppg.getMonthGeneration());
		map.put("unit", "MW·h");
		maplist.put("monthGeneration", map);
		//累计发电量
		map=new HashMap<>();
		map.put("value", ppg.getCumulativeGeneration());
		map.put("unit", "MW·h");
		maplist.put("cumulativeGeneration", map);
		//实时容量
		map=new HashMap<>();
		map.put("value", ppg.getRealTimeCapacity());
		map.put("unit", "MW");
		maplist.put("realTimeCapacity", map);
		//装机容量
		map=new HashMap<>();
		map.put("value", ppg.getInstalledCapacity());
		map.put("unit", "MW");
		maplist.put("installedCapacity", map);
		//可利用率
		map=new HashMap<>();
		map.put("value", ppg.getAvailability());
		map.put("unit", "%");
		maplist.put("availability", map);
		//等效利用小时
		map=new HashMap<>();
		map.put("value", ppg.getEquivalentUtilHours());
		map.put("unit", "H");
		maplist.put("equivalentUtilHours", map);
		//返回
		return Result.data(Constants.EQU_SUCCESS,maplist);
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
					try {
						String res=this.redisService.get(s);
						//验证accessToken是否存在，唯一
						boolean accessToken=res.contains("\"accessToken\":");
						if(accessToken) {
							//存在accessToken，验证是否属于当前租户
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
					} catch (Exception e) {
						// TODO: handle exception
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
					//验证accessToken是否存在，唯一
					try {
						String redisVlaue=this.redisService.get(s);
						boolean accessToken=redisVlaue.contains("\"accessToken\":");
						if(accessToken) {
							//存在accessToken，验证是否属于当前租户
							boolean str=redisVlaue.contains("\"tenantDomain\":\""+bean.getTenantDomain()+"\",");
							if(str) {
								online ++;
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			map.put("online", online);
		}
		return Result.data(Constants.EQU_SUCCESS,JSONUtil.toJsonStr(map));
	}

	/**
     * 门户天气查询
     *    查询租户下所有风场天气
     * @author WCH
     * @param bean
     * @return
     * 
     */
	@Override
	public Result<?> selectPortalWeatherByParam(TenantConfig bean) {
		Map<String,Object> map=null;
		List<Map<String,Object>> listmap=new ArrayList<>();
		log.info(JSONUtil.toJsonStr(bean));
		//租户天气
		TenantConfigVO tcf= this.tenantConfigDao.selectTenantConfig(bean);
		String locationWf=StrUtil.str(new StringBuilder().append(tcf.getLongitude()).append(",").append(tcf.getLatitude()));
		String res=this.redisService.get(locationWf);
		if(StrUtil.isNotBlank(res)) {
			//直接将值返回
			listmap.add(JSONUtil.parseObj(res));
		}else {
			map=new HashMap<>();
			map.put("proName", tcf.getProName());
			map.put("cityName", tcf.getCityName());
			map.put("areaName", tcf.getAreaName());
			map.put("windfarmId", "");
			map.put("windfarmName", "");
			listmap.add(requestWeather(locationWf, "0",map));
		}
		//查询各风场天气
		List<WindfarmConfigVO> wtlist=this.windFarmConfigDao.selectWindfarmConfig(bean);
		for(WindfarmConfigVO wt:wtlist) {
			String locationWt=StrUtil.str(new StringBuilder().append(wt.getLongitude()).append(",").append(wt.getLatitude()));
			String resWt=this.redisService.get(locationWt);
			if(StrUtil.isNotBlank(resWt)) {
				//直接将值返回
				listmap.add(JSONUtil.parseObj(resWt));
			}else {
				map=new HashMap<>();
				map.put("proName", wt.getProName());
				map.put("cityName", wt.getCityName());
				map.put("areaName", wt.getAreaName());
				map.put("windfarmName", wt.getWindFarmTitle()==null?"":wt.getWindFarmTitle());
				map.put("windfarmId", wt.getWindFarm());
				listmap.add(requestWeather(locationWt, "1",map));
			}
		}
		//查询租户下的所有风场信息
		return Result.data(Constants.EQU_SUCCESS,listmap);
	}

	
	/**
	 * 说明：根据经纬度，查询天气
	 * @param location 经纬度
	 * @param type 0-租户域 天气  1-风场天气
	 * 
	 * */
	public Map<String,Object> requestWeather(String location,String type,Map<String,Object> map){
		//不存在，重新获取 请求url和key
		List<WeatherConfig> wcfglist=JSONUtil.toList(JSONUtil.parseArray(this.redisService.get("initWeatherConfig")), WeatherConfig.class);
		for(WeatherConfig wc:wcfglist) {
			if(0==wc.getApiType()) {
				map.put("location", location);
				map.put("key", wc.getApiKey());
				//发送http get请求
				String resJson=HttpRequest.get(wc.getApiUrl()).form(map).timeout(60000).execute().body();
				Map<String,Object> resmap=JSONUtil.parseObj(resJson);
				map.put("weather", resmap.get("now")==null?"":resmap.get("now"));
				map.put("type", type);
			    //保存redis，设置时间1小时
				this.redisService.set(location,JSONUtil.toJsonStr(map), 1800);
				return map;
			}
		}
		return null;
	}

	/**
     * 门户-省份风场统计
     * @author WCH
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectWindfarmConfigByProCode(WindfarmConfig bean) {
		User user=UserThreadLocal.getUser();
		List<Map<String,Object>> mapRes=new ArrayList<>();
		List<Map<String,Object>> mapWfList=null;
		Map<String,Object> mapPor=null;
		Map<String,Object> mapWf=null;
		//分组统计省份风场数
		List<Map<String,Object>> maplist= null;
		List<WindfarmConfigVO> windfarmList=null;
		//验证是否平台管理员
		if(CkUtils.verifyAdminUser(user)) {
			maplist=this.windFarmConfigDao.selectProWindfarmByAdmin(bean);
			windfarmList=this.windFarmConfigDao.selectWindfarmConfigByAdmin(bean);
		}else {
			maplist=this.windFarmConfigDao.selectProWindfarm(bean,user);
			windfarmList=this.windFarmConfigDao.selectWindfarmConfigByProCode(bean,user);
		}
		//规范数据返回
		for(Map<String,Object> m:maplist) {
			mapWfList=new ArrayList<>();
			mapPor= new HashMap<>();
			mapPor.put("proName", m.get("proName"));
			mapPor.put("proCode", m.get("proCode"));
			mapPor.put("proLongitude", m.get("proLongitude"));
			mapPor.put("proLatitude", m.get("proLatitude"));
			mapPor.put("windFarmCount", m.get("windFarmCount"));
			String proCode=Convert.toStr(m.get("proCode"));
			for(WindfarmConfigVO wv:windfarmList) {
				if(proCode.equals(wv.getProCode())) {
					mapWf= new HashMap<>();
					mapWf.put("windFarmTitle", wv.getWindFarmTitle());
					mapWf.put("windFarm", wv.getWindFarm());
					mapWf.put("installedCapacity", wv.getInstalledCapacity()+" MW");
					mapWf.put("dailyGeneration", wv.getDailyGeneration()+" MW.h");
					mapWfList.add(mapWf);
				}
			}
			mapPor.put("windFarms", mapWfList);
			mapRes.add(mapPor);
		}
		log.info(JSONUtil.toJsonStr(mapRes));
		return Result.data(Constants.EQU_SUCCESS,mapRes);
	}
	
	
	
}
