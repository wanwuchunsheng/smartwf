package com.smartwf.sm.modules.admin.service.impl;

import java.util.Date;

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
import com.smartwf.sm.modules.admin.dao.UiSettingDao;
import com.smartwf.sm.modules.admin.pojo.SysConfig;
import com.smartwf.sm.modules.admin.service.UiSettingService;
import com.smartwf.sm.modules.admin.vo.SysConfigVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 前端配置务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class UiSettingServiceImpl implements UiSettingService{
	
	@Autowired
	private UiSettingDao uiSettingDao;
	
	/**
	 * 查询前端配置分页
	 * @result:
	 */
	@Override
	public Result<?> selectUiSettingByPage(Page<SysConfig> page, SysConfigVO bean) {
		QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("update_time"); 
  	    //租户
  		if (null!=bean.getTenantId() ) { 
  			queryWrapper.eq("tenant_id", bean.getTenantId()); 
  		}
        //是否系统内置 0是 1否
  		if (null!=bean.getIsSys()) {
  			queryWrapper.eq("is_sys", bean.getIsSys());
  		}
        //编码
        if (!StringUtils.isEmpty(bean.getConfigCode())) {
        	queryWrapper.like("config_code", bean.getConfigCode() );
        }
        //名称
        if (!StringUtils.isEmpty(bean.getConfigCode())) {
        	queryWrapper.like("config_name", bean.getConfigCode() );
        }
        //键
        if (!StringUtils.isEmpty(bean.getConfigKey())) {
        	queryWrapper.like("config_key", bean.getConfigKey() );
        }
        //值
        if (!StringUtils.isEmpty(bean.getConfigValue())) {
        	queryWrapper.like("config_value",  bean.getConfigValue() );
        }
        //状态
		if (null!=bean.getEnable()) {
			queryWrapper.eq("enable", bean.getEnable());
		}
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
        	queryWrapper.like("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		IPage<SysConfig> list=this.uiSettingDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
     * 主键查询前端配置
     * @return
     */
	@Override
	public Result<?> selectUiSettingById(SysConfig bean) {
		SysConfig sysConfig= this.uiSettingDao.selectById(bean);
		return Result.data(sysConfig);
	}
	
	/**
     * 添加前端配置
     * @return
     */
	@Override
	public void saveUiSetting(SysConfig bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.uiSettingDao.insert(bean);
	}

	/**
     * 修改前端配置
     * @return
     */
	@Override
	public void updateUiSetting(SysConfig bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.uiSettingDao.updateById(bean);
	}

	/**
     *  删除前端配置
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteUiSetting(SysConfigVO bean) {
		if( null!=bean.getId()) {
			//删除
			this.uiSettingDao.deleteById(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				SysConfigVO scv=null;
				for(String val:ids.split(Constants.CHAR)) {
					scv = new SysConfigVO();
					scv.setId(Integer.valueOf(val));
					//删除
					this.uiSettingDao.deleteById(scv);
				}
		    }
		}
	}

}
