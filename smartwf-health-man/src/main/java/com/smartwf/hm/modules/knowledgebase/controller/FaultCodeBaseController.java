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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.service.FaultCodeBaseService;
import com.smartwf.hm.modules.knowledgebase.vo.FaultCodeBaseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 故障代码控制器
 */
@RestController
@RequestMapping("faultcode")
@Slf4j
@Api(description = "故障代码控制器")
public class FaultCodeBaseController {
	
	@Autowired
	private FaultCodeBaseService faultCodeBaseService;
	
	/**
	 * @Description: 故障代码批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
    @PostMapping("saveFaultCodeBase")
    @ApiOperation(value = "故障代码批量录入接口", notes = "故障代码批量录入")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "故障代码数据（JOSN字符串）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> saveFaultCodeBase(FaultCodeBase bean) {
        try {
        	//验证参数
        	if(StringUtils.isBlank(bean.getTenantCode()) || StringUtils.isBlank(bean.getRemark())) {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"参数为空！"));
        	}
        	//保存
        	this.faultCodeBaseService.saveFaultCodeBase(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
        } catch (Exception e) {
            log.error("保存故障代码批量录入错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"保存故障代码批量录入错误！"));
    }
   
    /**
     * @Description： 故障代码库删除
     *   多条件删除
     * @return
     */
    @DeleteMapping("deleteFaultCodeBase")
    @ApiOperation(value = "故障代码库删除接口", notes = "故障代码库删除")
    @ApiImplicitParams({
    	  @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	  @ApiImplicitParam(paramType = "query", name = "pmsAmId", value = "资产型号id", dataType = "String",required = true),
    	  @ApiImplicitParam(paramType = "query", name = "pmsAiId", value = "风机部件id", dataType = "String")
    })
    @TraceLog(content = "故障代码库删除", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteFaultCodeBase(FaultCodeBase bean) {
    	try {
    		this.faultCodeBaseService.deleteFaultCodeBase(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"删除成功"));
		} catch (Exception e) {
			log.error("故障代码库删除错误！{}", e.getMessage(), e);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"故障代码库删除错误！"));
    }
    
    /**
     * @Description： 故障代码库(主键)删除
     *   主键删除
     * @return
     */
    @DeleteMapping("deleteFaultCodeBaseById")
    @ApiOperation(value = "故障代码库（主键）删除接口", notes = "故障代码库（主键）删除")
    @ApiImplicitParams({
    	  @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	  @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true)
    })
    @TraceLog(content = "故障代码库（主键）删除", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteFaultCodeBaseById(FaultCodeBase bean) {
    	try {
    		this.faultCodeBaseService.deleteFaultCodeBaseById(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"删除成功"));
		} catch (Exception e) {
			log.error("故障代码库（主键）删除错误！{}", e.getMessage(), e);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"故障代码库（主键）删除错误！"));
    }
    
    /**
     * @Description： 故障代码库(主键)查询
     *   主键查询
     * @return
     */
    @GetMapping("selectFaultCodeBaseById")
    @ApiOperation(value = "故障代码库（主键）查询接口", notes = "故障代码库（主键）查询")
    @ApiImplicitParams({
    	  @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	  @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectFaultCodeBaseById(FaultCodeBase bean) {
    	try {
    		Result<?> result=this.faultCodeBaseService.selectFaultCodeBaseById(bean);
    		return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			log.error("故障代码库（主键）查询错误！{}", e.getMessage(), e);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"故障代码库（主键）查询错误！"));
    }
    
    
    /**
     * @Description： 故障代码库分页查询
     * @author WCH
     * @datetime 2020-5-14 15:55:37
     * @return
     */
    @GetMapping("selectFaultCodeBaseByPage")
    @ApiOperation(value = "故障代码库分页查询接口", notes = "故障代码库分页查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "model", value = "风电机组型号", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "protocolNo", value = "协议号", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "iecPath", value = "iec路径", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "faultCode", value = "故障编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "faultName", value = "故障名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "engName", value = "英文名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "componentName", value = "部件名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "pmsAmId", value = "资产型号ID", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "pmsAiId", value = "风机部件ID", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "status", value = "状态（0待审核 1审核通过）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "是否启用（0-启用 1-禁用）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "sTime", value = "开始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
        @ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
        @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
	})
    public ResponseEntity<Result<?>> selectFaultCodeBaseByPage(Page<FaultCodeBase> page, FaultCodeBaseVO bean) {
    	try {
    		Result<?> result=this.faultCodeBaseService.selectFaultCodeBaseByPage(page,bean);
    		return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			log.error("故障代码库分页查询错误！{}", e.getMessage(), e);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"故障代码库分页查询错误！"));
    }
    
}
