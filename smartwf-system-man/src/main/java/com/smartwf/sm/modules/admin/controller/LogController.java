package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Log;
import com.smartwf.sm.modules.admin.service.LogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 日志控制器
 */
@RestController
@RequestMapping("log")
@Slf4j
@Api(description = "操作日志控制器")
public class LogController {


    @Autowired
    private LogService logService;


    /**
     * 分页查询日志
     *
     * @param page
     * @return
     */
    @GetMapping("selectLogByPage")
    @ApiOperation(value = "查询日志", notes = "分页查询日志")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "要查看的页码，默认是1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页查询数量，默认是10", dataType = "Integer")
    })
    public ResponseEntity<Result> selectLogByPage(Page<Log> page) {
        try {
            Result result = this.logService.selectLogByPage(page);
            log.info("返回结果{}",JSONArray.toJSONString(result));
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("分页查询日志信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询日志信息错误！"));
    }
    
    /**
     * @Description: 主键查询操作日志
     * @return
     */
    @GetMapping("selectLogById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询操作日志")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectLogById(Log bean) {
        try {
            Result<?> result = this.logService.selectLogById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询操作日志信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description： 删除操作日志
     * @param id 单个删除
     * @return
     */
    @DeleteMapping("deleteLog")
    @ApiOperation(value = "删除接口", notes = "删除操作日志")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "int",required = true)
    })
    @TraceLog(content = "删除操作日志系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteLog(Log bean) {
        try {
        	this.logService.deleteLog(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除操作日志信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除操作日志信息错误！"));
    }
}
