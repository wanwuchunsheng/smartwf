package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

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
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserAction;
import com.smartwf.sm.modules.admin.service.UserActionService;
import com.smartwf.sm.modules.admin.vo.UserActionVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用户操作控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("useraction")
@Slf4j
@Api(description ="用户操作控制器")
public class UserActionController {
	
	@Autowired
	private UserActionService userActionService;
	
	/**
	 * @Description: 查询用户操作分页
	 * @return
	 */
    @GetMapping("selectUserActionByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询用户操作")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "actCode", value = "操作编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "actName", value = "操作名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectUserActionByPage(Page<UserAction> page, UserActionVO bean) {
        try {
            Result<?> result = this.userActionService.selectUserActionByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询用户操作信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询用户操作信息错误！"));
    }
    
    /**
     * @Description: 主键查询用户操作
     * @return
     */
    @GetMapping("selectUserActionById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询用户操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectUserActionById(UserAction bean) {
        try {
            Result<?> result = this.userActionService.selectUserActionById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询用户操作信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加用户操作
     * @return
     */
    @PostMapping("saveUserAction")
    @ApiOperation(value = "添加接口", notes = "添加用户操作接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户id", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "actCode", value = "操作编码", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "actName", value = "操作名称", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveUserAction(HttpSession session,UserAction bean) {
        try {
    		this.userActionService.saveUserAction(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加用户操作信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加用户操作信息错误！"));
    }
    
    /**
     * @Description： 修改用户操作
     * @return
     */
    @PutMapping("updateUserAction")
    @ApiOperation(value = "修改接口", notes = "修改用户操作资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "用户操作（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "actCode", value = "操作编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "actName", value = "操作名称", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改用户操作", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateUserAction(UserAction bean) {
        try {
            this.userActionService.updateUserAction(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改用户操作信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改用户操作信息错误！"));
    }
    
    /**
     * @Description： 删除用户操作
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteUserAction")
    @ApiOperation(value = "删除接口", notes = "删除用户操作")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "int"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除用户操作系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteUserAction(UserActionVO bean) {
        try {
        	this.userActionService.deleteUserAction(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除用户操作信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除用户操作信息错误！"));
    }


}
