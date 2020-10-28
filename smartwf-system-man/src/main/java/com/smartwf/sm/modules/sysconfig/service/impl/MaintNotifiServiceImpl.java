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
import com.smartwf.sm.modules.sysconfig.dao.MaintNotifiDao;
import com.smartwf.sm.modules.sysconfig.pojo.MaintNotification;
import com.smartwf.sm.modules.sysconfig.service.MaintNotifiService;
import com.smartwf.sm.modules.sysconfig.vo.MaintNotifiVO;
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
		MaintNotifiVO icfg=this.maintNotifiDao.selectMaintNotifiById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加系统维护
     * @param bean
     * @return
     */
	@Override
	public Result<?> saveMaintNotifi(MaintNotification bean) {
		/**
		//添加前，查询是否已在维护，已在维护，不能继续发送维护信息
		QueryWrapper<MaintNotification> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("status", Constants.ZERO); 
		List<MaintNotification> list=this.maintNotifiDao.selectList(queryWrapper);
		if(list!=null && list.size()>0) {
			return Result.msg(Constants.ERRCODE502012, "失败，已处在维护状态！");
		}
		bean.setCreateTime(new Date());
		this.maintNotifiDao.insert(bean);
		return Result.msg(Constants.EQU_SUCCESS, "成功！");
		*/
		//status: 0-启用维护状态  1-关闭维护状态
		//启用停机维护，清空表数据
		this.maintNotifiDao.deleteMaintNotifiAll();
		//添加新的记录
		this.maintNotifiDao.insert(bean);
		//根据状态发送消息服务，通知前端做出相关调整
		
		//返回
		return Result.msg(Constants.EQU_SUCCESS, "成功！");
	}

	/**
     * @Description： 修改系统维护
     * @param bean
     * @return
     */
	@Override
	public Result<?> updateMaintNotifi(MaintNotification bean) {
		//添加之前，查询是否已在维护，已在维护，不能继续发送维护信息
		QueryWrapper<MaintNotification> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("status", Constants.ZERO); 
		List<MaintNotification> list=this.maintNotifiDao.selectList(queryWrapper);
		if(list!=null && list.size()>0) {
			return Result.msg(Constants.ERRCODE502012, "失败，已处在维护状态！");
		}
		bean.setEndTime(new Date());
		this.maintNotifiDao.updateById(bean);
		return Result.msg(Constants.EQU_SUCCESS, "成功！");
		
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
