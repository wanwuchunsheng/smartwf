package com.smartwf.sm.modules.sysconfig.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.EmailConfig;
import com.smartwf.sm.modules.sysconfig.service.EmailConfigService;
import com.smartwf.sm.modules.sysconfig.vo.EmailConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 邮件短信配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("emailconf")
@Slf4j
@Api(description ="邮件短信配置控制器")
public class EmailConfigController {
	
	@Autowired
	private EmailConfigService emailConfigService;
	
	
	/**
	 * @Description: 查询邮件短信配置分页
	 * @param bean
	 * @return
	 */
    @GetMapping("selectEmailConfigByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询邮件短信配置信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query", name = "serviceAddress", value = "服务地址", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "port", value = "端口", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "LoginCode", value = "登录名", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型 0-邮箱  1-短信", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectEmailConfigByPage(Page<EmailConfig> page, EmailConfigVO bean) {
        try {
            Result<?> result = this.emailConfigService.selectEmailConfigByPage(page, bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询邮件短信配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询邮件短信配置错误！"));
    }
    
    /**
     * @Description: 主键查询邮件短信配置
     * @param bean
     * @return
     */
    @GetMapping("selectEmailConfigById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询邮件短信配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectEmailConfigById(EmailConfig bean) {
        try {
            Result<?> result = this.emailConfigService.selectEmailConfigById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询邮件短信配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询邮件短信配置错误！"));
    }
    
    /**
     * @Description: 添加邮件短信配置
     * @param bean
     * @return
     */
    @PostMapping("saveEmailConfig")
    @ApiOperation(value = "添加接口", notes = "添加邮件短信配置接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "serviceAddress", value = "服务地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "port", value = "端口", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "LoginCode", value = "登录名", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "type", value = "类型 0-邮箱  1-短信", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "添加邮件短信配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveEmailConfig(HttpServletRequest request, EmailConfig bean) {
    	try {
            //保存本地数据
        	this.emailConfigService.saveEmailConfig(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 修改邮件短信配置
     * @param bean
     * @return
     */
    @PutMapping("updateEmailConfig")
    @ApiOperation(value = "修改接口", notes = "修改邮件短信配置资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "serviceAddress", value = "服务地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "port", value = "端口", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "LoginCode", value = "登录名", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "type", value = "类型 0-邮箱  1-短信", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改邮件短信配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateEmailConfig(HttpServletRequest request, EmailConfig bean) {
    	try {
            //保存本地数据
        	this.emailConfigService.updateEmailConfig(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 删除邮件短信配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteEmailConfig")
    @ApiOperation(value = "删除接口", notes = "删除邮件短信配置")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除邮件短信配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteEmailConfig(EmailConfigVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
        this.emailConfigService.deleteEmailConfig(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }

}
