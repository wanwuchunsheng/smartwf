package com.smartwf.sm.modules.admin.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.smartwf.common.pojo.Oauth2Client;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.common.utils.MD5Utils;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.service.GlobalDataService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 全局数据控制器
 */
@RestController
@RequestMapping("globaldata")
@Slf4j
@Api(description = "全局数据控制器")
public class GlobalDataController {
	
	@Autowired
    private GlobalDataService globalDataService;
	
	@Autowired
    private RedisService redisService;
	
	 /**
     * @Description 租户列表
     * @return
     */
    @GetMapping("tenantAll")
    @ApiOperation(value = "租户接口", notes = "获取租户列表信息")
    public ResponseEntity<Result<?>> tenantAll() {
        try {
           Result<?> result= this.globalDataService.tenantAll();
           if(result!=null) {
      		 return ResponseEntity.ok(result);
	       }
	       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.msg("数据为空！"));
        } catch (Exception e) {
            log.error("获取租户基础数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("获取租户基础数据错误！"));
    }
    
    /**
     * @Description 组织架构列表
     * @param tenantId
     * @return
     */
    @GetMapping("organizationAll")
    @ApiOperation(value = "组织架构接口", notes = "获取组织架构列表信息")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
	    @ApiImplicitParam(paramType = "query", name = "orgType", value = "返回数据的类型：1树形结构 2列表结构", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> organizationAll(OrganizationVO bean) {
        try {
        	 Result<?> result= this.globalDataService.organizationAll(bean);
        	 if(result!=null) {
        		 return ResponseEntity.ok(result);
        	 }
        	 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.msg("数据为空！"));
        } catch (Exception e) {
            log.error("获取组织架构基础数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("获取组织架构基础数据错误！"));
    }
    
    /**
     * @Description 职务列表
     * @param tenantId
     * @param organizationId
     * @return
     */
    @GetMapping("postAll")
    @ApiOperation(value = "职务接口", notes = "获取职务列表信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "organizationId", value = "组织架构（主键）", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> PostAll(Post bean) {
        try {
        	 Result<?> result= this.globalDataService.PostAll(bean);
        	 if(result!=null) {
        		 return ResponseEntity.ok(result);
        	 }
        	 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.msg("数据为空！"));
        } catch (Exception e) {
            log.error("获取职务基础数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("获取职务基础数据错误！"));
    }
    
    
    /**
     * @Description 角色列表
     * @param 
     * @return
     */
    @GetMapping("roleAll")
    @ApiOperation(value = "角色接口", notes = "获取角色列表信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> RoleAll(Role bean) {
        try {
        	 Result<?> result= this.globalDataService.RoleAll(bean);
        	 if(result!=null) {
        		 return ResponseEntity.ok(result);
        	 }
        	 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.msg("数据为空！"));
        } catch (Exception e) {
            log.error("获取角色基础数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("获取角色基础数据错误！"));
    }
    
    /**
     * @Description 获取数据字典基础数据列表
     * @param 
     * @return
     */
    @GetMapping("dictAll")
    @ApiOperation(value = "数据字典接口", notes = "获取数据字典列表信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "dictCode", value = "数据字典编码", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> dictAll(Dictionary bean) {
        try {
        	 Result<?> result= this.globalDataService.dictAll(bean);
        	 if(result!=null) {
        		 return ResponseEntity.ok(result);
        	 }
        	 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.msg("数据为空！"));
        } catch (Exception e) {
            log.error("获取数据字典基础数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("获取数据字典基础数据错误！"));
    }
    
    /**
     * @Description 刷新缓存
     * @param flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色
     * @return
     */
    @GetMapping("flushCacheAll")
    @ApiOperation(value = "刷新缓存接口", notes = "刷新缓存信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "flushType", value = "刷新类型,可字符串拼接（0全部 1租户 2组织机构 3职务  4数据字典 5角色）", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> flushCache(GlobalData bean) {
        try {
        	this.globalDataService.flushCache(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("成功！"));
        } catch (Exception e) {
            log.error("刷新缓存数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("刷新缓存异常！"));
    }
    

    /**
     * @Description：授权登录，接收参数
     * @param code,session_state和state
     * @return
     */
    @GetMapping("oauth2client")
    @ApiOperation(value = "授权登录", notes = "授权登录，接收参数")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "smartwfCode", value = "授权编码", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "smartwfType", value = "0:默认smartwToken  1:accessToken ", dataType = "String")
    })
    public ResponseEntity<Result<?>> oauth2client(Oauth2Client bean) {
        try {
        	String token=MD5Utils.md5(bean.getCode());
        	String mapstr=redisService.get(token);
        	if(!mapstr.equals("")) {
        		bean=new Oauth2Client();
        		bean.setSmartwfToken(token);
        		if("1".equals(bean.getSmartwfType())) { //1：返回access_token
        			Map<String,Object> map=JsonUtil.jsonToMap(mapstr);
            		bean.setAccessToken(String.valueOf(map.get("access_token")));
        		}
        		return ResponseEntity.ok(Result.data(bean));
        	}
        } catch (Exception e) {
            log.error("授权令牌错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权令牌错误！"));
    }
    
    /**
     * @Description：模拟wso2用户创建
     * @param code,session_state和state
     * @return
     */
    @GetMapping("addWSO2User")
    @ApiOperation(value = "wso2用户添加接口", notes = "wso2用户添加")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "password", value = "密码", dataType = "String")
    })
    public ResponseEntity<Result<?>> addWSO2User() {
        try {
        	Map<String,String> headers=new HashMap<String,String>();
            headers.put("accept", "application/scim+json");
            headers.put("Content-Type", "application/scim+json");
            headers.put("userName", "admin");
            headers.put("password", "admin");
        	String str= HttpClientUtil.doPost("https://identity.windmagics.com/scim2/Users", headers,"utf-8");
        } catch (Exception e) {
            log.error("wso2用户添加错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("wso2用户添加错误！"));
    }
}
