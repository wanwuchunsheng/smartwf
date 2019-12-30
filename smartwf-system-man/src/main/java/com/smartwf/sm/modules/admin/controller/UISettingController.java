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
import com.smartwf.sm.modules.admin.pojo.SysConfig;
import com.smartwf.sm.modules.admin.service.UISettingService;
import com.smartwf.sm.modules.admin.vo.SysConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 前端配置控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("uisetting")
@Slf4j
@Api(description ="前端配置控制器")
public class UISettingController {
	
	@Autowired
	private UISettingService uiSettingService;
	
	/**
	 * @Description: 查询前端配置分页
	 * @return
	 */
    @GetMapping("selectUISettingByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询前端配置")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "configCode", value = "参数编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "configName", value = "参数名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "configKey", value = "键", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "configValue", value = "值", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "isSys", value = "是否系统内置（0是 1否）", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectUISettingByPage(Page<SysConfig> page, SysConfigVO bean) {
        try {
            Result<?> result = this.uiSettingService.selectUISettingByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询前端配置信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询前端配置信息错误！"));
    }
    
    
    /**
     * @Description: 主键查询前端配置
     * @return
     */
    @GetMapping("selectUISettingById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询前端配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectUISettingById(SysConfig bean) {
        try {
            Result<?> result = this.uiSettingService.selectUISettingById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询前端配置信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加前端配置
     * @return
     */
    @PostMapping("saveUISetting")
    @ApiOperation(value = "添加接口", notes = "添加前端配置接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "configCode", value = "参数编码", dataType = "String", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "configName", value = "参数名称", dataType = "String", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "configKey", value = "键", dataType = "String", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "configValue", value = "值", dataType = "String", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "isSys", value = "是否系统内置（0是 1否）", dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-默认启用 1-禁用）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveUISetting(HttpSession session,SysConfig bean) {
        try {
    		this.uiSettingService.saveUISetting(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加前端配置信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加前端配置信息错误！"));
    }
    
    /**
     * @Description： 修改前端配置
     * @return
     */
    @PutMapping("updateUISetting")
    @ApiOperation(value = "修改接口", notes = "修改前端配置资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "configCode", value = "参数编码", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "configName", value = "参数名称", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "configKey", value = "键", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "configValue", value = "值", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "isSys", value = "是否系统内置（0是 1否）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-默认启用 1-禁用）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改前端配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateUISetting(SysConfig bean) {
        try {
            this.uiSettingService.updateUISetting(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改前端配置信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改前端配置信息错误！"));
    }
    
    /**
     * @Description： 删除前端配置
     * @param id 单个删除
     * @return
     */
    @DeleteMapping("deleteUISetting")
    @ApiOperation(value = "删除接口", notes = "删除前端配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除前端配置系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteUISetting(SysConfigVO bean) {
        try {
        	this.uiSettingService.deleteUISetting(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除前端配置信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除前端配置信息错误！"));
    }


}
