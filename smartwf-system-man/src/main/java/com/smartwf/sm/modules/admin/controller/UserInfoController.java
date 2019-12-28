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
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用户资料控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("user")
@Slf4j
@Api(description ="用户资料控制器")
public class UserInfoController {
	
	@Autowired
	private UserInfoService userService;
	
	/**
	 * @Description: 查询用户资料分页
	 * @return
	 */
    @GetMapping("selectUserInfoByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询用户资料")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query", name = "userCode", value = "用户编码", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "updateTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectUserInfoByPage(Page<UserInfo> page, UserInfo bean) {
        try {
            Result<?> result = this.userService.selectUserInfoByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询用户资料信息错误！"));
    }
    
    /**
     * @Description: 主键查询用户资料
     * @return
     */
    @GetMapping("selectUserInfoById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询用户资料")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectSysUserById(UserInfo bean) {
        try {
            Result<?> result = this.userService.selectUserInfoById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加用户资料
     * @return
     */
    @PostMapping("saveUserInfo")
    @ApiOperation(value = "添加接口", notes = "添加用户资料接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "organizationIds", value = "组织架构（主键），逗号拼接", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "postIds", value = "职务（主键），逗号拼接", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "roleIds", value = "角色（主键），逗号拼接", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "userCode", value = "用户编码", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录账号", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "mobile", value = "手机号", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "sex", value = "性别（0-女 1-男）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "mgrType", value = "等级（0-普通 1-管理员 2-超级管理员）", dataType = "int", required = true),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "电话", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "address", value = "联系地址", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveSysUser(HttpSession session,UserInfoVO bean) {
        try {
    		this.userService.saveUserInfo(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加用户资料信息错误！"));
    }
    
    /**
     * @Description： 修改用户资料
     * @return
     */
    @PutMapping("updateUserInfo")
    @ApiOperation(value = "修改接口", notes = "修改用户资料资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "organizationIds", value = "组织架构（主键），逗号拼接", dataType = "String", required = true),
	    @ApiImplicitParam(paramType = "query", name = "postIds", value = "职务（主键），逗号拼接", dataType = "String", required = true),
	    @ApiImplicitParam(paramType = "query", name = "roleIds", value = "角色（主键），逗号拼接", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "userCode", value = "用户编码", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录账号", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "mobile", value = "手机号", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "phone", value = "电话", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "sex", value = "性别（0-女 1-男）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "mgrType", value = "等级（0-普通 1-管理员 2-超级管理员）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "address", value = "联系地址", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改用户资料", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateSysUser(UserInfoVO bean) {
        try {
            this.userService.updateUserInfo(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改用户资料信息错误！"));
    }
    
    /**
     * @Description： 删除用户资料
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteUserInfo")
    @ApiOperation(value = "删除接口", notes = "删除用户资料")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除用户资料系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteUserInfo(UserInfoVO bean) {
        try {
        	this.userService.deleteUserInfo(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除用户资料信息错误！"));
    }


}
