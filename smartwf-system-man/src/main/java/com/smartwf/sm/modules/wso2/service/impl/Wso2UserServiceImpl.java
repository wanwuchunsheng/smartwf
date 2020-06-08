package com.smartwf.sm.modules.wso2.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
import com.smartwf.sm.modules.wso2.service.Wso2UserService;

import lombok.extern.log4j.Log4j2;

/**
 * 功能说明：wso2用户管理服务层
 * @author WCH
 * 
 * */
@Service
@Log4j2
public class Wso2UserServiceImpl implements Wso2UserService {
	
	@Autowired
	Wso2Config wso2Config;

	/**
     * @Description：模拟wso2用户创建
     * @param userName,password
     * @return maps
     */
	@Override
	public Map<String,Object> addUser(UserInfoVO bean) {
		//封装http请求头
		Map<String,String> headers=new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("authorization", wso2Config.userAuthorization);
        //封装数据
        Map<String,String> data=new HashMap<>();
        data.put("userName", bean.getLoginCode());//登录账号
        data.put("password", bean.getPwd());
        //发送请求
        String str=HttpClientUtil.post(new StringBuffer().append(wso2Config.userServerUri).append("/scim2/Users").toString(), JsonUtil.objectToJson(data),headers);
        Map<String,Object> map=JsonUtil.jsonToMap(str);
        //返回
		return map;
	}

	/**
     * @Description：模拟wso2用户创建
     *    wso2用户主键 对应 userCode
     * @author WCH
     * @param  userCode   
     * @return
     */
	@Override
	public String deleteUserByUserCode(UserInfo bean) {
		Map<String,String> handlers=new HashMap<>();
		handlers.put("authorization", wso2Config.userAuthorization);
		try {
			return HttpClientUtil.delete(new StringBuffer().append(this.wso2Config.userServerUri).append("/scim2/Users/").append(bean.getUserCode()).toString(),  handlers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * @Description：模拟wso2用户修改
     * @param code,session_state和state
     * @return
     */
	@Override
	public Map<String, Object> updateByUserCode(UserInfoVO bean) {
		//封装http请求头
		Map<String,String> handlers=new HashMap<>();
		handlers.put("authorization", wso2Config.userAuthorization);
		handlers.put("content-type", "application/scim+json" );
		handlers.put("accept", "application/scim+json" );
		//封装数据
		Map<String,String> data=new HashMap<>();
		if(StringUtils.isNoneBlank(bean.getLoginCode())) {
			data.put("userName", bean.getLoginCode());
		}
		if(StringUtils.isNoneBlank(bean.getPwd())) {
			data.put("password", bean.getPwd());
		}
		//判断修改参数是否为空
		if(data.size()>0) {
			try {
				String res= HttpClientUtil.put(new StringBuffer().append(this.wso2Config.userServerUri).append("/scim2/Users/").append(bean.getUserCode()).toString(), JSONUtils.toJSONString(data), handlers);
			    log.info("res="+res);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	
}
