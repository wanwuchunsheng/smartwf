package com.smartwf.hm.modules.alarmstatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 报警收件箱控制器
 */
@RestController
@RequestMapping("alarminbox")
@Slf4j
@Api(description = "报警收件箱控制器")
public class AlarmInboxController {
	
	@Autowired
	private AlarmInboxService alarmInboxService;

	/**
	 * @Description: 分页查询故障报警信息 
	 *    列表信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectAlarmInforByPage")
    @ApiOperation(value = "故障报警分页查询接口", notes = "故障报警分页查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0严重 1普通 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0故障 1报警 2缺陷)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1处理中 2已处理 3已关闭)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间", dataType = "Date" ),
    	    @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectAlarmInforByPage( Page<FaultInformation> page, FaultInformationVO bean) {
        try {
        	Result<?> result = this.alarmInboxService.selectAlarmInforByPage(page,bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("故障类型统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障类型统计信息错误！"));
    }
    
    /**
	 * @Description: 查询所有故障报警信息 
	 *   今天多少消息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectAlarmInforByAll")
    @ApiOperation(value = "查询所有故障报警接口", notes = "查询所有故障报警")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0严重 1普通 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0故障 1报警 2缺陷)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1处理中 2已处理 3已关闭)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> selectAlarmInforByAll(FaultInformationVO bean) {
        try {
        	Result<?> result = this.alarmInboxService.selectAlarmInforByAll(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("故障类型统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障类型统计信息错误！"));
    }
    
    /**
	 * @Description: 故障报警修改
	 *    重点关注
	 * @param id
	 */
    @PutMapping("updateAlarmInforById")
    @ApiOperation(value = "故障报警修改接口", notes = "故障报警修改")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0严重 1普通 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0故障 1报警 2缺陷)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1处理中 2已处理 3已关闭)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> updateAlarmInforById(FaultInformationVO bean) {
        try {
        	this.alarmInboxService.updateAlarmInforById(bean);
        	 return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("故障报警修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障报警修改错误！"));
    }
    
    /**
	 * @Description: 主键查询
	 *    故障详细
	 * @param id
	 */
    @PutMapping("selectAlarmInforById")
    @ApiOperation(value = "故障报警主键查询接口", notes = "故障报警主键查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectAlarmInforById(FaultInformationVO bean) {
        try {
        	 Result<?> result = this.alarmInboxService.selectAlarmInforById(bean);
        	 return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("故障报警主键查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障报警主键查询错误！"));
    }
    
    /**
	 * @Description: 实时故障报警总数查询
	 *    实时故障总和
	 * @return
	 */
    @GetMapping("selectAlarmsCountByAll")
    @ApiOperation(value = "实时故障报警总数接口", notes = "实时故障报警总数查询")
    public Integer selectAlarmsCountByAll() {
        try {
        	return this.alarmInboxService.selectAlarmsCountByAll();
        } catch (Exception e) {
            log.error("实时故障报警总数信息查询错误！{}", e.getMessage(), e);
        }
		return Constants.ZERO;
    }
    
}
