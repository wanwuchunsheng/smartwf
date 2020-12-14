package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.sysconfig.dao.TenantConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.service.TenantConfigService;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;

import lombok.extern.log4j.Log4j2;
/**
 * @Description: 租户业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j2
public class TenantConfigServiceImpl implements TenantConfigService{
	
	@Autowired
	private TenantConfigDao tenantConfigDao;

	/**
	 * @Description:查询多租户配置分页
	 * @result:
	 */
	@Override
	public Result<?> selectTenantConfigByPage(Page<TenantConfig> page, TenantConfigVO bean) {
		List<TenantConfigVO> list = this.tenantConfigDao.selectTenantConfigByParam(page,bean);
		return Result.data(page.getTotal(), list);
	}

	/**
     * @Description: 主键查询多租户配置
     * @return
     */
	@Override
	public Result<?> selectTenantConfigById(TenantConfig bean) {
		TenantConfig tenant= this.tenantConfigDao.selectById(bean);
		return Result.data(Constants.EQU_SUCCESS,tenant);
	}
	
	/**
     * @Description: 添加多租户配置
     * @return
     */
	@Override
	public void saveTenantConfig(TenantConfig bean) {
		bean.setCreateTime(new Date());
		//查询是否存在，
		QueryWrapper<TenantConfig> queryWrapper= new QueryWrapper<>();
		queryWrapper.eq("tenant_id", bean.getTenantId());
		TenantConfig tenant=this.tenantConfigDao.selectOne(queryWrapper);
		if(tenant!=null) {
			bean.setId(tenant.getId());
			this.tenantConfigDao.updateById(bean);
		}else {
			this.tenantConfigDao.insert(bean);
		}
	}

	/**
     * @Description： 修改多租户配置
     * @return
     */
	@Override
	public void updateTenantConfig(TenantConfig bean) {
		bean.setCreateTime(new Date());
		//修改
		this.tenantConfigDao.updateById(bean);
	}

	/**
     * @Description： 删除多租户配置
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteTenantConfig(TenantConfigVO bean) {
		if( null!=bean.getId()) {
			//删除多租户配置表
			this.tenantConfigDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//多租户配置表
				this.tenantConfigDao.deleteTenantConfigByIds(list);
			}
		}
	}

	/**
	 * 说明： 租户样式上传
	 *        （logo、样式json）
	 * @param tenantId
	 * @param remark  样式json
	 * @return
	 * 
	 * */
	@Override
	public Result<?> saveIndexStyleById(TenantConfig bean) {
		try {
			bean.setCreateTime(new Date());
			//查询是否存在，
			QueryWrapper<TenantConfig> queryWrapper= new QueryWrapper<>();
			queryWrapper.eq("tenant_id", bean.getTenantId());
			TenantConfig tenant=this.tenantConfigDao.selectOne(queryWrapper);
			if(tenant!=null) {
				bean.setId(tenant.getId());
				this.tenantConfigDao.updateById(bean);
				return Result.msg(Constants.EQU_SUCCESS, "成功");
			}
			this.tenantConfigDao.insert(bean);
			return Result.msg(Constants.EQU_SUCCESS, "成功");
		} catch (Exception e) {
			log.error("租户样式保存失败{}",e.getMessage(),e);
		}
		return Result.msg(Constants.BAD_REQUEST, "失败");
	}
	
	


}
