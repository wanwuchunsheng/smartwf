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
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.admin.vo.TenantVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 租户控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("tenant")
@Slf4j
@Api(description ="租户控制器")
public class TenantController {
	
	@Autowired
	private TenantService tenantService;
	
	/**
	 * @Description: 查询租户分页
	 * @return
	 */
    @GetMapping("selectTenantByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询租户")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户代码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "tenantName", value = "租户名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "sel", value = "默认租户（0-否 1-是）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectTenantByPage(Page<Tenant> page, TenantVO bean) {
        try {
            Result<?> result = this.tenantService.selectTenantByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询租户信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询租户信息错误！"));
    }
    
    /**
     * @Description: 主键查询租户
     * @return
     */
    @GetMapping("selectTenantById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询租户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectTenantById(Tenant bean) {
        try {
            Result<?> result = this.tenantService.selectTenantById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询租户信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询租户信息错误！"));
    }
    
    /**
     * @Description: 添加租户
     * @return
     */
    @PostMapping("saveTenant")
    @ApiOperation(value = "添加接口", notes = "添加租户接口")
    @ApiImplicitParams({
	    	@ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户代码", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "tenantName", value = "租户名称", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "sel", value = "默认租户（0-否 1-是）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveTenant(HttpSession session,Tenant bean) {
        try {
    		this.tenantService.saveTenant(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加租户信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加租户信息错误！"));
    }
    
    /**
     * @Description： 修改租户
     * @return
     */
    @PutMapping("updateTenant")
    @ApiOperation(value = "修改接口", notes = "修改租户资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户代码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "tenantName", value = "租户名称", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "sel", value = "默认租户（0-否 1-是）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改租户", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateTenant(Tenant bean) {
        try {
            this.tenantService.updateTenant(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改租户信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改租户信息错误！"));
    }
    
    /**
     * @Description： 删除租户
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteTenant")
    @ApiOperation(value = "删除接口", notes = "删除租户")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "int"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除租户系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteTenant(TenantVO bean) {
        try {
        	this.tenantService.deleteTenant(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除租户信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除租户信息错误！"));
    }


}
