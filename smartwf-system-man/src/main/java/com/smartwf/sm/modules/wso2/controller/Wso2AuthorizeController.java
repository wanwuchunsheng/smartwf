package com.smartwf.sm.modules.wso2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.wso2.service.Wso2AuthorizeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 说明：WSO2授权控制器
 * @author WCH
 * @date 2020-5-8 12:26:48
 * 
 * */
@RestController
@RequestMapping("wso2/authorize")
@Slf4j
@Api(description = "WSO2授权控制器")
public class Wso2AuthorizeController {
	
	@Autowired
	private Wso2AuthorizeService wso2AuthorizeService;
	
	
	/**
	 * 说明：UI批量授权服务器
	 * @author WCH
	 * @DateTime 2020-7-20 17:36:27
	 * @return
	 * */
    @PostMapping("batchUiAuthorization")
    @ApiOperation(value = "Wso2（UI）批量授权接口", notes = "Wso2（UI）批量授权")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "jsonStr", value = "JSON字符串", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> batchUiAuthorization(HttpServletRequest request, String jsonStr) {
        try {
        	String resStr =this.wso2AuthorizeService.batchUiAuthorization(jsonStr);
        	return ResponseEntity.ok().body(Result.data(resStr));
        } catch (Exception e) {
            log.error("Wso2（UI）批量授权错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("Wso2（UI）批量授权错误！"));
    }
}
