package com.smartwf.hm.modules.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.handler.UserProfile;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

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
    private AlarmInboxService alarmInboxService;

    /**
     * @Description：认证登录
     * @param code,session_state和state
     * @return
     */
    @GetMapping("oauth2client")
    @ApiOperation(value = "授权登录", notes = "授权登录，获取用户基础信息")
    public ResponseEntity<Result<?>> oauth2client(HttpServletRequest request, User bean) {
        try {
        	User user=UserProfile.getUser(request);
    		//成功返回
    		return ResponseEntity.ok(Result.data(user));
        } catch (Exception e) {
            log.error("登录异常！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("登录异常，请重新登录！"));
    }
    
    /**
   	 *  故障、缺陷{转工单}
   	 *      生产中心状态修改
   	 *      1）修改工单状态
   	 *      2）记录表插入修改记录
   	 * @author WCH
   	 * @param bean
   	 * @return
   	 */
    @PostMapping("updateAlarmInByParam")
    @ApiOperation(value = "转工单状态修改接口", notes = "转工单状态修改")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "faultId", value = "故障主键", dataType = "String", required = true),
   	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
   	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "状态(0未处理  2处理中 3已处理 4已关闭 )", dataType = "int", required = true),
   	    @ApiImplicitParam(paramType = "query", name = "orderNumber", value = "工单号", dataType = "String", required = true),
   	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> updateAlarmInByParam(FaultInformationVO bean) {
        try {
           Result<?> result= this.alarmInboxService.updateAlarmInByParam(bean);
       	   return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("转工单状态修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("转工单状态修改错误！"));
    }
    
      
   
}
