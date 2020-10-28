package com.smartwf.sm.modules.sysconfig.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.WeatherConfig;
import com.smartwf.sm.modules.sysconfig.service.WeatherConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 天气配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("weatherconf")
@Slf4j
@Api(description ="天气配置控制器")
public class WeatherConfigController {
	
	@Autowired
	private WeatherConfigService  weatherConfigService;
	
	 /**
     * @Description: 添加天气配置
     * @return
     */
    @PostMapping("saveWeatherConfig")
    @ApiOperation(value = "添加天气配置", notes = "添加天气配置接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "apiUrl", value = "api地址", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "apiKey", value = "秘钥", dataType = "String",required = true)
    })
    @TraceLog(content = "添加天气配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveWeatherConfig(HttpServletRequest request, WeatherConfig bean) {
		this.weatherConfigService.saveWeatherConfig(bean);
    	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
    }
    
    /**
     * @Description: 查询天气配置
     * @return
     */
    @GetMapping("selectWeatherConfig")
    @ApiOperation(value = "查询添加配置（固定一条）", notes = "查询添加配置")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "idapiUrl", value = "api地址", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "apiKey", value = "秘钥", dataType = "String")
    })
    @TraceLog(content = "查询天气配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> selectWeatherConfig(WeatherConfig bean) {
    	try {
    		Result<?> result=this.weatherConfigService.selectWeatherConfig(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			log.error("ERROR:查询天气配置数据错误{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询天气配置数据错误！"));
    }

}
