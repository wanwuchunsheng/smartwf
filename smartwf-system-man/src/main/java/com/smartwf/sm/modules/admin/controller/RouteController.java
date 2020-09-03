package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.PortalPowerGeneration;
import com.smartwf.sm.modules.admin.service.RouteService;
import com.smartwf.sm.modules.admin.vo.PortalPowerGenerationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
     * 门户发电量统计数据 -列表
     * @author WCH
     * @param bean
     * @param page
     * @return
     * 
     */
    @GetMapping("/selectPortalPowerGenByAll")
    @ApiOperation(value = "发电量分页查询接口", notes = "发电量分页查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
        @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
    	@ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectPortalPowerGenByAll(Page<PortalPowerGeneration> page,PortalPowerGenerationVO bean) {
        try {
            Result<?> result = this.routeService.selectPortalPowerGenByAll(page,bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("发电量分页查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("发电量分页查询错误！"));
    }
    
    /**
     * 门户发电量统计数据  - 删除
     * @author WCH
     * @param bean
     * @return
     */
    @DeleteMapping("/deletePortalPowerGenById")
    @ApiOperation(value = "删除发电量接口", notes = "删除发电量信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> deletePortalPowerGenById(PortalPowerGeneration bean) {
        try {
            this.routeService.deletePortalPowerGenById(bean);
            return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "删除成功！"));
        } catch (Exception e) {
            log.error("删除发电量信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除发电量信息错误！"));
    }
    
    
    /**
     * 门户发电量统计数据 -添加
     * @author WCH
     * @param bean
     * @return
     * 
     */
    @PostMapping("/addPortalPowerGen")
    @ApiOperation(value = "发电量添加接口", notes = "发电量添加")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "dailyElectPg", value = "日发电量", dataType = "Double", required = true),
    	@ApiImplicitParam(paramType = "query", name = "realTimePg", value = "实时容量", dataType = "Double", required = true),
    	@ApiImplicitParam(paramType = "query", name = "installedCapacity", value = "装机容量", dataType = "Double", required = true),
    	@ApiImplicitParam(paramType = "query", name = "createTime", value = "开始时间", dataType = "Date")
    })
    public ResponseEntity<Result<?>> addPortalPowerGen(PortalPowerGeneration bean) {
        try {
            Result<?> result = this.routeService.addPortalPowerGen(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("发电量添加错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("发电量添加错误！"));
    }
    
    /**
     * 门户发电量统计数据 -修改
     * @author WCH
     * @param bean
     * @return
     * 
     */
    @PutMapping("/updatePortalPowerGen")
    @ApiOperation(value = "发电量修改接口", notes = "发电量修改")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "Integer"),
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "dailyElectPg", value = "日发电量", dataType = "Double"),
    	@ApiImplicitParam(paramType = "query", name = "realTimePg", value = "实时容量", dataType = "Double"),
    	@ApiImplicitParam(paramType = "query", name = "installedCapacity", value = "装机容量", dataType = "Double"),
    	@ApiImplicitParam(paramType = "query", name = "createTime", value = "开始时间", dataType = "Date")
    })
    public ResponseEntity<Result<?>> updatePortalPowerGen(PortalPowerGeneration bean) {
        try {
            Result<?> result = this.routeService.updatePortalPowerGen(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("发电量修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("发电量修改错误！"));
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
    	@ApiImplicitParam(paramType = "query", name = "String", value = "风场", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "createTime", value = "日期时间（yyyy-MM-dd）", dataType = "Date")
    })
    public ResponseEntity<Result<?>> selectPortalPowerGenByParam(PortalPowerGenerationVO bean) {
        try {
            Result<?> result = this.routeService.selectPortalPowerGenByParam(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("发电量统计查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("发电量统计查询错误！"));
    }
    
}
