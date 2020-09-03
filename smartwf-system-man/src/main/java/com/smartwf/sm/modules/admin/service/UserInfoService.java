package com.smartwf.sm.modules.admin.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.pojo.UserRole;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

/**
 * @Description: 用户资料业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface UserInfoService {

	/**
	 * 查询用户资料分页
	 * @param bean
	 * @param page
	 * @return
	 */
	Result<?> selectUserInfoByPage(Page<UserInfo> page, UserInfo bean);

	/**
     * 主键查询用户资料
     * @param bean
     * @return
     */
	Result<?> selectUserInfoById(UserInfo bean);

	/**
     * 添加用户资料
     * @param bean
     * @return
     */
	Result<?> saveUserInfo(UserInfoVO bean);
	
	/**
     * 修改用户资料
     * @param bean
     */
	void updateUserInfo(UserInfoVO bean);

	/**
     *  删除用户资料
     *  @param bean
     * @return
     */
	Result<?> deleteUserInfo(UserInfoVO bean);
	
	/**
     * 用户名密码查询用户基本信息
     * @param bean
     * @return
     */
	User selectUserInfoByUserCode(User bean);
	/**
     * wso2租户默认用户保存
     * @param bean
     * @param tv
     */
	void saveWso2UserInfo(UserInfoVO tv,Tenant bean);

	/**
     * 查询用户头像路径
     * @author WCH
     * @param ids
     * @return
     */
	Result<?> selectUserInfoByCreateId(String ids);

	
	/**
   	 *  排班人员信息
   	 *    根据租户和角色名称 分组查询
   	 * @author WCH
   	 */
	void selectUserInfoByShift();

	/**
   	 *  排班用户查询
   	 *   查询所有
   	 * @author WCH
   	 * @param bean
   	 * @return
   	 */
	Result<?> selectUserInfoByRoleParam(String tenantDomain,String windFarm);
}
