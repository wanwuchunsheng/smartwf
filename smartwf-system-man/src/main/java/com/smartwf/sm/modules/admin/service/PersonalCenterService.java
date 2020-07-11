package com.smartwf.sm.modules.admin.service;

import org.springframework.http.ResponseEntity;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 个人中心业务层接口
 * @author WCH
 */
public interface PersonalCenterService {

	/**
     *  修改用户密码
     *  @param id
     *  @param oldPwd
     *  @param newPwd
     * @return
     */
	ResponseEntity<Result<?>> updateUserPwd(Integer id, String oldPwd, String newPwd);
	/**
     * 修改用户资料
     * @param bean
     */
	void updateUserInfo(UserInfoVO bean);
	/**
     *主键查询用户资料
     *@param bean
     * @return
     */
	Result<?> selectUserInfoById(UserInfo bean);

}
