package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.dao.AreaDao;
import com.smartwf.sm.modules.sysconfig.dao.CityDao;
import com.smartwf.sm.modules.sysconfig.dao.ProvinceDao;
import com.smartwf.sm.modules.sysconfig.pojo.SysArea;
import com.smartwf.sm.modules.sysconfig.pojo.SysCity;
import com.smartwf.sm.modules.sysconfig.pojo.SysProvince;
import com.smartwf.sm.modules.sysconfig.service.ProCityService;

@Service
public class ProCityServiceImpl implements ProCityService{

	@Autowired
	private ProvinceDao provinceDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private AreaDao areaDao;
	
	/**
	 * @Description: 省查询 -通过省id查询全部
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectProAll(SysProvince bean) {
		QueryWrapper<SysProvince> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(bean.getProCode())) {
			queryWrapper.eq("pro_code", bean.getProCode());
		}
		queryWrapper.orderByAsc("pro_code");
		List<SysProvince> list=this.provinceDao.selectList(queryWrapper);
		return Result.data(list.size(),list);
	}

	/**
	 * @Description: 市查询 -通过省id或者市ID查询
	 * @param proId , cityId
	 * @return
	 */
	@Override
	public Result<?> selectCityByProId(String proCode, String cityCode) {
		QueryWrapper<SysCity> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(proCode)) {
			queryWrapper.eq("province_code",proCode);
		}
		if(StringUtils.isNotEmpty(cityCode)) {
			queryWrapper.eq("code",cityCode);
		}
		queryWrapper.orderByAsc("code");
		List<SysCity> list=this.cityDao.selectList(queryWrapper);
		return Result.data(list.size(),list);
	}

	/**
	 * @Description: 县/区查询 -通过市Id，省ID
	 * @param proId , cityId
	 * @return
	 */
	@Override
	public Result<?> selectAreaByCityId( String cityCode) {
		QueryWrapper<SysArea> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(cityCode)) {
			queryWrapper.eq("city_code",cityCode);
		}
		queryWrapper.orderByAsc("code");
		List<SysArea> list=this.areaDao.selectList(queryWrapper);
		return Result.data(list.size(),list);
	}

}
