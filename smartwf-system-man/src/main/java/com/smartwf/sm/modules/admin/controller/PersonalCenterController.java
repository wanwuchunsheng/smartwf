package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.PersonalCenterService;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 个人中心控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("personal")
@Slf4j
@Api(description ="个人中心控制器")
public class PersonalCenterController {
	
	@Autowired
	private PersonalCenterService personalCenterService;
	
	
	/**
     * @Description： 修改用户密码
     * @return
     */
    @PutMapping("updateUserPwd")
    @ApiOperation(value = "修改密码错误接口", notes = "修改密码错误")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "用户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "oldPwd", value = "旧密码", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "newPwd", value = "新密码", dataType = "String", required = true),
    })
    @TraceLog(content = "修改密码错误", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateUserPwd(Integer id,String oldPwd,String newPwd) {
        try {
        	ResponseEntity<Result<?>> result= this.personalCenterService.updateUserPwd(id,oldPwd,newPwd);
            return result;
        } catch (Exception e) {
            log.error("修改密码错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改密码错误！"));
    }
    
    
    /**
     * @Description： 修改用户资料
     * @return
     */
    @PutMapping("updateUserInfo")
    @ApiOperation(value = "修改用户资料接口", notes = "修改用户资料资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
        @ApiImplicitParam(paramType = "query", name = "loginCode", value = "登录账号", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "sex", value = "性别（0-女 1-男）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "mobile", value = "手机号", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "address", value = "联系地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "avatar", value = "头像路径", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "phone", value = "电话", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "mgrType", value = "等级（0-普通 1-管理员 2-超级管理员）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "userCode", value = "用户编码", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改用户资料", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateSysUser(UserInfoVO bean) {
        try {
            this.personalCenterService.updateUserInfo(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改用户资料信息错误！"));
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
            Result<?> result = this.personalCenterService.selectUserInfoById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
}
