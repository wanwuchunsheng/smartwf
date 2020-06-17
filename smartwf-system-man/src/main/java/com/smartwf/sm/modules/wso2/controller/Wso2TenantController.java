package com.smartwf.sm.modules.wso2.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.wso2.pojo.Wso2Tenant;
import com.smartwf.sm.modules.wso2.service.Wso2TenantService;

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
@RequestMapping("wso2/tenant")
@Slf4j
@Api(description = "WSO2租户控制器")
public class Wso2TenantController {
	
	@Autowired
    private Wso2TenantService wso2TenantService;
	

	/**
     * @Description：模拟wso2租户创建
     * @param code,session_state和state
     * @return
     */
    @PostMapping("addTenant")
    @ApiOperation(value = "wso2租户添加接口", notes = "wso2租户添加")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "active", value = "添加租户是否启用（true和false）", dataType = "String" ,required = true),
    	@ApiImplicitParam(paramType = "query", name = "admin",  value = "登录名", dataType = "String",required = true),
    	@ApiImplicitParam(paramType = "query", name = "adminPassword",  value = "密码（长度不小于5）", dataType = "String",required = true),
    	@ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", dataType = "String",required = true),
    	@ApiImplicitParam(paramType = "query", name = "firstname", value = "名", dataType = "String",required = true),
    	@ApiImplicitParam(paramType = "query", name = "lastname", value = "姓氏", dataType = "String",required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域（一般后面带.com）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> addTenant(Wso2Tenant bean) {
        try {
        	Map<String,Object> map =this.wso2TenantService.addTenant(bean);
        	if(!map.isEmpty()) {
        		for(java.util.Map.Entry<String,Object> m:map.entrySet()) {
            		log.info("返回信息內容："+m.getKey()+"    "+m.getValue());
            	}
        	}
        	return ResponseEntity.ok().body(Result.data(null, map, Constants.EQU_SUCCESS, "成功！"));
        } catch (Exception e) {
            log.error("wso2租户添加错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2租户添加错误！"));
    }
    
    
    /**
     * @Description：模拟wso2查询租户
     * @param code,session_state和state
     * @return
     */
    @GetMapping("selectTenant")
    @ApiOperation(value = "wso2租户查询接口", notes = "wso2租户查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域（一般后面带.com）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectTenant(Wso2Tenant bean) {
        try {
        	Map<String,Object> map =this.wso2TenantService.selectTenant(bean);
        	if(!map.isEmpty()) {
        		for(java.util.Map.Entry<String,Object> m:map.entrySet()) {
            		log.info("返回信息內容："+m.getKey()+"    "+m.getValue());
            	}
        	}
        	return ResponseEntity.ok().body(Result.data(null, map, Constants.EQU_SUCCESS, "成功！"));
        } catch (Exception e) {
            log.error("wso2租户查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2租户查询错误！"));
    }
    
    /**
     * @Description：模拟wso2删除租户{wso2租户无法删除，可以使用禁用租户}
     * @param code,session_state和state
     * @return
     */
    @DeleteMapping("deleteTenant")
    @ApiOperation(value = "wso2租户删除接口", notes = "wso2租户删除")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域（一般后面带.com）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> deleteTenant(Wso2Tenant bean) {
        try {
        	Map<String,Object> map =this.wso2TenantService.deleteTenant(bean);
        	if(!map.isEmpty()) {
        		for(java.util.Map.Entry<String,Object> m:map.entrySet()) {
            		log.info("返回信息內容："+m.getKey()+"    "+m.getValue());
            	}
        	}
        	return ResponseEntity.ok().body(Result.data(null, map, Constants.EQU_SUCCESS, "成功！"));
        } catch (Exception e) {
            log.error("wso2租户删除错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2租户删除错误！"));
    }
    
    /**
     * @Description：模拟wso2租户修改
     * @param code,session_state和state
     * @return
     */
    @PutMapping("updateTenant")
    @ApiOperation(value = "wso2租户修改接口", notes = "wso2租户修改")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "String" ,required = true),
    	@ApiImplicitParam(paramType = "query", name = "active", value = "添加租户是否启用（true和false）", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "admin",  value = "登录名", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "adminPassword",  value = "密码（长度不小于5）", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "firstname", value = "名", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "lastname", value = "姓氏", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域（一般后面带.com）", dataType = "String")
    })
    public ResponseEntity<Result<?>> updateTenant(Wso2Tenant bean) {
        try {
        	Map<String,Object> map =this.wso2TenantService.updateTenant(bean);
        	if(!map.isEmpty()) {
        		for(java.util.Map.Entry<String,Object> m:map.entrySet()) {
            		log.info("返回信息內容："+m.getKey()+"    "+m.getValue());
            	}
        	}
        	return ResponseEntity.ok().body(Result.data(null, map, Constants.EQU_SUCCESS, "成功！"));
        } catch (Exception e) {
            log.error("wso2租户修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2租户修改错误！"));
    }
    
    /**
     * @Description：模拟wso2租户禁用/启用
     *   true 启用
     *   fale 禁用
     * @param code,session_state和state
     * @return
     */
    @PutMapping("deactivateTenant")
    @ApiOperation(value = "wso2租户禁用接口", notes = "wso2租户禁用")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域（一般后面带.com）", dataType = "String",required = true),
    	@ApiImplicitParam(paramType = "query", name = "active", value = "true:启用   false：禁用", dataType = "Boolean",required = true)
    })
    public ResponseEntity<Result<?>> deactivateTenant(Wso2Tenant bean) {
        try {
        	Map<String,Object> map =this.wso2TenantService.deactivateTenant(bean);
        	if(!map.isEmpty()) {
        		for(java.util.Map.Entry<String,Object> m:map.entrySet()) {
            		log.info("返回信息內容："+m.getKey()+"    "+m.getValue());
            	}
        	}
        	return ResponseEntity.ok().body(Result.data(null, map, Constants.EQU_SUCCESS, "成功！"));
        } catch (Exception e) {
            log.error("wso2租户禁用错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2租户禁用错误！"));
    }
}
