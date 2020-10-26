package com.smartwf.sm.modules.sysconfig.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.RedisService;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;
import com.smartwf.sm.modules.sysconfig.service.WindFarmConfigService;
import com.smartwf.sm.modules.sysconfig.vo.WindfarmConfigVO;

import cn.hutool.core.convert.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 首页配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("indexconf")
@Slf4j
@Api(description ="（系统管理中心）首页配置控制器")
public class IndexConfigController {

	@Autowired
	private RedisService redisService;
	
	
    /**
     * @Description: 主键查询首页配置
     * @return
     */
    @PostMapping("saveIndexStyleById")
    @ApiOperation(value = "主页样式接口", notes = "主页样式接配置")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", dataType = "int",required = true),
        @ApiImplicitParam(paramType = "query", name = "sysStyle", value = "系统样式（JSON数据）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectWindFarmConfigById(String sysStyle,Integer userId) {
        try {
        	this.redisService.set(Convert.toStr(userId), sysStyle);
            return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "成功"));
        } catch (Exception e) {
            log.error("主页样式配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主页样式配置错误！"));
    }
    
    
    
    /**
     * @Description： 修改首页配置
     * @return
     */
    @PostMapping("updaeIndexStyleById")
    @ApiOperation(value = "修改主页样式接口", notes = "修改主页样式接配置")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", dataType = "int",required = true),
        @ApiImplicitParam(paramType = "query", name = "sysStyle", value = "系统样式（JSON数据）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> updaeIndexStyleById(String sysStyle,Integer userId) {
        try {
        	this.redisService.set(Convert.toStr(userId), sysStyle);
            return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "成功"));
        } catch (Exception e) {
            log.error("修改主页样式配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改主页样式配置错误！"));
    }
    
    /**
     * @Description：查询
     * @return
     */
    @DeleteMapping("selectIndexStyleById")
    @ApiOperation(value = "查询主页样式接口", notes = "查询主页样式接口配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "userId", value = "用户主键", dataType = "int", required = true),
    	
    })
    @TraceLog(content = "查询首页样式配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> selectIndexStyleById(Integer userId) {
    	try {
        	this.redisService.get(Convert.toStr(userId));
            return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "成功"));
        } catch (Exception e) {
            log.error("查询主页样式配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询主页样式配置错误！"));
    }
    
    /**
     * @Description：删除
     * @return
     */
    @DeleteMapping("deleteIndexStyleById")
    @ApiOperation(value = "删除主页样式接口", notes = "删除主页样式接口配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "userId", value = "用户主键", dataType = "int", required = true),
    	
    })
    @TraceLog(content = "删除首页样式配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateWindFarmConfig(Integer userId) {
    	try {
        	this.redisService.del(Convert.toStr(userId));
            return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "成功"));
        } catch (Exception e) {
            log.error("删除主页样式配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除主页样式配置错误！"));
    }
    
    
}
