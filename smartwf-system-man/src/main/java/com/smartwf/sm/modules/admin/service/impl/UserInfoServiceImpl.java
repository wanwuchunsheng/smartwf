package com.smartwf.sm.modules.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
/**
 * @Description: 用户资料业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao userInfoDao;

	/**
	 * @Description:查询用户资料分页
	 * @result:
	 */
	@Override
	public Result<?> selectUserInfoByPage(Page<Object> page, UserInfo bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPageNum(), page.getPageSize());
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
     * @Description： 修改用户资料
     * @return
     */
	@Override
	public void updateUserInfo(UserInfo bean) {
		this.userInfoDao.updateByPrimaryKeySelective(bean);
	}

	/**
     * @Description： 删除用户资料
     * @return
     */
	@Transactional
	@Override
	public void deleteUserInfo(UserInfoVO bean) {
		//单个对象删除
		if( null!=bean.getId()) {
			this.userInfoDao.deleteByPrimaryKey(bean);
		}
		//批量删除
		if(StringUtils.isNotEmpty(bean.getIds())) {
			this.userInfoDao.deleteUserInfoByIds(bean);
		}
	}

}
