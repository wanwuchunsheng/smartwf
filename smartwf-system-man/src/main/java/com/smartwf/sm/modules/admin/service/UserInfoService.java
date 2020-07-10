package com.smartwf.sm.modules.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Tenant;
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
	Result<?> selectUserInfoByPage(Page<UserInfo> page, UserInfo bean);

	/**
     * @Description: 主键查询用户资料
     * @return
     */
	Result<?> selectUserInfoById(UserInfo bean);

	/**
     * @Description： 添加用户资料
     * @return
     */
	Result<?> saveUserInfo(UserInfoVO bean);
	
	/**
     * @Description： 修改用户资料
     * @return
     */
	void updateUserInfo(UserInfoVO bean);

	/**
     * @Description： 删除用户资料
     * @return
     */
	Result<?> deleteUserInfo(UserInfoVO bean);
	
	/**
     * @Description: 用户名密码查询用户基本信息
     * @return
     */
	User selectUserInfoByUserCode(User bean);
	/**
     * @Description: wso2租户默认用户保存
     * @return
     */
	void saveWso2UserInfo(UserInfoVO tv,Tenant bean);
}
