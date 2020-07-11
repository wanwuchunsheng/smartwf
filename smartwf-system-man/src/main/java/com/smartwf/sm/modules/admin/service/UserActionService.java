package com.smartwf.sm.modules.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserAction;
import com.smartwf.sm.modules.admin.vo.UserActionVO;

/**
 * @Description: 用户操作业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface UserActionService {

	/**
	 * 查询用户操作分页
	 * @param bean
	 * @param page
	 * @return 
	 */
	Result<?> selectUserActionByPage(Page<UserAction> page, UserActionVO bean);

	/**
     * 主键查询用户操作
     * @param bean
     * @return 
     */
	Result<?> selectUserActionById(UserAction bean);

	/**
     *  添加用户操作
     * @param bean
     */
	void saveUserAction(UserAction bean);
	
	/**
     * 修改用户操作
     * @param bean
     */
	void updateUserAction(UserAction bean);

	/**
     *  删除用户操作
     * @param bean
     */
	void deleteUserAction(UserActionVO bean);
	
	/**
     *  初始化用户操作
     * @return
     */
	List<UserAction> queryUserActionAll();
	

}
