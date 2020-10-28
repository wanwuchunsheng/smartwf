package com.smartwf.sm.modules.sysconfig.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.MaintNotification;
import com.smartwf.sm.modules.sysconfig.service.MaintNotifiService;
import com.smartwf.sm.modules.sysconfig.vo.MaintNotifiVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 系统维护控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("maint")
@Slf4j
@Api(description ="系统维护控制器")
public class MaintNotifiController {
	
	@Autowired
	private MaintNotifiService maintNotifiService;
	
	
	
	/**
     * @Description: 添加系统维护 - 修订
     * @param bean
     * @return
     */
    @PostMapping("saveMaintNotifi")
    @ApiOperation(value = "（修订）添加接口", notes = "（修订）添加系统维护接口")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "serviceAddress", value = "服务地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "status", value = "状态 0-启用维护状态  1-关闭维护状态 ", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "msg", value = "通知内容", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "添加系统维护", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveMaintNotifi(HttpServletRequest request, MaintNotification bean) {
    	try {
            //保存本地数据
        	Result<?> result= this.maintNotifiService.saveMaintNotifi(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
	
	
	/**
	 * @Description: 查询系统维护分页
	 * @param bean
	 * @return
	 */
    @GetMapping("selectMaintNotifiByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询系统维护信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "serivceAddress", value = "服务地址", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态 0-启用维护状态  1-关闭维护状态 ", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "msg", value = "通知内容", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectMaintNotifiByPage(Page<MaintNotification> page, MaintNotifiVO bean) {
        try {
            Result<?> result = this.maintNotifiService.selectMaintNotifiByPage(page, bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询系统维护错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询系统维护错误！"));
    }
    
    /**
     * @Description: 主键查询系统维护
     * @param bean
     * @return
     */
    @GetMapping("selectMaintNotifiById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询系统维护")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectMaintNotifiById(MaintNotification bean) {
        try {
            Result<?> result = this.maintNotifiService.selectMaintNotifiById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询系统维护错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询系统维护错误！"));
    }
    
    /**
     * @Description: 添加系统维护
     * @param bean
     * @return
     
    @PostMapping("saveMaintNotifi")
    @ApiOperation(value = "添加接口", notes = "添加系统维护接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "serviceAddress", value = "服务地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "status", value = "状态 0-启用维护状态  1-关闭维护状态 ", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "msg", value = "通知内容", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "添加系统维护", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveMaintNotifi(HttpServletRequest request, MaintNotification bean) {
    	try {
            //保存本地数据
        	Result<?> result= this.maintNotifiService.saveMaintNotifi(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    */
    
    /**
     * @Description： 修改系统维护
     * @param bean
     * @return
     */
    @PutMapping("updateMaintNotifi")
    @ApiOperation(value = "修改接口", notes = "修改系统维护资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "Integer"),
    	@ApiImplicitParam(paramType = "query", name = "serviceAddress", value = "服务地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "status", value = "状态0-启用维护状态  1-关闭维护状态 ", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "msg", value = "通知内容", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改系统维护", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateMaintNotifi(HttpServletRequest request, MaintNotification bean) {
    	try {
            //保存本地数据
    		Result<?> result=this.maintNotifiService.updateMaintNotifi(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 删除系统维护
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteMaintNotifi")
    @ApiOperation(value = "删除接口", notes = "删除系统维护")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除系统维护", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteMaintNotifi(MaintNotifiVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
        this.maintNotifiService.deleteMaintNotifi(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }

}
