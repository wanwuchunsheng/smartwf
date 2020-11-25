package com.smartwf.hm.modules.alarmstatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.FaultOverviewService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WCH
 * @Date: 2019-10-25 15:04:26
 * @Description: 故障总览控制器
 */
@RestController
@RequestMapping("faultoverview")
@Slf4j
@Api(description = "故障总览控制器")
public class FaultOverviewController {
	
	@Autowired
	private FaultOverviewService faultOverviewService;

	
	/**
	 * @Description: 故障类型统计 
	 *  @param incidentType 1-故障 2-缺陷  3-告警
	 *  @param faultType  0-故障    1-预警   2-人工提交（缺陷）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectFaultTypeByDate")
    @ApiOperation(value = "故障类型统计接口", notes = "故障类型统计查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "stime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true ),
            @ApiImplicitParam(paramType = "query", name = "etime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true )
    })
    public ResponseEntity<Result<?>> selectFaultTypeByDate(FaultInformationVO bean) {
        try {
            Result<?> result = this.faultOverviewService.selectFaultTypeByDate(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("故障类型统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障类型统计信息错误！"));
    }
    
    
    /**
	 * @Description: 故障处理状态
	 * @param alarmStatus 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectFaultStatusByDate")
    @ApiOperation(value = "故障处理状态接口", notes = "故障处理状态查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "incidentType", value = "事变类型{1故障  2警告  3缺陷}", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "stime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true ),
            @ApiImplicitParam(paramType = "query", name = "etime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true )
    })
    public ResponseEntity<Result<?>> selectFaultStatusByDate(FaultInformationVO bean) {
        try {
            Result<?> result = this.faultOverviewService.selectFaultStatusByDate(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("故障处理状态&部件故障分析错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障处理状态&部件故障分析错误！"));
    }
    
    /**
	 * @Description: 处理效率统计
	 * @param alarmStatus 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectProcessingEfficByDate")
    @ApiOperation(value = "处理效率统计", notes = "处理效率统计")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "stime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true ),
            @ApiImplicitParam(paramType = "query", name = "etime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true )
    })
    public ResponseEntity<Result<?>> selectProcessingEfficByDate(FaultInformationVO bean) {
        try {
            Result<?> result = this.faultOverviewService.selectProcessingEfficByDate(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("处理效率统计错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("处理效率统计错误！"));
    }
    
    
    
    /**
	 * @Description: 故障等级分布统计
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectFaultLevelByDate")
    @ApiOperation(value = "故障等级分布统计接口", notes = "故障等级分布统计查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "stime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true ),
            @ApiImplicitParam(paramType = "query", name = "etime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date", required = true )
    })
    public ResponseEntity<Result<?>> selectFaultLevelByDate(FaultInformationVO bean) {
        try {
            Result<?> result = this.faultOverviewService.selectFaultLevelByDate(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("故障等级分布统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障等级分布统计信息错误！"));
    }

    /**
     * 说明：门户故障/缺陷/警告 统计
     * @param bean
     * @return
     * */
    @PostMapping("selectFaultByAlarmStatus")
    @ApiOperation(value = "门户（故障/缺陷/警告）统计", notes = "故障处理统计查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectFaultByAlarmStatus(Page<FaultInformation> page,FaultInformationVO bean) {
        try {
            Result<?> result = this.faultOverviewService.selectFaultByAlarmStatus(page,bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("故障处理统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障处理统计信息错误！"));
    }

    /**
   	 * @Description: (故障、缺陷、警告)未处理记录统计
   	 * @author WCH
   	 * @dateTime 2020-7-20 17:55:35
   	 * @param bean
   	 * @return
   	 */
    @PostMapping("selectFaultRecordByIncidentType")
    @ApiOperation(value = "（故障、缺陷、警告）未处理记录统计接口", notes = "故障、缺陷、警告未处理记录统计{右上角通知警告提醒}")
    @ApiImplicitParams({
   	      @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
          @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectFaultRecordByIncidentType(FaultInformationVO bean) {
       try {
       	 Result<?> result =this.faultOverviewService.selectFaultRecordByIncidentType(bean);
       	 return ResponseEntity.status(HttpStatus.OK).body(result);
       } catch (Exception e) {
           log.error("(故障、缺陷、警告)未处理记录统计错误！{}", e.getMessage(), e);
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("(故障、缺陷、警告)未处理记录统计错误！"));
    }
}
