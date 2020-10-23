package com.smartwf.sm.modules.sysconfig.controller;

import java.util.List;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.config.ftp.SFtpConfig;
import com.smartwf.sm.config.ftp.SFtpUtil;
import com.smartwf.sm.modules.sysconfig.pojo.IotConfig;
import com.smartwf.sm.modules.sysconfig.service.IotConfigService;
import com.smartwf.sm.modules.sysconfig.vo.IotConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 设备物联配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("iot")
@Slf4j
@Api(description ="设备物联配置控制器")
public class IotConfigController {
	
	@Autowired
	private IotConfigService IotConfigService;
	/**
	 * 获取上传地址
	 * 
	 * */
	@Autowired
    private SFtpConfig config;
	
	/**
	 * @Description: 查询设备物联配置分页
	 * @param bean
	 * @return
	 */
    @GetMapping("selectIotConfigByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询设备物联配置信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "int",required = true),
            @ApiImplicitParam(paramType = "query", name = "routeAddress", value = "路由地址", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectIotConfigByPage(Page<IotConfig> page, IotConfigVO bean) {
        try {
            Result<?> result = this.IotConfigService.selectIotConfigByPage(page, bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询设备物联配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询设备物联配置错误！"));
    }
    
    /**
     * @Description: 主键查询设备物联配置
     * @param bean
     * @return
     */
    @GetMapping("selectIotConfigById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询设备物联配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectIotConfigById(IotConfig bean) {
        try {
            Result<?> result = this.IotConfigService.selectIotConfigById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询设备物联配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询设备物联配置错误！"));
    }
    
    /**
     * @Description: 添加设备物联配置
     * @param bean
     * @return
     */
    @PostMapping(value="saveIotConfig", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "添加接口", notes = "添加设备物联配置接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "routeAddress", value = "路由地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "secretPath", value = "证书地址", dataType = "String")
    })
    @TraceLog(content = "添加设备物联配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveIotConfig(@ApiParam(value = "证书地址（多个文件压缩上传）") MultipartFile file, IotConfig bean) {
    	try {
    		if(null !=file) {
            	//保存图片
	        	String temp = "document/";
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
	        		//上传成功
	        		bean.setSecretPath(datdDirectory);
	        	}
            }
            //保存本地数据
        	this.IotConfigService.saveIotConfig(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 修改设备物联配置
     * @param bean
     * @return
     */
    @PutMapping(value="updateIotConfig", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "修改接口", notes = "修改设备物联配置资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "routeAddress", value = "路由地址", dataType = "String")
    })
    @TraceLog(content = "修改设备物联配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateIotConfig(@ApiParam(value = "证书地址（多个文件压缩上传）") MultipartFile file, IotConfig bean) {
    	try {
    		if(null !=file) {
            	//保存图片
	        	String temp = "document/";
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
	        		//上传成功
	        		bean.setSecretPath(datdDirectory);
	        	}
            }
            //保存本地数据
        	this.IotConfigService.updateIotConfig(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 删除设备物联配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteIotConfig")
    @ApiOperation(value = "删除接口", notes = "删除设备物联配置")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除设备物联配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteIotConfig(IotConfigVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
        this.IotConfigService.deleteIotConfig(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }

}
