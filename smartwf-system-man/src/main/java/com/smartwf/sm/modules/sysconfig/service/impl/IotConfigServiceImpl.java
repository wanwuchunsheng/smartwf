package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.sysconfig.dao.IotConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.IotConfig;
import com.smartwf.sm.modules.sysconfig.service.IotConfigService;
import com.smartwf.sm.modules.sysconfig.vo.IotConfigVO;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;
/**
 * @Description: 设备物联配置业务实现层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class IotConfigServiceImpl implements IotConfigService{
	
	@Autowired
	private IotConfigDao iotConfigDao;

	/**
	 * @Description: 查询设备物联配置分页
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectIotConfigByPage(Page<IotConfig> page, IotConfigVO bean) {
		List<IotConfig> list=this.iotConfigDao.selectIotConfigByPage(page,bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}

	/**
     * @Description: 主键查询设备物联配置
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectIotConfigById(IotConfig bean) {
		IotConfig icfg=this.iotConfigDao.selectIotConfigById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加设备物联配置
     * @param bean
     * @return
     */
	@Override
	public void saveIotConfig(IotConfig bean) {
		this.iotConfigDao.insert(bean);
	}

	/**
     * @Description： 修改设备物联配置
     * @param bean
     * @return
     */
	@Override
	public void updateIotConfig(IotConfig bean) {
		this.iotConfigDao.updateById(bean);
	}

	/**
     * @Description： 删除设备物联配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteIotConfig(IotConfigVO bean) {
		if( null!=bean.getId()) {
			//删除多租户配置表
			this.iotConfigDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//多租户配置表
				this.iotConfigDao.deleteIotConfigByIds(list);
			}
		}
	}
	

}
