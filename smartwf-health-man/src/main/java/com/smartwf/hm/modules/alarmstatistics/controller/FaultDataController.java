package com.smartwf.hm.modules.alarmstatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.FaultDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Deprecated 实时故障数据
 *     故障、报警、缺陷实时数据api接口
 * @author WCH
 * */
@RestController
@RequestMapping("api/faultdata")
@Slf4j
@Api(description = "实时故障/缺陷数据控制器")
public class FaultDataController {
	
	@Autowired
	private FaultDataService faultDataService;
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	
	/**
	 * @Description: 实时故障报警缺陷数据
	 *     故障、报警、缺陷实时数据
	 * @return
	 */
    @PostMapping("saveFaultInformation")
    @ApiOperation(value = "故障报警缺陷数据保存接口", notes = "故障报警缺陷数据保存")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "事变等级(0危急 1严重 2一般 3未知)", dataType = "int",required = true),
    	    //@ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "事变类型(1故障类型 2缺陷类型)", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "报警来源(0故障 1报警 2人工缺陷)", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "manufacturers", value = "厂家", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String",required = true),
    	    //@ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1处理中 2已处理 3已关闭)", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "故障起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ,required = true),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "故障截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> saveFaultInformation(FaultInformation bean) {
        try {
        	this.faultDataService.saveFaultInformation(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("成功"));
        } catch (Exception e) {
            log.error("保存实时故障报警信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("保存实时故障报警信息错误！"));
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
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("成功"));
        } catch (Exception e) {
            log.error("初始化故障数据信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("初始化故障数据信息错误！"));
    }

}
