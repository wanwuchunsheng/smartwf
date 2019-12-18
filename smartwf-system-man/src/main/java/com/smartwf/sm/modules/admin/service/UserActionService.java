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
	 * @Description: 查询用户操作分页
	 * @result: 
	 */
	Result<?> selectUserActionByPage(Page<UserAction> page, UserActionVO bean);

	/**
     * @Description: 主键查询用户操作
     * @return
     */
	Result<?> selectUserActionById(UserAction bean);

	/**
     * @Description： 添加用户操作
     * @return
     */
	void saveUserAction(UserAction bean);
	
	/**
     * @Description： 修改用户操作
     * @return
     */
	void updateUserAction(UserAction bean);

	/**
     * @Description： 删除用户操作
     * @return
     */
	void deleteUserAction(UserActionVO bean);
	
	/**
     * @Description： 初始化用户操作
     * @return
     */
	List<UserAction> queryUserActionAll();
	

}
