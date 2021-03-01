package com.smartwf.sm.modules.admin.controller;

import java.util.Map;

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
import com.smartwf.sm.modules.sysconfig.pojo.TenantConfig;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;

import cn.hutool.core.util.StrUtil;
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
    
    
    /**
     * 门户天气查询
     * @author WCH
     * @param bean
     * @return
     * 
     */
    @PostMapping("/selectPortalWeatherByParam")
    @ApiOperation(value = "天气查询接口", notes = "天气查询查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户Id", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> selectPortalWeatherByParam(TenantConfig bean) {
        try {
            Result<?> result = this.routeService.selectPortalWeatherByParam(bean);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("天气查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( Constants.BAD_REQUEST ,"天气查询错误！"));
    }
    
    /**
     * 门户-省份风场统计
     * @author WCH
     * @param bean
     * @return
     */
    @GetMapping("/selectWindfarmConfigByProCode")
    @ApiOperation(value = "省份风场统计接口", notes = "省份风场统计查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户Id", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> selectWindfarmConfigByProCode(WindfarmConfig bean) {
        try {
            Result<?> result = this.routeService.selectWindfarmConfigByProCode(bean);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("省份风场统计错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( Constants.BAD_REQUEST ,"省份风场统计错误！"));
    }
    
    /**
     * 健康中心 -人员/角色查询
     * @author WCH
     * @param tenantDomain
     * @param windFarm
     * @return
     */
    @PostMapping("/selectWindfarmUserAndRole")
    @ApiOperation(value = "用户/角色查询列表接口", notes = "用户角色查询列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "farmId", value = "风场ID", dataType = "String", required = true)
    })
    public Map<String,Object> selectWindfarmUserAndRole(String tenantDomain,String farmId) {
        try {
        	if(StrUtil.isNotBlank(tenantDomain) && StrUtil.isNotBlank(farmId)) {
        		Map<String,Object> map = this.routeService.selectWindfarmUserAndRole(tenantDomain,farmId);
                return map;
        	}
        } catch (Exception e) {
            log.error("用户角色查询列表错误！{}", e.getMessage(), e);
        }
        return null;
    }
    
    
    
    
    
    
    
    
}
