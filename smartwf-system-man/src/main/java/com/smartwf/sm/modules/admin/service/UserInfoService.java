package com.smartwf.sm.modules.admin.service;

import com.smartwf.common.pojo.PageVO;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserInfo;

/**
 * @Description: 用户资源业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface UserInfoService {

	/**
	 * @Description: 查询用户资源分页
	 * @result: 
	 */
	Result<?> selectUserInfoByPage(PageVO page, UserInfo bean);

	/**
     * @Description: 主键查询用户资源
     * @return
     */
	Result<?> selectUserInfoById(UserInfo bean);

	/**
     * @Description： 修改用户资源
     * @return
     */
	void updateUserInfo(UserInfo bean);

	/**
     * @Description： 删除用户资源
     * @return
     */
	void deleteUserInfo(UserInfo bean);

}
