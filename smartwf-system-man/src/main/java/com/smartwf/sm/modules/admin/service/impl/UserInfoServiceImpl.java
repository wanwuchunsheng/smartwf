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
import com.smartwf.common.utils.MD5Utils;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.dao.UserOrganizationDao;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

import lombok.extern.log4j.Log4j;
import tk.mybatis.mapper.entity.Example;
/**
 * @Description: 用户资料业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao userInfoDao;

	/**
	 * @Description:查询用户资料分页
	 * @ MgrType：2超级管理员  1管理员  0普通
	 * 
	 *   1）a.普通用户只能看到所有普通用户信息  b.过滤租户
	 *   2）a.管理员用户可以看到所有管理员用户及所有普通用户信息  b.过滤租户
	 *   3）a.超级管理员可以看到所有超级用户信息，所有管理员信息，所有普通用户信息 b.非过滤租户
	 * @result:
	 */
	@Override
	public Result<?> selectUserInfoByPage(Page<Object> page, UserInfo bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		//过滤租户（超级管理员无需）
		if(Constants.ADMIN==bean.getMgrType()) {
			bean.setTenantId(null);
		}
		//查询
		List<UserInfo> UserInfoList = this.userInfoDao.selectUserInfoByPage(bean,objectPage);
		return Result.data(page.getTotal(), UserInfoList);
	}

	/**
     * @Description: 主键查询用户资料
     * @return
     */
	@Override
	public Result<?> selectUserInfoById(UserInfo bean) {
		UserInfo userInfo= this.userInfoDao.selectByPrimaryKey(bean);
		return Result.data(userInfo);
	}
	
	/**
     * @Description: 添加用户资料
     * @return
     */
	@Override
	public void saveUserInfo(UserInfo bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//md5加密
		if(StringUtils.isNotBlank(bean.getPwd())) {
			bean.setPwd(MD5Utils.convertMd5(bean.getPwd()));
		}
		//保存
		this.userInfoDao.insertSelective(bean);
	}

	/**
     * @Description： 修改用户资料
     * @return
     */
	@Override
	public void updateUserInfo(UserInfo bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//md5加密
		if(StringUtils.isNotBlank(bean.getPwd())) {
			bean.setPwd(MD5Utils.convertMd5(bean.getPwd()));
		}
		//修改
		this.userInfoDao.updateByPrimaryKeySelective(bean);
	}

	/**
     * @Description： 删除用户资料
     * @return
     */
	@Transactional
	@Override
	public void deleteUserInfo(UserInfoVO bean) {
		if( null!=bean.getId()) {
			//单个对象删除
			this.userInfoDao.deleteUserInfoById(bean);
			//删除用户组织架构表
			this.userInfoDao.deleteUserOrgById(bean);
			//删除用户职务表
			this.userInfoDao.deleteUserPostById(bean);
			//删除用户角色表
			this.userInfoDao.deleteUserRoleById(bean);
		}else {
			//批量删除
			String ids=StrUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(",")) {
					list.add(val);
					bean = new UserInfoVO();
					bean.setId(Integer.valueOf(val));
					//删除用户组织架构表
					this.userInfoDao.deleteUserOrgById(bean);
					//删除用户职务表
					this.userInfoDao.deleteUserPostById(bean);
					//删除用户角色表
					this.userInfoDao.deleteUserRoleById(bean);
				}
				//批量删除
				this.userInfoDao.deleteUserInfoByIds(list);
			}
		}
	}


}
