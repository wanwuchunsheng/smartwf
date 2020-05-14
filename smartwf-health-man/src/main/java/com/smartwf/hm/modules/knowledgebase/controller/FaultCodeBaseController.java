package com.smartwf.hm.modules.knowledgebase.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.service.FaultCodeBaseService;

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
@RequestMapping("smartwf_health/faultcode")
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
   

}





