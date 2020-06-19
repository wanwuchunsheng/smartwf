package com.smartwf.sm.modules.wso2.service;
import java.util.Map;

import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

public interface Wso2UserService {

	/**
     * @Description：模拟wso2用户创建
     * @param code,session_state和state
     * @return
     */
	Map<String,Object> addUser(UserInfoVO bean);

	/**
     * @Description：模拟wso2用户删除
     * @return
     */
	String deleteUserByUserCode(UserInfo bean);

	/**
     * @Description：模拟wso2用户修改
     * @param code,session_state和state
     * @return
     */
	Map<String, Object> updateByUserCode(UserInfoVO bean);
	
	

}
