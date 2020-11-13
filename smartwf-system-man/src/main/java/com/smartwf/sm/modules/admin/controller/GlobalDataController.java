package com.smartwf.sm.modules.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.utils.MathUtils;
import com.smartwf.common.utils.Wso2ClientUtils;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.config.util.HS256Utils;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.LoginRecord;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.service.GlobalDataService;
import com.smartwf.sm.modules.admin.service.LoginRecordService;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import cn.hutool.core.convert.Convert;
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
    private LoginRecordService loginRecordService;
	
	@Autowired
    private Wso2Config wso2Config;
	
	@Autowired
    private TenantService tenantService;
	
	 
   
    /**
     * @Description 根据用户等级，返回租户列表
     * @param MgrType {2平台管理员 1管理员 0普通}
     * @return
     */
    @GetMapping("tenantByMgrType")
    @ApiOperation(value = "用户等级查询租户列表接口", notes = "用户等级查询租户列表信息")
    public ResponseEntity<Result<?>> tenantByMgrType() {
        try {
           //获取用户信息
           User user=UserThreadLocal.getUser();
           //返回租户列表
           Result<?> result=this.globalDataService.selectTenantByPage(user);
	       return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("根据用户等级，返回租户列表错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("根据用户等级，返回租户列表错误！"));
    }
    
    
    /**
     * @Description 通过租户查询   - 区公司列表
     * @return
     */
    @GetMapping("distCompanyAll")
    @ApiOperation(value = "租户查询区公司接口", notes = "租户查询区公司信息")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> distCompanyAll(Integer tenantId) {
        try {
           Result<?> result= this.globalDataService.distCompanyAll(tenantId);
           return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("租户查询区公司错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("租户查询区公司错误！"));
    }
    
    
    /**
     * @Description 通过租户查询   - 用户所属风场
     * @return
     */
    @GetMapping("windFarmByTenantId")
    @ApiOperation(value = "租户查询风场接口", notes = "1.租户ID返回租户下所有风场  2.租户用户参数，返回用户所属风场")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
	    @ApiImplicitParam(paramType = "query", name = "userId", value = "用户（主键）", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> windFarmByTenantId(UserOrganization bean) {
        try {
           Result<?> result= this.globalDataService.windFarmByTenantId(bean);
           return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("获取区公司数据错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("获取区公司数据错误！"));
    }
    
    
    /**
     * @Description 通过租户/区公司查询  - 风场集合
     * @return
     */
    @GetMapping("windFarmByDistCompanyId")
    @ApiOperation(value = "租户/区公司查询风场接口", notes = "租户/区公司查询风场信息")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
	    @ApiImplicitParam(paramType = "query", name = "orgId", value = "区公司（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> windFarmByDistCompanyId(Integer tenantId,Integer orgType,Integer orgId) {
        try {
           Result<?> result= this.globalDataService.windFarmAll(tenantId,orgId);
      	   return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("租户/区公司查询风场错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("租户/区公司查询风场错误！"));
    }
    
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
    	@ApiImplicitParam(paramType = "query", name = "flushType", value = "刷新类型,可字符串拼接（0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置）7天气预报", dataType = "String", required = true)
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
    public ResponseEntity<Result<?>> oauth2client(HttpServletRequest request, User bean) {
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
    		//添加登录记录
    		addLoginRecord(request, userInfo, Constants.ZERO);
    		//更新替换redis数据
    		this.redisService.set(userInfo.getSessionId(), JSONUtil.toJsonStr(userInfo),wso2Config.tokenRefreshTime);
    		//成功返回
    		return ResponseEntity.ok(Result.data(Wso2ClientUtils.resUserInfo(userInfo)));
        } catch (Exception e) {
            log.error("授权返回用户基础信息异常！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("授权返回用户基础信息异常，请重新登录！"));
    }
    
    
    /**
     * @Description：获取用户基础信息
     * @param sessionId
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
        		//区分子系统和app端过期时间
        		if(StringUtils.isNotBlank(userInfo.getSessionState())) {
        			this.redisService.set(userInfo.getSessionId(), JSONUtil.toJsonStr(userInfo),wso2Config.tokenRefreshTime);
        		}else {
        			this.redisService.set(userInfo.getSessionId(), JSONUtil.toJsonStr(userInfo),Constants.APP_TIMEOUT);
        		}
        		//成功返回
        		return ResponseEntity.ok(Result.data(Constants.EQU_SUCCESS,Wso2ClientUtils.resUserInfo(userInfo)));
        	}
        } catch (Exception e) {
            log.error("获取用户基础信息失败！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( "获取用户基础信息失败！"));
    }
   
    
    /**
     * @Description 统一注销
     * @return
     */
    @GetMapping("logout")
    @ApiOperation(value = "注销接口", notes = "注销接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "sessionIds", value = "批量注销（sessionId逗号拼接）", dataType = "String")
    })
    public ResponseEntity<Result<?>> userLogin(HttpServletRequest request,String sessionIds) {
        try {
        	//批量注销
        	if(StringUtils.isNotBlank(sessionIds)) {
        		String[] keys=sessionIds.split(",");
        		for(String key: keys) {
           			this.redisService.del(key);
           			//添加注销记录
           			String value =redisService.get(key);
                    if(StringUtils.isNotBlank(value)){
               			User user=JSONUtil.toBean(value, User.class);
               			//添加注销记录
               			addLoginRecord(request,user,Constants.ONE);
                    }
        		}
        	}else {
        		//单体注销
                String sessionId=request.getHeader(Constants.SESSION_ID);
                String userStr =redisService.get(sessionId);
                if(StringUtils.isNotBlank(userStr)){
           			User user=JSONUtil.toBean(userStr, User.class);
           			//清空redis
           			this.redisService.del(sessionId);
           			//添加注销记录
           			addLoginRecord(request,user,Constants.ONE);
                }
        	}
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("注销成功！"));
        } catch (Exception e) {
            log.error("注销失败！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("注销失败！"));
    }
    
    /**
     * 说明：添加登录/注销记录
     * @author WCH
     * @param status 0-登录   1-注销
     * 
     * */
    public void addLoginRecord(HttpServletRequest request,User user,int status) {
		try {
			//第一次登录，添加登录记录信息
    		String ip=MathUtils.getIpAddress(request);
    		String loginType=HttpClientUtil.getBrowserInfo(request);
    		String deviceName=HttpClientUtil.getDeviceName(request);
    		LoginRecord lr=new LoginRecord();
    		lr.setIpAddress(ip);
    		lr.setLoginType(loginType);
    		lr.setCreateTime(new Date());
    		lr.setLoginCode(user.getLoginCode());
    		lr.setTenantId(user.getTenantId());
    		lr.setLoginTime(lr.getCreateTime());
    		lr.setStatus(status);
    		lr.setDeviceName(deviceName);
    		this.loginRecordService.addLoginRecord(lr);
		} catch (Exception e) {
			log.error("ERROR：插入登录记录错误！{}-{}",e.getMessage(),e);
		}
    }
    
    /**
     * @Description：获取全部租户和风场
     * @return
     */
    @PostMapping("selectTenantOrWindFarm")
    @ApiOperation(value = "获取全部租户和风场接口", notes = "获取全部租户和风场")
    public ResponseEntity<Result<?>> selectTenantOrWindFarm(HttpServletRequest request) {
        try {
        	//获取头部apitoken
        	String apiToken = request.getHeader(Constants.ACCESS_TOKEN);
        	if(apiToken==null) {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST,"token为空！"));
        	}
        	//验证token
        	if(!HS256Utils.vaildToken(apiToken)) {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST,"token无效！"));
        	};
        	//租户
        	List<Tenant> tenantList= JSONUtil.toList( JSONUtil.parseArray( this.redisService.get("initTenant")), Tenant.class);
        	//组织机构
        	Map<String, Object> map = JSONUtil.parseObj(redisService.get("initOrganization"));
        	Map<String,Object> tenantMap=null;
        	Map<String,Object> orgMap=null;
        	List<Map<String,Object>> windfarmlist=null;
        	List<Map<String,Object>> reslist=new ArrayList<>();
        	for(Tenant t:tenantList) {
        		tenantMap=new HashMap<>();
        		tenantMap.put("tenantId", t.getId());
        		tenantMap.put("tenantDomain", t.getTenantDomain());
        		tenantMap.put("tenantName", t.getTenantName());
        		if(map!=null && map.size()> 0 ) {
    				//组织机构
    				List<OrganizationVO> orglist= JSONUtil.toList( JSONUtil.parseArray( map.get(Convert.toStr(t.getId()))), OrganizationVO.class) ;
    				windfarmlist=new ArrayList<>();
    				//过滤风场
    				for(OrganizationVO orgVo:orglist) {
    					orgMap=new HashMap<>();
    					//风场
    					if(orgVo.getOrgType()==Constants.ONE) {
    						orgMap.put("windFarmId", orgVo.getId());
    						orgMap.put("windFarmName", orgVo.getOrgName());
    						windfarmlist.add(orgMap);
    					}
    				}
    				tenantMap.put("windFarms", windfarmlist);
        		}
        		//租户风场树形结构
        		reslist.add(tenantMap);
        	}
        	//封装根节点返回
        	tenantMap=new HashMap<>();
        	tenantMap.put("tenantStation", reslist);
    		//成功返回
    		return ResponseEntity.ok(Result.data(Constants.EQU_SUCCESS,tenantMap));
        } catch (Exception e) {
            log.error("获取全部租户和风场！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( "获取全部租户和风场！"));
    }
   
    
    /**
     * @Description：知识中心-获取用户风场信息
     * @param sessionId
     * @return
     */
    @GetMapping("selectUserInfoByWindFarm")
    @ApiOperation(value = "用户风场接口", notes = "获取用户风场信息")
    public ResponseEntity<Result<?>> selectUserInfoByWindFarm(UserOrganization bean) {
        try {
        	Result<?> result= this.globalDataService.selectUserInfoByWindFarm(bean);
        	return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("获取用户风场失败！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg( "用户风场失败！"));
    }
    
}
