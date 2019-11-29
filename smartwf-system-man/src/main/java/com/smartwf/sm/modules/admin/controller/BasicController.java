package com.smartwf.sm.modules.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.sm.modules.admin.pojo.Tenant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 基础数据控制器
 */
@RestController
@RequestMapping("base")
@Slf4j
@Api(description = "基础数据控制器")
public class BasicController {
	
	@Autowired
    private RedisService redisService;

	 /**
     * @Description 租户列表
     * @return
     */
    @GetMapping("tenantAll")
    @ApiOperation(value = "查询租户列表接口", notes = "查询租户列表信息")
    public ResponseEntity<Result<?>> tenantAll() {
        try {
        	List<Tenant> list=JsonUtil.jsonToList(redisService.get("TenantAll"), Tenant.class);
           return ResponseEntity.ok(Result.data(list));
        } catch (Exception e) {
            log.error("查询租户列表信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询租户列表信息错误！"));
    }

}
