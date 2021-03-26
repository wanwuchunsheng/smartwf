package com.smartwf.hm.modules.healthstatistics.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.healthstatistics.pojo.FanStatus;
import com.smartwf.hm.modules.healthstatistics.service.FanStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WCH
 * @Date: 2019-10-25 15:04:26
 * @Description: 健康分析控制器
 */
@RestController
@RequestMapping("fanstatus")
@Slf4j
@Validated
@Api(description = "健康分析（统计）控制器")
public class FanStatusController {
	
	@Autowired
	private FanStatusService fanStatusService;
	
	/**
	 * @Description: 监视中心 - 统计风机运行状态
	 *    统计结果信息
	 * @param tenantDomain
	 * @param windFarm
	 * @author WCH
	 * @Datetime 2021-3-26 10:26:07
	 * @return
	 */
    @PostMapping("selectFanRunStatusByCount")
    @ApiOperation(value = "统计风机运行状态接口", notes = "统计风机运行状态查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场ID", dataType = "String",required = true )
    })
    public ResponseEntity<Result<?>> selectFanRunStatusByCount(FanStatus bean,
    		@NotNull(message ="租户不能为空！" ) String tenantDomain,@NotNull(message = "风场ID不能为空！" ) String windFarm ) {
        try {
        	Result<?> result = this.fanStatusService.selectFanRunStatusByCount(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("统计风机运行状态错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("统计风机运行状态错误！"));
    }

}
