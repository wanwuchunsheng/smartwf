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
import com.smartwf.sm.modules.sysconfig.dao.MaintNotifiDao;
import com.smartwf.sm.modules.sysconfig.pojo.MaintNotification;
import com.smartwf.sm.modules.sysconfig.service.MaintNotifiService;
import com.smartwf.sm.modules.sysconfig.vo.MaintNotifiVO;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;
/**
 * @Description: 系统维护业务实现层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class MaintNotifiServiceImpl implements MaintNotifiService{
	
	@Autowired
	private MaintNotifiDao maintNotifiDao;

	/**
	 * @Description: 查询系统维护分页
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectMaintNotifiByPage(Page<MaintNotification> page, MaintNotifiVO bean) {
		List<MaintNotification> list=this.maintNotifiDao.selectMaintNotifiByPage(page,bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}

	/**
     * @Description: 主键查询系统维护
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectMaintNotifiById(MaintNotification bean) {
		MaintNotification icfg=this.maintNotifiDao.selectMaintNotifiById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加系统维护
     * @param bean
     * @return
     */
	@Override
	public void saveMaintNotifi(MaintNotification bean) {
		this.maintNotifiDao.insert(bean);
	}

	/**
     * @Description： 修改系统维护
     * @param bean
     * @return
     */
	@Override
	public void updateMaintNotifi(MaintNotification bean) {
		this.maintNotifiDao.updateById(bean);
	}

	/**
     * @Description： 删除系统维护
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteMaintNotifi(MaintNotifiVO bean) {
		if( null!=bean.getId()) {
			//删除多租户配置表
			this.maintNotifiDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//多租户配置表
				this.maintNotifiDao.deleteMaintNotifiByIds(list);
			}
		}
	}
	

}
