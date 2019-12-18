package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.sm.modules.admin.dao.PermissionDao;
import com.smartwf.sm.modules.admin.dao.ResouceDao;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.service.PermissionService;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResouceVO;

import lombok.extern.log4j.Log4j;
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
	
	@Autowired
	private ResouceDao resouceDao;

	/**
	 * @Description:查询权限分页
	 * @result:
	 */
	@Override
	public Result<?> selectPermissionByPage(Page<Permission> page, PermissionVO bean) {
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			
  		}
		List<Permission> list=this.permissionDao.selectPermissionByAll(bean);
		return Result.data(list);
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
		this.permissionDao.insert(bean);
	}

	

	/**
     * @Description： 删除权限
     * @return
     */
	@Transactional
	@Override
	public void deletePermission(Permission bean) {
		this.permissionDao.deleteById(bean);
	}

	/**
	 * @Description: 资源与用户操作查询
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	@Override
	public Result<?> selectResouceUserActByPage(PermissionVO bean) {
		//过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) {
  			bean.setTenantId(null);
  		}
		List<ResouceVO> list=this.resouceDao.selectResouceUserActByPage(bean);
		return Result.data(list);
	}

	


}
