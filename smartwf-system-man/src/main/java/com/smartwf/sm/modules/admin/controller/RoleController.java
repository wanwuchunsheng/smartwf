package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

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
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.service.RoleService;
import com.smartwf.sm.modules.admin.vo.RoleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 角色控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("role")
@Slf4j
@Api(description ="角色控制器")
public class RoleController {
	
	@Autowired
	private RoleService RoleService;
	
	/**
	 * @Description: 查询角色分页
	 * @return
	 */
    @GetMapping("selectRoleByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询角色")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "mgrType", value = "管理员类型（0-普通 1管理员  2超级管理员）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "roleCode", value = "角色编号", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectRoleByPage(Page<Role> page, RoleVO bean) {
        try {
            Result<?> result = this.RoleService.selectRoleByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询角色信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询角色信息错误！"));
    }
    
    /**
     * @Description: 主键查询角色
     * @return
     */
    @GetMapping("selectRoleById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectRoleById(Role bean) {
        try {
            Result<?> result = this.RoleService.selectRoleById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询角色信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加角色
     * @return
     */
    @PostMapping("saveRole")
    @ApiOperation(value = "添加接口", notes = "添加角色接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "roleCode", value = "角色编码", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "int"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveRole(HttpSession session,Role bean) {
        try {
    		this.RoleService.saveRole(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加角色信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加角色信息错误！"));
    }
    
    /**
     * @Description： 修改角色
     * @return
     */
    @PutMapping("updateRole")
    @ApiOperation(value = "修改接口", notes = "修改角色资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int"),
	    @ApiImplicitParam(paramType = "query", name = "RoleCode", value = "角色编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "RoleName", value = "角色名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int"),
        @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "int"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改角色", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateRole(Role bean) {
        try {
            this.RoleService.updateRole(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改角色信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改角色信息错误！"));
    }
    
    /**
     * @Description： 删除角色
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteRole")
    @ApiOperation(value = "删除接口", notes = "删除角色")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "int"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除角色系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteRole(RoleVO bean) {
        try {
        	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除数据为空！"));
        	}
        	this.RoleService.deleteRole(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除角色信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除角色信息错误！"));
    }


}
