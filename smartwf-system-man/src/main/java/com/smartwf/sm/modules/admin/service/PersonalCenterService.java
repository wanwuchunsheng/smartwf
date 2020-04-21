package com.smartwf.sm.modules.admin.service;

import org.springframework.http.ResponseEntity;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 个人中心业务层接口
 */
public interface PersonalCenterService {

	/**
     * @Description： 修改用户密码
     * @return
     */
	ResponseEntity<Result<?>> updateUserPwd(Integer id, String oldPwd, String newPwd);
	/**
     * @Description： 修改用户资料
     * @return
     */
	void updateUserInfo(UserInfoVO bean);
	/**
     * @Description: 主键查询用户资料
     * @return
     */
	Result<?> selectUserInfoById(UserInfo bean);

}
