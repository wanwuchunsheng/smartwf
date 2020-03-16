package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import com.smartwf.common.annotation.ParamValidated.Update;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.TenantConfig;
import com.smartwf.sm.modules.admin.service.TenantConfigService;
import com.smartwf.sm.modules.admin.vo.TenantConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 多租户配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("tenantcfg")
@Slf4j
@Api(description ="多租户配置控制器")
public class TenantConfigController {
	
	@Autowired
	private TenantConfigService tenantConfigService;
	
	/**
	 * @Description: 查询多租户配置分页
	 * @return
	 */
    @GetMapping("selectTenantConfigByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询多租户配置信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "loginName", value = "登录名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "loginPwd", value = "登录密码", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectTenantConfigByPage(Page<TenantConfig> page, TenantConfigVO bean) {
        try {
            Result<?> result = this.tenantConfigService.selectTenantConfigByPage(page, bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询多租户配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询多租户配置错误！"));
    }
    
    /**
     * @Description: 主键查询多租户配置
     * @return
     */
    @GetMapping("selectTenantConfigById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询多租户配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectTenantConfigById(@Validated(value = Query.class) TenantConfig bean) {
        try {
            Result<?> result = this.tenantConfigService.selectTenantConfigById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询多租户配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询多租户配置错误！"));
    }
    
    /**
     * @Description: 添加多租户配置
     * @return
     */
    @PostMapping("saveTenantConfig")
    @ApiOperation(value = "添加接口", notes = "添加多租户配置接口")
    @ApiImplicitParams({
	    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
		    @ApiImplicitParam(paramType = "query", name = "loginName", value = "登录名称", dataType = "String",required = true),
		    @ApiImplicitParam(paramType = "query", name = "loginPwd", value = "登录密码", dataType = "String",required = true), 
	    	@ApiImplicitParam(paramType = "query", name = "type", value = "类型（1-IOT租户）", dataType = "int",required = true),
		    @ApiImplicitParam(paramType = "query", name = "pathUrl", value = "路径地址", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int",required = true),
	        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "添加多租户配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveTenantConfig(HttpSession session,@Validated(value = Add.class) TenantConfig bean) {
		this.tenantConfigService.saveTenantConfig(bean);
    	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
    }
    
    /**
     * @Description： 修改多租户配置
     * @return
     */
    @PutMapping("updateTenantConfig")
    @ApiOperation(value = "修改接口", notes = "修改多租户配置资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "loginName", value = "登录名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "loginPwd", value = "登录密码", dataType = "String"), 
    	@ApiImplicitParam(paramType = "query", name = "type", value = "类型（1-IOT租户）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "pathUrl", value = "路径地址", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改多租户配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateTenantConfig(@Validated(value = Update.class) TenantConfig bean) {
        this.tenantConfigService.updateTenantConfig(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
    }
    
    /**
     * @Description： 删除多租户配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteTenantConfig")
    @ApiOperation(value = "删除接口", notes = "删除多租户配置")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除多租户配置系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteTenantConfig(TenantConfigVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
        this.tenantConfigService.deleteTenantConfig(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }


}
