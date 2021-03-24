package com.smartwf.sm.modules.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.app.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 手机APP控制器
 * @author WCH
 */
@RestController
@RequestMapping("app")
@Slf4j
@Api(description = "手机APP控制器")
@Validated
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	/**
     * @Description app登录认证
     * @return
     */
    @GetMapping("login")
    @ApiOperation(value = "登录接口", notes = "登录信息")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录账号（禁止特殊字符）", dataType = "String", required = true),
	    @ApiImplicitParam(paramType = "query", name = "pwd", value = "密码（长度不小于6）", dataType = "String", required = true),
	    @ApiImplicitParam(paramType = "query", name = "clientKey", value = "wso2 client_key", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> userLogin(HttpServletRequest request, User user,
    		@NotNull(message = "登录账号不能为空") String loginCode,
    		@NotNull(message = "密码不能为空") String pwd,
    		@NotNull(message = "wso2 client_key不能为空") String clientKey	) {
        try {
           //通过参数，换取wso2登录信息
           Result<?> result = this.loginService.userLogin(request,user);
           return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("登录信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("登录信息错误！"));
    }
    
    

}
