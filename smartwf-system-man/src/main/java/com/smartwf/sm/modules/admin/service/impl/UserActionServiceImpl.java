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
import com.smartwf.sm.modules.admin.dao.UserActionDao;
import com.smartwf.sm.modules.admin.pojo.UserAction;
import com.smartwf.sm.modules.admin.service.UserActionService;
import com.smartwf.sm.modules.admin.vo.UserActionVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 用户操作业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class UserActionServiceImpl implements UserActionService{
	
	@Autowired
	private UserActionDao UserActionDao;

	/**
	 * @Description:查询用户操作分页
	 * @result:
	 */
	@Override
	public Result<?> selectUserActionByPage(Page<UserAction> page, UserActionVO bean) {
		QueryWrapper<UserAction> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("update_time"); //降序
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			queryWrapper.eq("tenantId", bean.getTenantId()); 
  		}
        //用户操作编码
        if (!StringUtils.isEmpty(bean.getActCode())) {
        	queryWrapper.like("UserActionCode", Constants.PER_CENT + bean.getActCode() + Constants.PER_CENT);
        }
        //用户操作名称
        if (!StringUtils.isEmpty(bean.getActName())) {
        	queryWrapper.like("UserActionName", Constants.PER_CENT + bean.getActName() + Constants.PER_CENT);
        }
        //状态
		if (null!=bean.getEnable()) { 
			queryWrapper.eq("enable", bean.getEnable()); 
		}
        //时间
        if (bean.getStartTime() != null && bean.getEndTime() != null) {
        	queryWrapper.between("createTime", bean.getStartTime(), bean.getEndTime());
        }
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
        	queryWrapper.like("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		IPage<UserAction> list=this.UserActionDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
     * @Description: 主键查询用户操作
     * @return
     */
	@Override
	public Result<?> selectUserActionById(UserAction bean) {
		UserAction UserAction= this.UserActionDao.selectById(bean);
		return Result.data(UserAction);
	}
	
	/**
     * @Description: 添加用户操作
     * @return
     */
	@Override
	public void saveUserAction(UserAction bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.UserActionDao.insert(bean);
	}

	/**
     * @Description： 修改用户操作
     * @return
     */
	@Override
	public void updateUserAction(UserAction bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.UserActionDao.updateById(bean);
	}

	/**
     * @Description： 删除用户操作
     * @return
     */
	@Transactional
	@Override
	public void deleteUserAction(UserActionVO bean) {
		if( null!=bean.getId()) {
			//删除用户操作表
			this.UserActionDao.deleteById(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(",")) {
					list.add(val);
				}
				//用户操作表
				this.UserActionDao.deleteUserActionByIds(list);
			}
		}
	}

	/**
     * @Description： 初始化用户操作
     * @return
     */
	@Override
	public List<UserAction> queryUserActionAll() {
		return this.UserActionDao.queryUserActionAll();
	}


}
