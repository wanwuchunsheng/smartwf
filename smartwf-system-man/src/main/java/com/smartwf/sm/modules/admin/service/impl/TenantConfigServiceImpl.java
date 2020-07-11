package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.TenantConfigDao;
import com.smartwf.sm.modules.admin.pojo.TenantConfig;
import com.smartwf.sm.modules.admin.service.TenantConfigService;
import com.smartwf.sm.modules.admin.vo.TenantConfigVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 租户业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class TenantConfigServiceImpl implements TenantConfigService{
	
	@Autowired
	private TenantConfigDao tenantConfigDao;

	/**
	 * @Description:查询多租户配置分页
	 * @result:
	 */
	@Override
	public Result<?> selectTenantConfigByPage(Page<TenantConfig> page, TenantConfigVO bean) {
		QueryWrapper<TenantConfig> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("update_time"); 
        //租户ID
        if (!StringUtils.isEmpty(bean.getTenantId())) {
        	queryWrapper.eq("tenant_id", bean.getTenantId());
        }
        //登录名
        if (!StringUtils.isEmpty(bean.getLoginName())) {
        	queryWrapper.like("login_name", Constants.PER_CENT + bean.getLoginName() + Constants.PER_CENT);
        }
        //密码
        if (!StringUtils.isEmpty(bean.getLoginPwd())) {
        	queryWrapper.like("login_pwd", Constants.PER_CENT + bean.getLoginPwd() + Constants.PER_CENT);
        }
        //类型
  		if (null!=bean.getType()) { 
  			queryWrapper.eq("type", bean.getType()); 
  		}
        //状态
		if (null!=bean.getEnable()) { 
			queryWrapper.eq("enable", bean.getEnable()); 
		}
        //时间
        if (bean.getStartTime() != null && bean.getEndTime() != null) {
        	queryWrapper.between("create_time", bean.getStartTime(), bean.getEndTime());
        }
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
        	queryWrapper.like("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		IPage<TenantConfig> list=this.tenantConfigDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
     * @Description: 主键查询多租户配置
     * @return
     */
	@Override
	public Result<?> selectTenantConfigById(TenantConfig bean) {
		TenantConfig tenant= this.tenantConfigDao.selectById(bean);
		return Result.data(tenant);
	}
	
	/**
     * @Description: 添加多租户配置
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveTenantConfig(TenantConfig bean) {
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		this.tenantConfigDao.insert(bean);
	}

	/**
     * @Description： 修改多租户配置
     * @return
     */
	@Override
	public void updateTenantConfig(TenantConfig bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
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
			String ids=StrUtils.regex(bean.getIds());
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
	
	


}
