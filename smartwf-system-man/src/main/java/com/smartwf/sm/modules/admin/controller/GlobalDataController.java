package com.smartwf.sm.modules.admin.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.Md5Utils;
import com.smartwf.common.utils.Wso2ClientUtils;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.service.GlobalDataService;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 全局数据控制器
 * @author WCH
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
	
	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
    private Wso2Config wso2Config;
	
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
    public ResponseEntity<Result<?>> postAll(Post bean) {
        try {
        	 Result<?> result= this.globalDataService.postAll(bean);
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
    public ResponseEntity<Result<?>> roleAll(Role bean) {
        try {
        	 Result<?> result= this.globalDataService.roleAll(bean);
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
    	@ApiImplicitParam(paramType = "query", name = "flushType", value = "刷新类型,可字符串拼接（0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置）", dataType = "String", required = true)
    })
    public ResponseEntity<Result<?>> flushCache(GlobalData bean) {
        try {
        	//用户基础信息
        	this.globalDataService.flushCache(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("成功！"));
        } catch (Exception e) {
            log.error("刷新缓存数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("刷新缓存异常！"));
    }
    

    /**
     * @Description：授权登录，获取用户基础信息
     * @param bean
     * @return
     */
    @GetMapping("oauth2client")
    @ApiOperation(value = "授权登录", notes = "授权登录，获取用户基础信息")
    public ResponseEntity<Result<?>> oauth2client(User bean) {
        try {
        	//1 通过参数验证是否redis是否存在
        	User user=UserThreadLocal.getUser();
    		//3根据accessToken查询用户ID
    		String str=Wso2ClientUtils.reqWso2UserInfo(wso2Config, user);
    		if(StringUtils.isBlank(str)) {
    			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权参数异常，accessToken查询用户信息失败！"));
    		}
    		Map<String,Object> resmap=JSONUtil.parseObj(str);
			//打印返回结果
			for(Entry<String, Object> m:resmap.entrySet()) {
        		log.info(m.getKey()+"    "+m.getValue());
        	}
			//4验证是否成功
			if(!resmap.containsKey(Constants.USERID)) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权参数异常，WSO2 user_id为空！"));
			}
			user.setUserCode(String.valueOf(resmap.get("user_id")));
			//5通过user_id查询用户基础信息	
    		User userInfo=this.userInfoService.selectUserInfoByUserCode(user);
    		if(null==userInfo) {
    			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权参数异常，通过wso2 user_id查询用户信息异常！"));
    		}
    		this.redisService.set(userInfo.getSeesionId(), JSONUtil.toJsonStr(userInfo));
    		//成功返回
    		return ResponseEntity.ok(Result.data(userInfo));
        } catch (Exception e) {
            log.error("授权返回用户基础信息异常！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权返回用户基础信息异常，请重新登录！"));
    }
    
    
    /**
     * @Description：获取用户基础信息
     * @param smartwfToken
     * @return
     */
    @GetMapping("selectUserInfoByToken")
    @ApiOperation(value = "获取用户基础信息接口", notes = "获取用户基础信息")
    public ResponseEntity<Result<?>> selectUserInfoByToken(HttpServletRequest request) {
        try {
        	//1 通过参数验证是否redis是否存在
        	String userStr =redisService.get(request.getHeader(Constants.SESSION_ID));
        	if(StringUtils.isNoneBlank(userStr)) {
        		//2 转换成对象
        		User user=JSONUtil.toBean(userStr, User.class);
        		//3根据accessToken查询用户ID
        		String str=Wso2ClientUtils.reqWso2UserInfo(wso2Config, user);
        		if(StringUtils.isBlank(str)) {
        			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权参数异常，accessToken查询用户信息失败！"));
        		}
        		Map<String,Object> resmap=JSONUtil.parseObj(str);
    			//4验证是否成功
    			if(!resmap.containsKey(Constants.USERID)) {
    				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权参数异常，WSO2 user_id为空！"));
    			}
    			user.setUserCode(String.valueOf(resmap.get("user_id")));
				//5通过user_id查询用户基础信息	
        		User userInfo=this.userInfoService.selectUserInfoByUserCode(user);
        		if(null==userInfo) {
        			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权参数异常，user_id查询用户信息异常！"));
        		}
        		this.redisService.set(userInfo.getSeesionId(), JSONUtil.toJsonStr(userInfo));
        		//成功返回
        		return ResponseEntity.ok(Result.data(Constants.EQU_SUCCESS,userInfo));
        	}
        } catch (Exception e) {
            log.error("获取用户基础信息失败！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( "获取用户基础信息失败！"));
    }
   
}
