package com.smartwf.sm.modules.app.service;

import javax.servlet.http.HttpServletRequest;

import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;

public interface LoginService {

	/**
     * @Description app登录认证
     * @return
     */
	Result<?> userLogin(HttpServletRequest request,User user);

}
