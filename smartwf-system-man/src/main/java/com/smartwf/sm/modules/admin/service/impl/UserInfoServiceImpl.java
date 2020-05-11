package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.MD5Utils;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.OrganizationDao;
import com.smartwf.sm.modules.admin.dao.PostDao;
import com.smartwf.sm.modules.admin.dao.ResourceDao;
import com.smartwf.sm.modules.admin.dao.RoleDao;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.dao.UserOrganizationDao;
import com.smartwf.sm.modules.admin.dao.UserPostDao;
import com.smartwf.sm.modules.admin.dao.UserRoleDao;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.pojo.UserPost;
import com.smartwf.sm.modules.admin.pojo.UserRole;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

import lombok.extern.log4j.Log4j;
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
	
	@Autowired
	private UserOrganizationDao userOrganizationDao;
	
	@Autowired
	private UserPostDao userPostDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ResourceDao resourceDao;

	
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
	public Result<?> selectUserInfoByPage(Page<UserInfo> page, UserInfo bean) {
		//查询
		List<UserInfo> UserInfoList = this.userInfoDao.selectUserInfoByPage(bean,page);
		return Result.data(page.getTotal(), UserInfoList);
	}

	/**
     * @Description: 主键查询用户资料
     * @return
     */
	@Override
	public Result<?> selectUserInfoById(UserInfo bean) {
		UserInfo userInfo= this.userInfoDao.selectUserInfoById(bean);
		return Result.data(userInfo);
	}
	
	/**
     * @Description: 添加用户资料
     * @return
     */
	@Transactional
	@Override
	public void saveUserInfo(UserInfoVO bean) {
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
		//保存用户资料
		this.userInfoDao.insert(bean);
		//批量添加与用户相关联表
		//添加用户组织机构
		if(StringUtils.isNotBlank(bean.getOrganizationIds())) {
			String orgIds=StrUtils.regex(bean.getOrganizationIds());
			if(StringUtils.isNotBlank(orgIds)) {
				UserOrganization uo=null;
				for(String id:orgIds.split(",")) {
					uo=new UserOrganization();
					uo.setUserId(bean.getId());
					uo.setTenantId(bean.getTenantId());
					uo.setOrganizationId(Integer.valueOf(id));
					this.userOrganizationDao.insert(uo);
				}
			}
		}
		
		//添加用户职务
		if(StringUtils.isNotBlank(bean.getPostIds())) {
			String postIds=StrUtils.regex(bean.getPostIds());
			if(StringUtils.isNotBlank(postIds)) {
				UserPost up = null;
				for(String id:postIds.split(",")) {
					up=new UserPost();
					up.setUserId(bean.getId());
					up.setTenantId(bean.getTenantId());
					up.setPostId(Integer.valueOf(id));
					this.userPostDao.insert(up);
				}
			}
		}
		//添加用户角色
		if(StringUtils.isNotBlank(bean.getRoleIds())) {
			String roleIds=StrUtils.regex(bean.getRoleIds());
			if(StringUtils.isNotBlank(roleIds)) {
				UserRole ur = null;
				for(String id:roleIds.split(",")) {
					ur=new UserRole();
					ur.setUserId(bean.getId());
					ur.setTenantId(bean.getTenantId());
					ur.setRoleId(Integer.valueOf(id));
					this.userRoleDao.insert(ur);
				}
			}
		}
	}

	/**
     * @Description： 修改用户资料
     *    1.用户信息修改
     *    2.用户关联表修改
     * @return
     */
	@Transactional
	@Override
	public void updateUserInfo(UserInfoVO bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//md5加密
		if(StringUtils.isNotBlank(bean.getPwd())) {
			bean.setPwd(MD5Utils.convertMd5(bean.getPwd()));
		}
		//1)修改用户资料
		this.userInfoDao.updateById(bean);
		
		//2）删除原始用户对应的关系表（清空）
		//删除用户组织架构表
		this.userInfoDao.deleteUserOrgById(bean);
		//删除用户职务表
		this.userInfoDao.deleteUserPostById(bean);
		//删除用户角色表
		this.userInfoDao.deleteUserRoleById(bean);
		//3）重新添加新的关联关系
		//添加用户组织机构
		String orgIds=StrUtils.regex(bean.getOrganizationIds());
		if(StringUtils.isNotBlank(orgIds)) {
			UserOrganization uo=null;
			for(String id:orgIds.split(",")) {
				uo=new UserOrganization();
				uo.setUserId(bean.getId());
				uo.setTenantId(bean.getTenantId());
				uo.setOrganizationId(Integer.valueOf(id));
				this.userOrganizationDao.insert(uo);
			}
		}
		//添加用户职务
		String postIds=StrUtils.regex(bean.getPostIds());
		if(StringUtils.isNotBlank(postIds)) {
			UserPost up = null;
			for(String id:postIds.split(",")) {
				up=new UserPost();
				up.setUserId(bean.getId());
				up.setTenantId(bean.getTenantId());
				up.setPostId(Integer.valueOf(id));
				this.userPostDao.insert(up);
			}
		}
		//添加用户角色
		String roleIds=StrUtils.regex(bean.getRoleIds());
		if(StringUtils.isNotBlank(roleIds)) {
			UserRole ur = null;
			for(String id:roleIds.split(",")) {
				ur=new UserRole();
				ur.setUserId(bean.getId());
				ur.setTenantId(bean.getTenantId());
				ur.setRoleId(Integer.valueOf(id));
				this.userRoleDao.insert(ur);
			}
		}
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

	/**
     * @Description：获取用户基础信息
     *   角色，权限，组织架构
     * @return
     */
	@Transactional
	@Override
	public User selectUserInfoByUserCode(User user) {
		//获取用户信息
		User userInfo= this.userInfoDao.selectUserInfoByUserCode(user);
		if(userInfo !=null) {
			userInfo.setCode(user.getCode());
			userInfo.setSmartwfToken(user.getSmartwfToken());
			userInfo.setRefreshToken(user.getRefreshToken());
			userInfo.setAccessToken(user.getAccessToken());
			//获取组织架构
			userInfo.setOrganizationList(this.organizationDao.selectOrganizationByUserId(userInfo));
			//职务
			userInfo.setPostList(this.postDao.selectPostByUserId(userInfo));
			//角色
			userInfo.setRoleList(this.roleDao.selectRoleByUserId(userInfo));
			//资源权限
			userInfo.setResouceList(this.resourceDao.selectResourceByUserId(userInfo));
		}
		return userInfo;
	}

	 
}
