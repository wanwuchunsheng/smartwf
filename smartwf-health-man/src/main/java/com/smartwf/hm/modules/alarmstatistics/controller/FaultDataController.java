package com.smartwf.hm.modules.alarmstatistics.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.service.FaultDataService;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Deprecated 实时故障数据
 *     故障、报警、缺陷实时数据api接口
 * @author WCH
 * */
@RestController
@RequestMapping("smartwf_health/faultdata")
@Slf4j
@Api(description = "实时故障/缺陷数据控制器")
public class FaultDataController {
	
	@Autowired
	private FaultDataService faultDataService;
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	
	@Autowired
	private DefectService defectService;
	
	@Value("${web.upload-path}")
	private String localFilePath;  //获取上传地址
	
	/**
	 * @Description: 实时故障报警数据
	 * @return
	 */
    @PostMapping("saveFaultInformation")
    @ApiOperation(value = "故障报警数据录入接口", notes = "故障报警数据录入")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "事变等级(0危急 1严重 2一般 3未知)", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "报警来源(0故障 1报警 2人工缺陷)", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "manufacturers", value = "厂家", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "故障起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ,required = true),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "故障截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> saveFaultInformation(FaultInformation bean) {
        try {
        	this.faultDataService.saveFaultInformation(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
        } catch (Exception e) {
            log.error("保存实时故障报警信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"保存实时故障报警信息错误！"));
    }
    
    
    /**
	 * @Description: 缺陷工单录入接口
	 * @return
	 */
    @PostMapping(value = "saveDefect", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = " 缺陷工单录入接口", notes = " 缺陷工单录入")
    @ApiImplicitParams({
	    	@ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String",required = true),
		    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String",required = true),
		    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "事变等级(0危急 1严重 2一般 3未知)", dataType = "int",required = true),
		    @ApiImplicitParam(paramType = "query", name = "incidentType", value = "事变类型(1故障类型 2缺陷类型)", dataType = "int",required = true),
		    @ApiImplicitParam(paramType = "query", name = "faultType", value = "报警来源(0故障 1报警 2人工缺陷)", dataType = "int",required = true),
		    @ApiImplicitParam(paramType = "query", name = "manufacturers", value = "厂家", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String",required = true),
		    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
		    @ApiImplicitParam(paramType = "query", name = "startTime", value = "故障起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ,required = true),
	        @ApiImplicitParam(paramType = "query", name = "endTime", value = "故障截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
	        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String" ),
	        @ApiImplicitParam(paramType = "query", name = "discovererName", value = "发现人姓名（逗号拼接）", dataType = "String" ),
	        @ApiImplicitParam(paramType = "query", name = "discovererId", value = "发现人id（逗号拼接）", dataType = "String" )
    })
    public ResponseEntity<Result<?>> saveDefect(DefectVO bean,HttpServletRequest request) {
        try {
        	//获取前端上传的文件列表
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            StringBuffer sb=null;
            if(files!=null && files.size()>0) {
            	sb=new StringBuffer();
            	//保存图片
            	for(MultipartFile fl: files) {
        	        if (fl.getContentType().contains("image")) {
    	                String temp = "images" + File.separator ;
    	                // 获取图片的文件名
    	                String fileName = fl.getOriginalFilename();
    	                // 获取图片的扩展名
    	                String extensionName = fileName.substring(fileName.indexOf("."));
    	                // 新的图片文件名 = 获取时间戳+"."图片扩展名
    	                String newFileName = String.valueOf(System.currentTimeMillis())  + extensionName;
    	                // 数据库保存的目录
    	                String datdDirectory = temp.concat(newFileName);
    	                // 文件路径
    	                String filePath = localFilePath+File.separator+temp;
    	                File dest = new File(filePath, newFileName);
    	                if (!dest.getParentFile().exists()) {
    	                    dest.getParentFile().mkdirs();
    	                }
    	                // 上传到指定目录
    	                fl.transferTo(dest);
    	                sb.append(datdDirectory).append(",");
        	        }
            	}
            }
            //拼接路径
            if(sb!=null) {
            	bean.setFilePath(sb.toString().trim());
            }
        	this.defectService.saveDefect(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
        } catch (Exception e) {
            log.error("保存实时故障报警信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"保存实时故障报警信息错误！"));
    }
    
    /**
	 * @Description: 初始化故障数据
	 * @return
	 */
    @PostMapping("initFaultInformation")
    @ApiOperation(value = "初始化故障数据接口", notes = "初始化故障数据")
    public ResponseEntity<Result<?>> initFaultInformation() {
        try {
        	this.alarmInboxService.selectFaultInformationByAll();
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
        } catch (Exception e) {
            log.error("初始化故障数据信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"初始化故障数据信息错误！"));
    }
    
    /**
	 * @Description: 初始化缺陷数据
	 * @return
	 */
    @PostMapping("initDefectAll")
    @ApiOperation(value = "初始化缺陷数据接口", notes = "初始化缺陷数据")
    public ResponseEntity<Result<?>> initDefectAll() {
        try {
        	this.defectService.initDefectAll();
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
        } catch (Exception e) {
            log.error("初始化缺陷数据信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"初始化缺陷数据信息错误！"));
    }

}
