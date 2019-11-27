package com.smartwf.sm.modules.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartwf.common.pojo.PageVO;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.UserInfoService;
/**
 * @Description: 用户资源业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao userInfoDao;

	/**
	 * @Description:查询用户资源分页
	 * @result:
	 */
	@Override
	public Result<?> selectUserInfoByPage(PageVO page, UserInfo bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPage(), page.getLimit());
        List<UserInfo> UserInfoList = this.userInfoDao.selectUserInfoByPage(bean,objectPage);
		return Result.data(objectPage.getTotal(), UserInfoList);
	}

	@Override
	public Result<?> selectUserInfoById(UserInfo bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserInfo(UserInfo bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserInfo(UserInfo bean) {
		// TODO Auto-generated method stub
		
	}

}
