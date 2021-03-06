package com.smartwf.sm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.Wso2LoginUtils;
import com.smartwf.common.wso2.Wso2Config;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录拦截器
 * @author WCH
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

	
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private Wso2Config wso2Config;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	return Wso2LoginUtils.checkLogin(request, response, handler, redisService, wso2Config);
    	//return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 当请求完成之后，消除用户信息、权限信息
        UserThreadLocal.setUser(null);
    }
   

}