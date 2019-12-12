package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.sm.modules.admin.dao.PermissionDao;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.service.PermissionService;
import com.smartwf.sm.modules.admin.vo.PermissionVO;

import lombok.extern.log4j.Log4j;
import tk.mybatis.mapper.entity.Example;
/**
 * @Description: 权限业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionDao permissionDao;

	/**
	 * @Description:查询权限分页
	 * @result:
	 */
	@Override
	public Result<?> selectPermissionByPage(Page<Object> page, PermissionVO bean) {
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			
  		}
		List<Permission> list=this.permissionDao.selectPermissionByAll(bean);
		return Result.data(null, list);
	}

	/**
     * @Description: 主键查询权限
     * @return
     */
	@Override
	public Result<?> selectPermissionById(Permission bean) {
		Permission Permission= this.permissionDao.selectByPrimaryKey(bean);
		return Result.data(Permission);
	}
	
	/**
     * @Description: 添加权限
     * @return
     */
	@Override
	public void savePermission(Permission bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.permissionDao.insertSelective(bean);
	}

	/**
     * @Description： 修改权限
     * @return
     */
	@Override
	public void updatePermission(Permission bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.permissionDao.updateByPrimaryKeySelective(bean);
	}

	/**
     * @Description： 删除权限
     * @return
     */
	@Transactional
	@Override
	public void deletePermission(Permission bean) {
		this.permissionDao.deleteByPrimaryKey(bean);
	}

	


}
