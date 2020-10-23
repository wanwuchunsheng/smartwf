package com.smartwf.sm.modules.sysconfig.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.SysProvince;
import com.smartwf.sm.modules.sysconfig.service.ProCityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 省市控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("procity")
@Slf4j
@Api(description ="省市县/区级联控制器")
public class ProCityController {
	
	@Autowired
	private ProCityService proCityService;

	
	/**
	 * @Description: 省查询
	 * @param bean
	 * @return
	 */
    @GetMapping("selectProAll")
    @ApiOperation(value = "省查询（全部）接口", notes = "省查询接口信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "主键编码", dataType = "String"),
    })
    public ResponseEntity<Result<?>> selectProAll( SysProvince bean) {
        try {
            Result<?> result = this.proCityService.selectProAll( bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询省信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询省信息错误！"));
    }
    
    /**
	 * @Description: 市查询 -通过省id或者市ID查询
	 * @param proId , cityId
	 * @return
	 */
    @GetMapping("selectCityByProId")
    @ApiOperation(value = "省市关联查询接口", notes = "省市关联查询接口信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "proCode", value = "省编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectCityByProId( String proCode,String cityCode) {
        try {
            Result<?> result = this.proCityService.selectCityByProId( proCode,cityCode);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("省市关联查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("省市关联查询错误！"));
    }
    
    /**
	 * @Description: 县/区查询 -通过市Id，省ID
	 * @param proId , cityId
	 * @return
	 */
    @GetMapping("selectAreaByCityId")
    @ApiOperation(value = "县/区关联查询接口", notes = "县/区关联查询接口信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "cityCode", value = "市编码", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectAreaByCityId(String cityCode) {
        try {
            Result<?> result = this.proCityService.selectAreaByCityId(cityCode);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("县/区关联查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("县/区关联查询错误！"));
    }
}
