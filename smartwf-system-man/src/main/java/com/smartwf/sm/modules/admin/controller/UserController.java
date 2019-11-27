package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.sm.modules.admin.service.UserService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用户资源控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("user")
@Slf4j
@Api("用户资源控制器")
public class UserController {
	
	@Autowired
	private UserService userService;
	


}
