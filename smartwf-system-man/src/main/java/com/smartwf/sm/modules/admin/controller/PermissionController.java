package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.service.PermissionService;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResourceVO;
import com.smartwf.sm.modules.admin.vo.UserActionVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 权限控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("permission")
@Slf4j
@Api(description ="权限控制器")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * @Description: 查询资源子系统
	 * @return
	 */
    @GetMapping("selectResourceByPid")
    @ApiOperation(value = "查询子系统接口", notes = "查询资源子系统")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectResourceByPid( ResourceVO bean ) {
        try {
            Result<?> result = this.permissionService.selectResourceByPid(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("查询资源子系统信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询资源子系统信息错误！"));
    }
    
	
	/**
	 * @Description: 分页权限查询权限
	 * @return
	 */
    @GetMapping("selectPermissionByPage")
    @ApiOperation(value = "树形查询所有数据接口", notes = "树形查询所有数据")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "子系统（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectPermissionByPage( PermissionVO bean) {
        try {
            Result<?> result = this.permissionService.selectPermissionByPage(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询权限信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询权限信息错误！"));
    }
    

    /**
     * @Description: 授权添加
     * @param tenantId 租户ID
     * @param ids json字符串
     * @return
     */
    @PostMapping("savePermission")
    @ApiOperation(value = "添加接口", notes = "添加权限接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户id", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "json字符串", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> savePermission(HttpSession session,PermissionVO bean) {
        try {
        	if(StringUtils.isNotBlank(bean.getIds())) {
        		this.permissionService.savePermission(bean);
            	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        	}
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("ids数据不能为空！"));
        } catch (Exception e) {
            log.error("添加权限信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加权限信息错误！"));
    }
    
    /**
	 * @Description: 用户操作
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
    @GetMapping("selectUserActAll")
    @ApiOperation(value = "用户操作查询接口", notes = "用户操作查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectUserActAll(UserActionVO bean) {
        try {
            Result<?> result = this.permissionService.selectUserActAll(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("资源与用户操作查询信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("资源与用户操作查询信息错误！"));
    }
    
    /**
	 * @Description: 资源与用户操作查询
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
    @GetMapping("selectResourceUserActByPage")
    @ApiOperation(value = "资源与用户操作查询接口", notes = "资源与用户操作查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
 	        @ApiImplicitParam(paramType = "query", name = "mgrType", value = "管理员类型（0-普通 1管理员  2超级管理员）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectResourceActByPage( PermissionVO bean) {
        try {
            Result<?> result = this.permissionService.selectResourceUserActByPage(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("资源与用户操作查询信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("资源与用户操作查询信息错误！"));
    }
    
    
    /**
     * @Description： 删除权限
     * @param id 单个删除
     * @return
     */
    @DeleteMapping("deletePermission")
    @ApiOperation(value = "删除接口", notes = "删除权限")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键删除", dataType = "int", required = true)
    })
    @TraceLog(content = "删除权限系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deletePermission(Permission bean) {
        try {
        	this.permissionService.deletePermission(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除权限信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除权限信息错误！"));
    }


}
