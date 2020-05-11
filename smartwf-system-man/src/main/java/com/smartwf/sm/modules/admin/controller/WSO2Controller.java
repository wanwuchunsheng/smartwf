package com.smartwf.sm.modules.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.HttpClientUtil;

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
@RequestMapping("wso2")
@Slf4j
@Api(description = "全局数据控制器")
public class WSO2Controller {

	 /**
     * @Description：模拟wso2用户创建
     * @param code,session_state和state
     * @return
     */
    @GetMapping("addWSO2User")
    @ApiOperation(value = "wso2用户添加接口", notes = "wso2用户添加")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "password", value = "密码", dataType = "String")
    })
    public ResponseEntity<Result<?>> addWSO2User() {
        try {
        	Map<String,String> headers=new HashMap<String,String>();
            headers.put("accept", "application/scim+json");
            headers.put("Content-Type", "application/scim+json");
            headers.put("userName", "admin");
            headers.put("password", "admin");
        	String str= HttpClientUtil.doPost("https://identity.windmagics.com/scim2/Users", headers,"utf-8");
        } catch (Exception e) {
            log.error("wso2用户添加错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2用户添加错误！"));
    }
}
