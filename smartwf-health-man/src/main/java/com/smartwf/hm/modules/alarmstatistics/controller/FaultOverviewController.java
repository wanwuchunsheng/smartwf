package com.smartwf.hm.modules.alarmstatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.service.FaultOverviewService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 故障总览控制器
 */
@RestController
@RequestMapping("faultoverview")
@Slf4j
@Api(description = "故障总览控制器")
public class FaultOverviewController {
	
	@Autowired
	FaultOverviewService faultOverviewService;

	
	/**
	 * @Description: 故障类型统计 
	 *  @param faultType  0-故障    1-预警   2-人工提交（缺陷）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectFaultTypeByDate")
    @ApiOperation(value = "故障类型统计接口", notes = "故障类型统计查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间", dataType = "Date", required = true ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间", dataType = "Date", required = true )
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
	 * @Description: 故障等级分布统计 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectFaultDistrByDate")
    @ApiOperation(value = "故障分布统计接口", notes = "故障分布计查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间", dataType = "Date", required = true ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间", dataType = "Date", required = true )
    })
    public ResponseEntity<Result<?>> selectFaultDistrByDate(FaultInformationVO bean) {
        try {
            Result<?> result = this.faultOverviewService.selectFaultDistrByDate(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("故障分布统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障分布统计信息错误！"));
    }

}
