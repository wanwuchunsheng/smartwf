package com.smartwf.hm.modules.alarmstatistics.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.config.ftp.SFtpConfig;
import com.smartwf.hm.config.ftp.SFtpUtil;
import com.smartwf.hm.modules.alarmstatistics.pojo.SecurityIncidents;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.service.SecurityIncidentsService;
import com.smartwf.hm.modules.alarmstatistics.vo.SecurityIncidentsVO;

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
	private DefectService defectService;
	
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
    @PostMapping("selectSecurityIncidentsByPage")
    @ApiOperation(value = "安全事故主键查询接口", notes = "安全事故主键查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
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
    @PostMapping("selectSecurityIncidentsByFiles")
    @ApiOperation(value = "安全事故附件查询接口", notes = "安全事故附件查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
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
	    @ApiImplicitParam(paramType = "query", name = "occurrenceTime", value = "发生时间", dataType = "Date"),
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
        	        		sb.append(datdDirectory).append(",");
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
    @PostMapping("updateSecurityIncidents")
    @ApiOperation(value = "安全事故添加接口", notes = "安全事故添加查询")
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
    	    @ApiImplicitParam(paramType = "query", name = "incidentStatus", value = "状态(0待审核 1通过  2不通过)", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "其他情况", dataType = "String")
    })
    public ResponseEntity<Result<?>> updateSecurityIncidents(HttpServletRequest request, SecurityIncidentsVO bean) {
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
        	        		sb.append(datdDirectory).append(",");
        	        	}
        	        }
            	}
            }
            //拼接路径
            if(sb!=null) {
            	bean.setFilePath(sb.toString().trim());
            }
        	Result<?> result = this.securityIncidentsService.updateSecurityIncidents(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("安全事故修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST, "安全事故修改错误！"));
    }
    
 
}
