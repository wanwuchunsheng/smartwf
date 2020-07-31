package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.service.RouteService;

import io.swagger.annotations.Api;
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
    
    
}
