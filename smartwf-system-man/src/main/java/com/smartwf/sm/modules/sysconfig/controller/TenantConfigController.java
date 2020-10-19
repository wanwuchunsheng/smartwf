package com.smartwf.sm.modules.sysconfig.controller;

import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.config.ftp.SFtpConfig;
import com.smartwf.sm.config.ftp.SFtpUtil;
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
 * @Description: 多租户配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("tenantconf")
@Slf4j
@Api(description ="租户配置控制器")
public class TenantConfigController {
	
	@Autowired
	private TenantConfigService tenantConfigService;
	/**
	 * 获取上传地址
	 * 
	 * */
	@Autowired
    private SFtpConfig config;
	
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
    	    @ApiImplicitParam(paramType = "query", name = "logoUrl", value = "租户logo地址", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "path", value = "路由地址", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "type", value = "类型", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "省编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "areaCode", value = "县/区编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "latitude", value = "纬度", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "longitude", value = "经度", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "geoJson", value = "GeoJson数据", dataType = "String"),
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
    public ResponseEntity<Result<?>> selectTenantConfigById(TenantConfig bean) {
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
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "loginName", value = "登录名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "loginPwd", value = "登录密码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "logoUrl", value = "租户logo地址", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "path", value = "路由地址", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "type", value = "类型", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "省编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "areaCode", value = "县/区编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "latitude", value = "纬度", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "longitude", value = "经度", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "geoJson", value = "GeoJson数据", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "添加多租户配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveTenantConfig(HttpServletRequest request, TenantConfig bean) {
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
	    @ApiImplicitParam(paramType = "query", name = "logoUrl", value = "租户logo地址", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "path", value = "路由地址", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "type", value = "类型", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "省编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "areaCode", value = "县/区编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "latitude", value = "纬度", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "longitude", value = "经度", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "geoJson", value = "GeoJson数据", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改多租户配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateTenantConfig(HttpServletRequest request, TenantConfig bean) {
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

    /**
	 * 功能说明：图片上传
	 * @author WCH
	 * @dateTime 2020-4-21 14:09:52
	 * */
	@PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	@ApiOperation(value = "上传图片接口", notes = "上传图片")
	public ResponseEntity<Result<?>> upload(@ApiParam(value = "用户图片", required = true) MultipartFile file) {
	    if (!file.isEmpty()) {
	    	String image="image";
	        if (file.getContentType().contains(image)) {
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
                	boolean flag = SFtpUtil.uploadFile( config,datdDirectory, file.getInputStream());
                	if(flag) {
                		return ResponseEntity.status(HttpStatus.OK).body(Result.msg(datdDirectory));
    	        	}
	            }catch (Exception e){
	            	log.error("文件上传异常！{}", e.getMessage(), e);
	            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！上传异常"));
	            }
	        }
	    }
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！请检查上传的文件"));
	}

}
