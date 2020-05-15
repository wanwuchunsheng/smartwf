package com.smartwf.hm.modules.knowledgebase.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultSolutionInfo;
import com.smartwf.hm.modules.knowledgebase.service.FaultSolutionInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 故障解决方案控制器
 */
@RestController
@RequestMapping("solution")
@Slf4j
@Api(description = "故障解决方案控制器")
public class FaultSolutionInfoController {
	
	@Autowired
	private FaultSolutionInfoService faultSolutionInfoService;
	
	/**
	 * @Description: 故障解决方案批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
    @PostMapping("saveFaultSolutionInfo")
    @ApiOperation(value = "故障解决方案批量录入接口", notes = "故障解决方案批量录入")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "故障解决方案（JOSN字符串）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> saveFaultSolutionInfo(FaultSolutionInfo bean) {
        try {
        	//验证参数
        	if(StringUtils.isBlank(bean.getTenantCode()) || StringUtils.isBlank(bean.getRemark())) {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"参数为空！"));
        	}
        	//保存
        	this.faultSolutionInfoService.saveFaultSolutionInfo(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
        } catch (Exception e) {
            log.error("保存故障解决方案批量录入错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"保存故障解决方案批量录入错误！"));
    }
    
    /**
     * @Description： 故障解决方案删除
     * @param faultCode
     * @return
     */
    @DeleteMapping("deleteFaultSolutionInfo")
    @ApiOperation(value = "故障解决方案删除接口", notes = "故障解决方案删除")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "faultCode", value = "故障代码", dataType = "String",required = true)
    })
    @TraceLog(content = "故障解决方案删除", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteFaultSolutionInfo(FaultSolutionInfo bean) {
    	try {
    		 this.faultSolutionInfoService.deleteFaultSolutionInfo(bean);
    	        return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"删除成功"));
		} catch (Exception e) {
			log.error("故障解决方案删除错误！{}", e.getMessage(), e);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"故障解决方案删除错误！"));
    }
    
    /**
     * @Description： 故障解决方案(主键)删除
     *   主键删除
     * @return
     */
    @DeleteMapping("deleteFaultSolutionInfoById")
    @ApiOperation(value = "故障解决方案（主键）删除接口", notes = "故障解决方案（主键）删除")
    @ApiImplicitParams({
    	  @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	  @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true)
    })
    @TraceLog(content = "故障解决方案（主键）删除", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteFaultSolutionInfoById(FaultCodeBase bean) {
    	try {
    		this.faultSolutionInfoService.deleteFaultSolutionInfoById(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"删除成功"));
		} catch (Exception e) {
			log.error("故障解决方案（主键）删除错误！{}", e.getMessage(), e);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"故障解决方案（主键）删除错误！"));
    }

    /**
     * @Description：故障解决方案(故障代码)查询
     * @author WCH
     * @datetime 2020-5-14 15:38:29
     * @return
     */
    @GetMapping("selectFaultSolInfoByFaultCode")
    @ApiOperation(value = "故障解决方案（故障代码）查询接口", notes = "故障解决方案（故障代码）查询")
    @ApiImplicitParams({
    	  @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	  @ApiImplicitParam(paramType = "query", name = "faultCode", value = "故障代码", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectFaultSolInfoByFaultCode(FaultSolutionInfo bean) {
    	try {
    		Result<?> result=this.faultSolutionInfoService.selectFaultSolInfoByFaultCode(bean);
    		return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			log.error("故障解决方案（故障代码）查询错误！{}", e.getMessage(), e);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"故障解决方案（故障代码）查询错误！"));
    }
    
}





