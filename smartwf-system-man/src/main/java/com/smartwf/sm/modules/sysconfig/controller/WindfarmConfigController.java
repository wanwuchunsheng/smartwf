package com.smartwf.sm.modules.sysconfig.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;
import com.smartwf.sm.modules.sysconfig.service.WindFarmConfigService;
import com.smartwf.sm.modules.sysconfig.vo.WindfarmConfigVO;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 风场配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("windfarmconf")
@Slf4j
@Api(description ="风场(场站)配置控制器")
public class WindfarmConfigController {

	@Autowired
	private WindFarmConfigService windFarmConfigService;
	
	/**
	 * @Description: 查询风场配置分页
	 * @return
	 */
    @GetMapping("selectWindFarmConfigByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询风场配置信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键（租户下拉主键）", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场（组织架构风场ID）", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarmTitle", value = "风场名称（组织架构风场名称）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "kks", value = "kks编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "realTimeCapacity", value = "实时容量", dataType = "Double"),
    	    @ApiImplicitParam(paramType = "query", name = "installedCapacity", value = "装机容量", dataType = "Double"),
    	    @ApiImplicitParam(paramType = "query", name = "dailyGeneration", value = "日发电量", dataType = "Double"),
    	    @ApiImplicitParam(paramType = "query", name = "cumulativeGeneration", value = "累计发电量", dataType = "Double"),
    	    @ApiImplicitParam(paramType = "query", name = "availability", value = "可利用率", dataType = "Double"),
    	    @ApiImplicitParam(paramType = "query", name = "equivalentUtilHours", value = "等效利用小时", dataType = "Double"),
    	    @ApiImplicitParam(paramType = "query", name = "planMonthGeneration", value = "计划月发电量", dataType = "double",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "annualOutput", value = "年发电量", dataType = "Double",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "planAnnualOutput", value = "计划年发电量", dataType = "Double",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "省编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "areaCode", value = "县/区编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "latitude", value = "纬度", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "longitude", value = "经度", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "geoJson", value = "GeoJson数据", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "generatingSet", value = "机组", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "powerGeneration", value = "发电", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "standBy", value = "待机", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "malfunctions", value = "故障", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "maintenance", value = "维护", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "powerRestriction", value = "限电", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "offLine", value = "离线", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "status", value = "状态（是否启用人工更新数据）0否  1是", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectTenantConfigByPage(Page<WindfarmConfig> page, WindfarmConfigVO bean) {
        try {
            Result<?> result = this.windFarmConfigService.selectWindFarmConfigByPage(page, bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询风场配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询风场配置错误！"));
    }
    
    /**
     * @Description: 主键查询风场配置
     * @return
     */
    @GetMapping("selectWindFarmConfigById")
    @ApiOperation(value = "主键查询风场配置接口", notes = "主键查询风场配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键（租户下拉主键）", dataType = "int",required = true),
        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场主键（组织机构主键）", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectWindFarmConfigById(WindfarmConfig bean) {
        try {
            Result<?> result = this.windFarmConfigService.selectWindFarmConfigById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询风场配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询风场配置错误！"));
    }
    
    /**
     * @Description: 添加风场配置
     * @return
     */
    @PostMapping("saveWindFarmConfig")
    @ApiOperation(value = "添加风场配置接口", notes = "添加风场配置接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "Integer"),
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场（组织架构风场ID）", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarmTitle", value = "风场名称（组织架构风场名称）", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "kks", value = "kks编码", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "realTimeCapacity", value = "实时容量", dataType = "double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "installedCapacity", value = "装机容量", dataType = "double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "dailyGeneration", value = "日发电量", dataType = "double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "monthGeneration", value = "月发电量", dataType = "double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "cumulativeGeneration", value = "累计发电量", dataType = "double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "availability", value = "可利用率", dataType = "Double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "equivalentUtilHours", value = "等效利用小时", dataType = "Double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "planMonthGeneration", value = "计划月发电量", dataType = "double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "annualOutput", value = "年发电量", dataType = "Double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "planAnnualOutput", value = "计划年发电量", dataType = "Double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "省编码", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "areaCode", value = "县/区编码", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "latitude", value = "纬度", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "longitude", value = "经度", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "geoJson", value = "GeoJson数据", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "generatingSet", value = "机组", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "powerGeneration", value = "发电", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "standBy", value = "待机", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "malfunctions", value = "故障", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "maintenance", value = "维护", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "powerRestriction", value = "限电", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "offLine", value = "离线", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "type", value = "类型（0 场站  1光伏）", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "status", value = "状态（是否启用人工更新数据）0否  1是", dataType = "int",required = true)
    })
    @TraceLog(content = "添加风场配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveWindFarmConfig( WindfarmConfig bean) {
		Result<?> result = this.windFarmConfigService.saveWindFarmConfig(bean);
    	return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    /**
     * @Description： 修改风场配置
     * @return
     */
    @PutMapping("updateWindFarmConfig")
    @ApiOperation(value = "修改风场配置接口", notes = "修改风场配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "Integer"),
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场（组织架构风场ID）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "windFarmTitle", value = "风场名称（组织架构风场名称）", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "kks", value = "kks编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "realTimeCapacity", value = "实时容量", dataType = "Double"),
	    @ApiImplicitParam(paramType = "query", name = "installedCapacity", value = "装机容量", dataType = "Double"),
	    @ApiImplicitParam(paramType = "query", name = "dailyGeneration", value = "日发电量", dataType = "Double"),
	    @ApiImplicitParam(paramType = "query", name = "monthGeneration", value = "月发电量", dataType = "Double"),
	    @ApiImplicitParam(paramType = "query", name = "cumulativeGeneration", value = "累计发电量", dataType = "Double"),
	    @ApiImplicitParam(paramType = "query", name = "availability", value = "可利用率", dataType = "Double"),
	    @ApiImplicitParam(paramType = "query", name = "equivalentUtilHours", value = "等效利用小时", dataType = "Double"),
	    @ApiImplicitParam(paramType = "query", name = "planMonthGeneration", value = "计划月发电量", dataType = "double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "annualOutput", value = "年发电量", dataType = "Double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "planAnnualOutput", value = "计划年发电量", dataType = "Double",required = true),
	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "省编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "areaCode", value = "县/区编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "latitude", value = "纬度", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "longitude", value = "经度", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "geoJson", value = "GeoJson数据", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "generatingSet", value = "机组", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "powerGeneration", value = "发电", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "standBy", value = "待机", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "malfunctions", value = "故障", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "maintenance", value = "维护", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "powerRestriction", value = "限电", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "offLine", value = "离线", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "type", value = "类型（0 场站  1光伏）", dataType = "int"),
	    @ApiImplicitParam(paramType = "query", name = "status", value = "状态（是否启用人工更新数据）0否  1是", dataType = "Integer")
    })
    @TraceLog(content = "修改风场配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateWindFarmConfig(WindfarmConfig bean) {
    	if(bean.getId()==null) {
    		this.windFarmConfigService.saveWindFarmConfig(bean);
    	}else {
    		this.windFarmConfigService.updateWindFarmConfig(bean);
    	}
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
    }
    
    /**
     * @Description： 删除风场配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteTenantConfig")
    @ApiOperation(value = "删除接口", notes = "删除风场配置")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除风场配置系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteWindFarmConfig(WindfarmConfigVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
        this.windFarmConfigService.deleteWindFarmConfig(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }
}
