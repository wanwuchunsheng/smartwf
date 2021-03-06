package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.ParamValidated.Add;
import com.smartwf.common.annotation.ParamValidated.Query;
import com.smartwf.common.annotation.ParamValidated.QueryParam;
import com.smartwf.common.annotation.ParamValidated.Update;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
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
	private RoleService roleService;
	
	/**
	 * @Description: 查询角色分页
	 * @return
	 */
    @GetMapping("selectRoleByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询角色")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "roleCode", value = "角色编号", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectRoleByPage(Page<Role> page,@Validated(value = QueryParam.class) RoleVO bean) {
        try {
            Result<?> result = this.roleService.selectRoleByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询角色信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询角色信息错误！"));
    }
    
    
    /**
	 * @Description: 查询角色列表
	 *   根据当前用户的角色，过滤列表
	 *   平台管理员显示全部角色
	 *   租户管理员不能显示平台管理员角色
	 *   风场管理员不能显示平台管理员、租户管理员角色
	 * @return
	 */
    @GetMapping("selectRoleByUserId")
    @ApiOperation(value = "筛选角色列表接口", notes = "筛选角色列表")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectRoleByUserId(Role bean) {
        try {
            Result<?> result = this.roleService.selectRoleByUserId(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("筛选角色列表信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("筛选角色列表信息错误！"));
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
    public ResponseEntity<Result<?>> selectRoleById(@Validated(value = Query.class) Role bean) {
        try {
            Result<?> result = this.roleService.selectRoleById(bean);
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
		    @ApiImplicitParam(paramType = "query", name = "engName", value = "角色英文名称（Wso2扩展字段）", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveRole(HttpSession session,@Validated(value = Add.class) Role bean) {
        try {
        	Result<?> result=this.roleService.saveRole(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
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
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "roleCode", value = "角色编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "engName", value = "角色英文名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改角色", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateRole(@Validated(value = Update.class) Role bean) {
    	try {
        	Result<?> result=this.roleService.updateRole(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
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
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer")
    })
    @TraceLog(content = "删除角色系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteRole(RoleVO bean) {
    	Result<?> data= this.roleService.deleteRole(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.data(Constants.EQU_SUCCESS, data));
    }


}
