package com.smartwf.sm.modules.admin.service;

import com.github.pagehelper.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

/**
 * @Description: 用户资料业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface UserInfoService {

	/**
	 * @Description: 查询用户资料分页
	 * @result: 
	 */
	Result<?> selectUserInfoByPage(Page<Object> page, UserInfo bean);

	/**
     * @Description: 主键查询用户资料
     * @return
     */
	Result<?> selectUserInfoById(UserInfo bean);

	/**
     * @Description： 添加用户资料
     * @return
     */
	void saveUserInfo(UserInfoVO bean);
	
	/**
     * @Description： 修改用户资料
     * @return
     */
	void updateUserInfo(UserInfoVO bean);

	/**
     * @Description： 删除用户资料
     * @return
     */
	void deleteUserInfo(UserInfoVO bean);
	

}
