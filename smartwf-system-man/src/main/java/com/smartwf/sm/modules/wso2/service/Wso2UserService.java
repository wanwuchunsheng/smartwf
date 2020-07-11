package com.smartwf.sm.modules.wso2.service;
import java.util.Map;

import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
/**
 * 
 * @author WCH
 * 
 * */
public interface Wso2UserService {

	/**
     * 模拟wso2用户创建
     * @param code,session_state和state
     * @param bean
     * @return
     */
	Map<String,Object> addUser(UserInfoVO bean);

	/**
     * 模拟wso2用户删除
     * @param bean
     * @return
     */
	String deleteUserByUserCode(UserInfo bean);

	/**
     * 模拟wso2用户修改
     * @param code,session_state和state
     * @param bean
     * @return
     */
	Map<String, Object> updateByUserCode(UserInfoVO bean);

	/**
     * 模拟wso2用户查询
     * @param code,session_state和state
     * @param bean
     * @param resInfo
     * @return
     */
	Map<String, Object> selectUserById(UserInfoVO bean, Tenant resInfo);
	
	/**
     * 模拟wso2用户查询
     * @param code,session_state和state
     * @param bean
     * @param resInfo
     * @return
     */
	Map<String, Object> selectUserByName(UserInfoVO bean, Tenant resInfo);
	
	

}
