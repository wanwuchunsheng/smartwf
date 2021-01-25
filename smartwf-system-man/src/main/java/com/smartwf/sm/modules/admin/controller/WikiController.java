package com.smartwf.sm.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.service.WikiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 知识中心控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("wiki")
@Api(description ="知识中心控制器")
@Slf4j
public class WikiController {
	
	@Autowired
	private WikiService wikiService;
	
	/**
     * @Description：知识中心-用户ID返回名称
     * @param tenantId
     * @param userId
     * @return
     */
    @GetMapping("selectUserInfoByIds")
    @ApiOperation(value = "用户ID查询名称", notes = "根据用户id返回用户名称（支持批量查询）")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID(逗号拼接)", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "orgId", value = "组织机构ID(逗号拼接)", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> selectUserInfoByIds(String tenantId, String userId,String orgId) {
        try {
        	Result<?> result= this.wikiService.selectUserInfoByIds(tenantId, userId,orgId);
        	return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("用户ID查询名称失败！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR, "用户ID查询名称失败！"));
    }
    
    /**
     * @Description：知识中心-组织ID查询组织下的所有用户（
     *    不包括当前组织人员）
     * @param tenantId
     * @param orgId
     * @return
     */
    @GetMapping("selectUserOrganizationByOrgId")
    @ApiOperation(value = "组织人员查询接口", notes = "组织ID查询组织下的所有用户（支持批量查询）")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "orgId", value = "组织机构ID(逗号拼接)", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> selectUserOrganizationByOrgId(String tenantId, String orgId) {
        try {
        	Result<?> result= this.wikiService.selectUserOrganizationByOrgId(tenantId, orgId);
        	return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("用户ID查询名称失败！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR, "用户ID查询名称失败！"));
    }
    
    /**
     * @Description：知识中心-查询所有用户
     * @param tenantId
     * @param orgId
     * @return
     */
    @GetMapping("selectUserInfoByAll")
    @ApiOperation(value = "查询租户下所有用户接口", notes = "查询租户下所有用户")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectUserInfoByAll(Integer tenantId) {
        try {
        	Result<?> result= this.wikiService.selectUserInfoByAll(tenantId);
        	return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("用户ID查询名称失败！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR, "查询失败！"));
    }

}
