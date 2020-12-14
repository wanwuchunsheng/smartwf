package com.smartwf.sm.modules.sysconfig.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.config.ftp.SFtpConfig;
import com.smartwf.sm.config.ftp.SFtpUtil;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.service.TenantConfigService;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 首页配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("indexconf")
@Slf4j
@Api(description ="（系统管理中心）首页配置控制器")
public class IndexConfigController {

	
	@Autowired
	private TenantConfigService tenantConfigService;
	
	@Autowired
	private TenantService tenantService;
	
	/**
	 * 获取上传地址
	 * 
	 * */
	@Autowired
    private SFtpConfig config;
	
	/**
	 * 说明： 租户样式上传
	 *        （logo、样式json）
	 * @param tenantId
	 * @param remark  样式json
	 * @return
	 * 
	 * */
    @PostMapping(value = "/saveIndexStyleById", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	@ApiOperation(value = "主页样式接口", notes = "主页样式接配置（添加和修改都可以共用，有去重判断）")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户Id", dataType = "int",required = true),
        @ApiImplicitParam(paramType = "query", name = "styleJson", value = "系统样式（JSON数据）", dataType = "String")
    })
	public ResponseEntity<Result<?>> upload(HttpServletRequest request, TenantConfig bean , @ApiParam(value = "用户图片", required = true) MultipartFile file) {
	    if (!file.isEmpty()) {
	        if (file.getContentType().contains(Constants.IMAGE)) {
	            try {
	            	String temp = "image/";
	                // 获取图片的文件名
	                String fileName = file.getOriginalFilename();
	                // 获取图片的扩展名
	                String extensionName = fileName.substring(fileName.indexOf("."));
	                // 新的图片文件名 = 获取时间戳+"."图片扩展名
	                String newFileName = UUID.randomUUID().toString().replaceAll("-", "")  + extensionName;
	                // 数据库保存的目录
	                String datdDirectory = temp.concat(newFileName);
	                //上传文件到sftp
	                SFtpUtil.uploadFile( config,datdDirectory, file.getInputStream());
	                //修改租户表logo
	                Tenant tenant=new Tenant();
	                tenant.setId(bean.getTenantId());
	                //获取logo路径
	                tenant.setLogoUrl(datdDirectory);
	                this.tenantService.updateTenant(tenant);
	            }catch (Exception e){
	            	log.error("文件上传异常！{}", e.getMessage(), e);
	            	return ResponseEntity.ok(Result.msg(Constants.INTERNAL_SERVER_ERROR,"失败！上传异常"));
	            }
	        }
	    }
	    Result<?> result= this.tenantConfigService.saveIndexStyleById(bean);
        return ResponseEntity.ok(result);
	}
    
    @PostMapping(value = "/updaeIndexStyleById", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	@ApiOperation(value = "修改主页样式接口", notes = "修改主页样式接配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户Id", dataType = "int",required = true),
        @ApiImplicitParam(paramType = "query", name = "styleJson", value = "系统样式（JSON数据）", dataType = "String")
    })
	public ResponseEntity<Result<?>> updaeIndexStyleById(HttpServletRequest request, TenantConfig bean , @ApiParam(value = "用户图片", required = true) MultipartFile file) {
	    if (!file.isEmpty()) {
	        if (file.getContentType().contains(Constants.IMAGE)) {
	            try {
	            	String temp = "image/";
	                // 获取图片的文件名
	                String fileName = file.getOriginalFilename();
	                // 获取图片的扩展名
	                String extensionName = fileName.substring(fileName.indexOf("."));
	                // 新的图片文件名 = 获取时间戳+"."图片扩展名
	                String newFileName = UUID.randomUUID().toString().replaceAll("-", "")  + extensionName;
	                // 数据库保存的目录
	                String datdDirectory = temp.concat(newFileName);
	                //上传文件到sftp
	                SFtpUtil.uploadFile( config,datdDirectory, file.getInputStream());
	                //修改租户表logo
	                Tenant tenant=new Tenant();
	                tenant.setId(bean.getTenantId());
	                //获取logo路径
	                tenant.setLogoUrl(datdDirectory);
	                this.tenantService.updateTenant(tenant);
	            }catch (Exception e){
	            	log.error("文件上传异常！{}", e.getMessage(), e);
	            	return ResponseEntity.ok(Result.msg(Constants.INTERNAL_SERVER_ERROR,"失败！上传异常"));
	            }
	        }
	    }
	    Result<?> result= this.tenantConfigService.saveIndexStyleById(bean);
        return ResponseEntity.ok(result);
	}
    
    
    
    /**
     * @Description：查询
     * @return
     */
    @GetMapping("selectIndexStyleById")
    @ApiOperation(value = "查询主页样式接口", notes = "查询主页样式接口配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "样式主键", dataType = "ing", required = true)
    })
    @TraceLog(content = "查询首页样式配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> selectIndexStyleById( TenantConfig bean) {
    	try {
    		Result<?> result=this.tenantConfigService.selectTenantConfigById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("查询主页样式配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询主页样式配置错误！"));
    }
    
    /**
     * @Description：删除
     * @return
     */
    @DeleteMapping("deleteIndexStyleById")
    @ApiOperation(value = "删除主页样式接口", notes = "删除主页样式接口配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	
    })
    @TraceLog(content = "删除首页样式配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateWindFarmConfig(TenantConfigVO bean) {
    	try {
    		this.tenantConfigService.deleteTenantConfig(bean);
            return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "成功"));
        } catch (Exception e) {
            log.error("删除主页样式配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除主页样式配置错误！"));
    }
    
    
}
