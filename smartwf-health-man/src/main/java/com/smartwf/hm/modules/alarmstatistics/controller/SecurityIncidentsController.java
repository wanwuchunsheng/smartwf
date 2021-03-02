package com.smartwf.hm.modules.alarmstatistics.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.config.ftp.SFtpConfig;
import com.smartwf.hm.config.ftp.SFtpUtil;
import com.smartwf.hm.modules.alarmstatistics.pojo.FileUploadRecord;
import com.smartwf.hm.modules.alarmstatistics.pojo.SecurityIncidents;
import com.smartwf.hm.modules.alarmstatistics.service.SecurityIncidentsService;
import com.smartwf.hm.modules.alarmstatistics.vo.SecurityIncidentsVO;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WCH
 * @Date: 2019-10-25 15:04:26
 * @Description: 安全事故控制器
 */
@RestController
@RequestMapping("incident")
@Slf4j
@Api(description = "安全事故控制器")
public class SecurityIncidentsController {
	
	@Autowired
	private SecurityIncidentsService securityIncidentsService;
	
	@Autowired
	private SFtpConfig config;
	

	/**
	 * @Description: 安全事故-分页查询
	 *    列表信息
	 * @param startTime,endTime
	 * @param  incidentCode,locality
	 * @return
	 */
    @PostMapping("selectSecurityIncidentsByPage")
    @ApiOperation(value = "安全事故查询接口", notes = "安全事故查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "incidentCode", value = "警告码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "locality", value = "发生地", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
    	    @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectSecurityIncidentsByPage( Page<SecurityIncidents> page, SecurityIncidentsVO bean) {
        try {
        	Result<?> result = this.securityIncidentsService.selectAlarmInforByPage(page,bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("安全事故信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故信息错误！"));
    }
    
    /**
	 * @Description: 安全事故-主键查询
	 * @param id
	 * @return
	 */
    @GetMapping("selectSecurityIncidentsById")
    @ApiOperation(value = "安全事故主键查询接口", notes = "安全事故主键查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectSecurityIncidentsById(SecurityIncidents bean) {
        try {
        	Result<?> result = this.securityIncidentsService.selectSecurityIncidentsById(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("安全事故主键查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故主键查询错误！"));
    }
    
    /**
	 * @Description: 安全事故-附件查询
	 * @param id
	 * @return
	 */
    @GetMapping("selectSecurityIncidentsByFiles")
    @ApiOperation(value = "安全事故附件查询接口", notes = "安全事故附件查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectSecurityIncidentsByFiles(SecurityIncidents bean) {
        try {
        	Result<?> result = this.securityIncidentsService.selectSecurityIncidentsByFiles(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("安全事故附件查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故附件查询错误！"));
    }
    
    
    
    /**
	 * @Description: 安全事故-添加
	 * @param id
	 * @return
	 */
    @PostMapping("saveSecurityIncidents")
    @ApiOperation(value = "安全事故添加接口", notes = "安全事故添加查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "incidentTitle", value = "事故标题", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "occurrenceTime", value = "发生时间（yyyy-MM-dd HH:mm:ss）", dataType = "Date"),
	    @ApiImplicitParam(paramType = "query", name = "locality", value = "发生地点", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "afterBriefly", value = "概要经过", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "personalInjury", value = "人身伤害情况", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "powerFacilitiesDamaged", value = "电力设施损坏情况", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "otherFacilitiesDamaged", value = "其他损坏情况", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "adverseSocialInfluence", value = "不良社会影响", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "incidentStatus", value = "状态(0待审核 1通过  2不通过)", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "其他情况", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveSecurityIncidents(HttpServletRequest request,SecurityIncidentsVO bean) {
        try {
        	//获取前端上传的文件列表
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            StringBuffer sb=null;
            if(files!=null && files.size()>0) {
            	sb=new StringBuffer();
            	//保存图片
            	for(MultipartFile fl: files) {
        	        if (fl.getContentType().contains(Constants.IMAGE)) {
        	        	String temp = "image/";
    	                // 获取图片的文件名
    	                String fileName = fl.getOriginalFilename();
    	                long fileSize = fl.getSize();
    	                // 获取图片的扩展名
    	                String extensionName = fileName.substring(fileName.indexOf("."));
    	                // 新的图片文件名 = 获取时间戳+"."图片扩展名
    	                String newFileName = UUID.randomUUID().toString().replaceAll("-", "")  + extensionName;
    	                // 数据库保存的目录
    	                String datdDirectory = temp.concat(newFileName);
    	                //上传文件到sftp
    	                boolean flag = SFtpUtil.uploadFile( config,datdDirectory, fl.getInputStream());
        	        	if(flag) {
        	        		//上传成功，
        	        		sb.append(datdDirectory).append(",,").append(fileName).append(",,").append(fileSize).append("&&");
        	        	}
        	        }
            	}
            }
            //拼接路径
            if(sb!=null) {
            	bean.setFilePath(sb.toString().trim());
            }
        	Result<?> result = this.securityIncidentsService.saveSecurityIncidents(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("安全事故添加错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故添加错误！"));
    }
    
    
    /**
	 * @Description: 安全事故-修改
	 * @param id
	 * @return
	 */
    @PutMapping("updateSecurityIncidents")
    @ApiOperation(value = "安全事故修改接口", notes = "安全事故修改查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true),
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "incidentTitle", value = "事故标题", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "occurrenceTime", value = "发生时间", dataType = "Date"),
    	    @ApiImplicitParam(paramType = "query", name = "locality", value = "发生地点", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "afterBriefly", value = "概要经过", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "personalInjury", value = "人身伤害情况", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "powerFacilitiesDamaged", value = "电力设施损坏情况", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "otherFacilitiesDamaged", value = "其他损坏情况", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "adverseSocialInfluence", value = "不良社会影响", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "incidentStatus", value = "状态(0待审核 1通过  2不通过)", dataType = "int", required =true ),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "其他情况", dataType = "String")
    })
    public ResponseEntity<Result<?>> updateSecurityIncidents(HttpServletRequest request, SecurityIncidentsVO bean) {
        try {
        	/***
        	//获取前端上传的文件列表
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            StringBuffer sb=null;
            if(files!=null && files.size()>0) {
            	sb=new StringBuffer();
            	//保存图片
            	for(MultipartFile fl: files) {
        	        if (fl.getContentType().contains(Constants.IMAGE)) {
        	        	String temp = "image/";
    	                // 获取图片的文件名
    	                String fileName = fl.getOriginalFilename();
    	                // 获取图片的扩展名
    	                String extensionName = fileName.substring(fileName.indexOf("."));
    	                // 新的图片文件名 = 获取时间戳+"."图片扩展名
    	                String newFileName = UUID.randomUUID().toString().replaceAll("-", "")  + extensionName;
    	                // 数据库保存的目录
    	                String datdDirectory = temp.concat(newFileName);
    	                //上传文件到sftp
    	                boolean flag = SFtpUtil.uploadFile( config,datdDirectory, fl.getInputStream());
        	        	if(flag) {
        	        		//上传成功
        	        		sb.append(datdDirectory).append(",,").append(fileName).append("&&");
        	        	}
        	        }
            	}
            }
            //拼接路径
            if(sb!=null) {
            	bean.setFilePath(sb.toString().trim());
            }
            */
        	Result<?> result = this.securityIncidentsService.updateSecurityIncidents(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("安全事故修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故修改错误！"));
    }
    
 
    /**
	 * @Description: 安全事故-删除附件
	 * @param id
	 * @return
	 */
    @DeleteMapping("deleteSecurityIncidentsByFiles")
    @ApiOperation(value = "安全事故附件删除接口（单删除）", notes = "安全事故附件删除")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "filePath", value = "文件路径", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> deleteSecurityIncidentsByFiles(SecurityIncidentsVO bean) {
        try {
        	//删除文件
            boolean flag = SFtpUtil.deleteFile(config,bean.getFilePath());
        	if(flag) {
        		//删除数据库表记录
        		Result<?> result = this.securityIncidentsService.deleteSecurityIncidentsByFiles(bean);
            	return ResponseEntity.status(HttpStatus.OK).body(result);
        	}
        	return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "事故附件删除错误！"));
        } catch (Exception e) {
            log.error("安全事故附件删除错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故附件删除错误！"));
    }
    
    /**
	 * @Description: 安全事故-下载附件
	 * @author WCH
	 * @param id
	 * @return
	 */
    @PostMapping("downloadByFiles")
    @ApiOperation(value = "安全事故附件下载接口", notes = "安全事故附件下载接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "filePath", value = "文件路径", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true)
    })
    public ResponseEntity<Object> downloadByFiles(SecurityIncidentsVO bean) {
        try {
        	//通过主键,路径查询附件信息
        	FileUploadRecord fur=this.securityIncidentsService.selectFileUploadRecord(bean);
        	//下载文件
            File file = SFtpUtil.downloadFile(config,bean.getFilePath());
            //获取原始文件名称
            String fileName = fur.getRemark();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment;filename=\"%s", fileName));
            headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
            return responseEntity;
        } catch (Exception e) {
            log.error("安全事故附件下载错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故附件下载错误！"));
    }
    
}
