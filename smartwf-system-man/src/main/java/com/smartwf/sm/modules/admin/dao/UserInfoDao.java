package com.smartwf.sm.modules.admin.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.pojo.UserRole;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 用户资源持久层接口
 * @author WCH
 */
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {

	/**
	 * 
	 * 查询用户资源分页
	 * @param page 
	 * @param bean
	 * @return
	 */
	List<UserInfo> selectUserInfoByPage(@Param("bean") UserInfo bean, Page<UserInfo> page);

	/**
	 *  批量删除
	 *  @param list
	 */
	void deleteUserInfoByIds(@Param("list") List<String> list);

	/**
	 * 单个删除
	 * @param bean
	 */
	void deleteUserInfoById(@Param("bean") UserInfoVO bean);

	/**
	 * 单个删除用户组织架构
	 * @param bean
	 */
	void deleteUserOrgById(@Param("bean") UserInfoVO bean);

	/**
	 * 单个删除用户职务
	 * @param bean
	 */
	void deleteUserPostById(@Param("bean") UserInfoVO bean);

	/**
	 * 单个删除用户角色
	 * @param bean
	 */
	void deleteUserRoleById(@Param("bean") UserInfoVO bean);
	/**
	 * 主键查询用户资料
	 * @param bean
	 * @return
	 */
	UserInfo selectUserInfoById(@Param("bean") UserInfo bean);

	/**
     *  修改用户密码
     * @param id
     * @param oldPwd
     * @param newPwd
     */
	void updateUserPwd(@Param("id") Integer id, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

	/**
     *  获取用户基础信息
     *  @param user
     * @return
     */
	User selectUserInfoByUserCode(@Param("bean") User user);

	/**
     * 查询用户头像路径
     * @author WCH
     * @param list
     * @return
     */
	List<UserInfo> selectUserInfoByCreateId(@Param("list") List<String> list);
	/**
   	 *  排班用户查询
   	 *    角色ID，租户域
   	 *    根据角色ID，查询属于该角色的所有排班用户
   	 * @author WCH
   	 * @param bean
   	 * @return
   	 */
	List<UserInfo> selectUserInfoByRoleId(@Param("bean") UserRole bean);
	/**
   	 *  排班人员信息
   	 *    根据租户和角色名称 分组查询
   	 * @author WCH
   	 * @return 
   	 */
	List<UserInfo> selectUserInfoByShift(@Param("bean") Role bean);


    
}
