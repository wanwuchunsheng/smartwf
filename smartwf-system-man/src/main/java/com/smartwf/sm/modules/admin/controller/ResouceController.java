package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Resouce;
import com.smartwf.sm.modules.admin.service.ResouceService;
import com.smartwf.sm.modules.admin.vo.ResouceVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 资源控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("resouce")
@Slf4j
@Api(description ="资源控制器")
public class ResouceController {
	
	@Autowired
	private ResouceService resouceService;
	
	/**
	 * @Description: 查询资源子系统
	 * @return
	 */
    @GetMapping("selectResouceByPid")
    @ApiOperation(value = "查询子系统接口", notes = "查询资源子系统")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectResouceByPid( ResouceVO bean ) {
        try {
            Result<?> result = this.resouceService.selectResouceByPid(bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询组织架构信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询组织架构信息错误！"));
    }
	
	/**
	 * @Description: 查询资源分页
	 * @return
	 
    @GetMapping("selectResouceByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询资源")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "子系统（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "resCode", value = "资源编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "resName", value = "资源名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectResouceByPage(Page<Resouce> page, ResouceVO bean) {
        try {
            Result<?> result = this.resouceService.selectResouceByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询资源信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询资源信息错误！"));
    }
    */
    
    /**
	 * @Description: 查询所有资源，树形结构
	 * @return
	 */
    @GetMapping("selectResouceByAll")
    @ApiOperation(value = "树形查询所有数据接口", notes = "查询资源所有数据，树形结构")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "子系统（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "resCode", value = "资源编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "resName", value = "资源名称", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectResouceByAll( ResouceVO bean) {
        try {
            Result<?> result = this.resouceService.selectResouceByAll( bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询资源信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询资源信息错误！"));
    }
    
    /**
     * @Description: 主键查询资源
     * @return
     */
    @GetMapping("selectResouceById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询资源")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectResouceById(Resouce bean) {
        try {
            Result<?> result = this.resouceService.selectResouceById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询资源信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加资源
     *      1子系统添加
     *      2模块，资源添加
     * @return
     */
    @PostMapping("saveResouce")
    @ApiOperation(value = "添加接口", notes = "添加资源接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户id", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "uid", value = "上级id", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "pid", value = "父级id", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "resCode", value = "资源编码", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "resName", value = "资源名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "resType", value = "资源类型（1系统 2模块 3资源）", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "level", value = "层次级别", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-默认启用 1-禁用）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveResouce(HttpSession session,Resouce bean) {
        try {
    		this.resouceService.saveResouce(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加资源信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加资源信息错误！"));
    }
    
    /**
     * @Description： 修改资源
     * @return
     */
    @PutMapping("updateResouce")
    @ApiOperation(value = "修改接口", notes = "修改资源资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "资源（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "resCode", value = "操作编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "resName", value = "操作名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改资源", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateResouce(Resouce bean) {
        try {
            this.resouceService.updateResouce(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改资源信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改资源信息错误！"));
    }
    
    /**
     * @Description： 删除资源
     * @param id 单个删除
     * @return
     */
    @DeleteMapping("deleteResouce")
    @ApiOperation(value = "删除接口", notes = "删除资源")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单删除", dataType = "int",required = true)
    })
    @TraceLog(content = "删除资源系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteResouce(ResouceVO bean) {
        try {
        	this.resouceService.deleteResouce(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除资源信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除资源信息错误！"));
    }


}
