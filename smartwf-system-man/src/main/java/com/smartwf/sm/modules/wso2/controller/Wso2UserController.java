package com.smartwf.sm.modules.wso2.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
import com.smartwf.sm.modules.wso2.service.Wso2UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 说明：WSO2控制器
 * @author WCH
 * @date 2020-5-8 12:26:48
 * 
 * */
@RestController
@RequestMapping("wso2/user")
@Slf4j
@Api(description = "WSO2用户控制器")
public class Wso2UserController {
	
	@Autowired
    private Wso2UserService wso2UserService;
	
	

	/**
     * @Description：模拟wso2用户创建
     * @param code,session_state和state
     * @return
     */
    @PostMapping("addUser")
    @ApiOperation(value = "wso2用户添加接口", notes = "wso2用户添加")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> addUser(HttpServletRequest request, UserInfoVO bean) {
        try {
        	Map<String,Object> map =this.wso2UserService.addUser(bean);
        	for(java.util.Map.Entry<String,Object> m:map.entrySet()) {
        		log.info("返回信息內容："+m.getKey()+"    "+m.getValue());
        	}
        	ResponseEntity.ok().body(Result.data(null, map, Constants.EQU_SUCCESS, "成功！"));
        } catch (Exception e) {
            log.error("wso2用户添加错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2用户添加错误！"));
    }
    
    /**
     * @Description：模拟wso2用户删除
     * @param code,session_state和state
     * @return
     */
    @DeleteMapping("deleteUserByUserCode")
    @ApiOperation(value = "wso2用户删除接口", notes = "wso2用户删除")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录名", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String")
    })
    public ResponseEntity<Result<?>> deleteUserByUserCode(UserInfo bean) {
        try {
        	this.wso2UserService.deleteUserByUserCode(bean);
        	ResponseEntity.ok().body(Result.data("删除成功！"));
        } catch (Exception e) {
            log.error("wso2用户删除错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2用户删除错误！"));
    }
    
    
    /**
     * @Description：模拟wso2用户修改
     * @param code,session_state和state
     * @return
     */
    @PutMapping("updateByUserCode")
    @ApiOperation(value = "wso2用户修改接口", notes = "wso2用户修改")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录名", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> updateByUserCode(UserInfoVO bean) {
        try {
        	Map<String,Object> map =this.wso2UserService.updateByUserCode(bean);
        	for(java.util.Map.Entry<String,Object> m:map.entrySet()) {
        		log.info("返回信息內容："+m.getKey()+"    "+m.getValue());
        	}
        	ResponseEntity.ok().body(Result.data(null, map, Constants.EQU_SUCCESS, "成功！"));
        } catch (Exception e) {
            log.error("wso2用户修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2用户修改错误！"));
    }
    
    /**
     * @Description：模拟wso2用户修改
     * @param code,session_state和state
     * @return
     */
    @PutMapping("loginOut")
    @ApiOperation(value = "wso2用户修改接口", notes = "wso2用户修改")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "sessionId", value = "令牌", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> loginOut(HttpServletRequest request,User bean) {
        try {
			/*
			 * wso2UserService.loginOut ResponseEntity.ok().body(Result.data(map));
			 */
        } catch (Exception e) {
            log.error("wso2用户修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2用户修改错误！"));
    }
}
