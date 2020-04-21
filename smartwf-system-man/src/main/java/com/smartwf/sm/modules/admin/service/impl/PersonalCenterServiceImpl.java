package com.smartwf.sm.modules.admin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.MD5Utils;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.PersonalCenterService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 个人中心业务层实现
 */
@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {
	
	@Autowired
	private UserInfoDao userInfoDao;
	

	/**
     * @Description： 修改用户密码
     * @return
     */
	@Override
	public ResponseEntity<Result<?>> updateUserPwd(Integer id, String oldPwd, String newPwd) {
		//新旧密码加密
		oldPwd=MD5Utils.convertMd5(oldPwd);
		newPwd=MD5Utils.convertMd5(newPwd);
		//旧密码正确性验证
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		//主键
  		queryWrapper.eq("id",id); 
  	    //旧密码
  		queryWrapper.eq("pwd",oldPwd); 
  		UserInfo userInfo= userInfoDao.selectOne(queryWrapper);
  		if(userInfo!=null) {
  			this.userInfoDao.updateUserPwd(id,oldPwd,newPwd);
  			return ResponseEntity.status(HttpStatus.OK).body(Result.msg("密码修改成功！"));
  		}
  		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("原始密码不正确！"));
	}

	/**
     * @Description： 修改用户资料
     * @return
     */
	@Override
	public void updateUserInfo(UserInfoVO bean) {
		this.userInfoDao.updateById(bean);
	}
	/**
     * @Description: 主键查询用户资料
     * @return
     */
	@Override
	public Result<?> selectUserInfoById(UserInfo bean) {
		// TODO Auto-generated method stub
		return null;
	}
   
}
