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

import com.github.pagehelper.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.service.PermissionService;
import com.smartwf.sm.modules.admin.vo.PermissionVO;

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
	 * @Description: 查询权限分页
	 * @return
	 */
    @GetMapping("selectPermissionByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询权限")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
 	        @ApiImplicitParam(paramType = "query", name = "mgrType", value = "管理员类型（0-普通 1管理员  2超级管理员）", dataType = "int", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectPermissionByPage(Page<Object> page, PermissionVO bean) {
        try {
            Result<?> result = this.permissionService.selectPermissionByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询权限信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询权限信息错误！"));
    }
    
    /**
     * @Description: 主键查询权限
     * @return
     */
    @GetMapping("selectPermissionById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectPermissionById(Permission bean) {
        try {
            Result<?> result = this.permissionService.selectPermissionById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询权限信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加权限
     *      1子系统添加
     *      2模块，权限添加
     * @return
     */
    @PostMapping("savePermission")
    @ApiOperation(value = "添加接口", notes = "添加权限接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户id", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "uid", value = "上级id", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "pid", value = "父级id", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "resCode", value = "权限编码", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "resName", value = "权限名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "resType", value = "权限类型（1系统 2模块 3权限）", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "level", value = "层次级别", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-默认启用 1-禁用）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> savePermission(HttpSession session,Permission bean) {
        try {
    		this.permissionService.savePermission(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加权限信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加权限信息错误！"));
    }
    
    /**
     * @Description： 修改权限
     * @return
     */
    @PutMapping("updatePermission")
    @ApiOperation(value = "修改接口", notes = "修改权限资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "权限（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "resCode", value = "操作编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "resName", value = "操作名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改权限", paramIndexs = {0})
    public ResponseEntity<Result<?>> updatePermission(Permission bean) {
        try {
            this.permissionService.updatePermission(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改权限信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改权限信息错误！"));
    }
    
    /**
     * @Description： 删除权限
     * @param id 单个删除
     * @return
     */
    @DeleteMapping("deletePermission")
    @ApiOperation(value = "删除接口", notes = "删除权限")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键删除", dataType = "int")
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
