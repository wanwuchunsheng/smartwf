package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.LoginRecord;
import com.smartwf.sm.modules.admin.service.LoginRecordService;
import com.smartwf.sm.modules.admin.vo.LoginRecordVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 登录记录控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("lgrecord")
@Slf4j
@Api(description ="登录记录控制器")
public class LoginRecordController {
	
	@Autowired
	private LoginRecordService loginRecordService;
	
    
    /**
	 * @Description: 查询所有登录记录
	 * @author WCH
	 * @return
	 */
    @GetMapping("selectLoginRecordByPage")
    @ApiOperation(value = "查询所有登录记录", notes = "查询所有登录记录")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int"),
    	    @ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "loginType", value = "登录类型", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "ipAddress", value = "登录地址", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "status", value = "状态（0成功 1失败）", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "loginTime", value = "登录时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectLoginRecordByPage(Page<LoginRecord> page,LoginRecordVO bean) {
        try {
            Result<?> result = this.loginRecordService.selectLoginRecordByPage(page,bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("查询所有登录记录错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有登录记录错误！"));
    }
  
    /**
	 * @Description: 保存登录记录
	 * @author WCH
	 * @return
	 */
    @GetMapping("addLoginRecordByPage")
    @ApiOperation(value = "保存登录记录接口", notes = "保存登录记录")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "loginType", value = "登录类型", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "ipAddress", value = "登录地址", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "status", value = "状态（0成功 1失败）", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "loginTime", value = "登录时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "创建时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date")
    })
    public ResponseEntity<Result<?>> addLoginRecordByPage(LoginRecord bean) {
        try {
            this.loginRecordService.addLoginRecordByPage(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("成功"));
        } catch (Exception e) {
            log.error("保存登录记录错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("保存登录记录错误！"));
    }

}
