package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.service.RouteService;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 门户控制层
 * @author WCH
 */
@RestController
@RequestMapping("route")
@Slf4j
@Api(description = "门户控制器")
public class RouteController {


    @Autowired
    private RouteService routeService;


    /**
     * 门户菜单查询
     * @return
     */
    @GetMapping("selectRouteByAll")
    @ApiOperation(value = "门户菜单接口", notes = "门户菜单查询")
    public ResponseEntity<Result<?>> selectRouteByAll() {
        try {
            Result<?> result = this.routeService.selectRouteByAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("门户菜单查询信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("门户菜单查询信息错误！"));
    }
    
   
    /**
     * 门户发电量统计数据 -发电量统计
     * @author WCH
     * @param bean
     * @return
     * 
     */
    @PostMapping("/selectPortalPowerGenByParam")
    @ApiOperation(value = "发电量统计查询接口", notes = "发电量统计查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectPortalPowerGenByParam(WindfarmConfig bean) {
        try {
            Result<?> result = this.routeService.selectPortalPowerGenByParam(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("发电量统计查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( Constants.BAD_REQUEST ,"发电量统计查询错误！"));
    }
    
    /**
     * 门户状态统计 - 机器状态
     * @author WCH
     * @param bean
     * @return
     * 
     */
    @PostMapping("/selectPortalStatusByParam")
    @ApiOperation(value = "门户状态统计查询接口", notes = "门户状态统计查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectPortalStatusByParam(WindfarmConfig bean) {
        try {
            Result<?> result = this.routeService.selectPortalStatusByParam(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("门户状态统计错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( Constants.BAD_REQUEST ,"门户状态统计错误！"));
    }
    
    /**
     * 门户人员统计 -场站、风场
     * @author WCH
     * @param bean
     * @return
     * 
     */
    @PostMapping("/selectPortalUserByParam")
    @ApiOperation(value = "人员统计查询接口", notes = "人员统计查询查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户Id", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectPortalUserByParam(WindfarmConfig bean) {
        try {
            Result<?> result = this.routeService.selectPortalUserByParam(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("人员统计查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( Constants.BAD_REQUEST ,"人员统计查询错误！"));
    }
    
}
