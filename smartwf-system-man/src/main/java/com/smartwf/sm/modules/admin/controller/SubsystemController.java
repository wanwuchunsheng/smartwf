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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.service.SubsystemService;
import com.smartwf.sm.modules.admin.vo.ResourceVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 子系统控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("subsystem")
@Slf4j
@Api(description ="子系统管理控制器")
public class SubsystemController {
	
	@Autowired
	private SubsystemService subsystemService;
	
	
	/**
	 * @Description: 查询子系统分页
	 * @return
	 */
    @GetMapping("selectSubsystemByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询子系统")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "resCode", value = "子系统编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "resName", value = "子系统名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectSubsystemByPage(Page<Resource> page, ResourceVO bean) {
        try {
            Result<?> result = this.subsystemService.selectSubsystemByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询子系统信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询子系统信息错误！"));
    }
    
    
    /**
     * @Description: 主键查询子系统
     * @return
     */
    @GetMapping("selectSubsystemById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询子系统")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectSubsystemById(Resource bean) {
        try {
            Result<?> result = this.subsystemService.selectSubsystemById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询子系统信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加子系统
     * @return
     */
    @PostMapping("saveSubsystem")
    @ApiOperation(value = "添加接口", notes = "添加子系统接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "resCode", value = "子系统编码", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "resName", value = "子系统名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "resHref", value = "资源地址", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "routeHref", value = "路由地址", dataType = "String", required = true),
	        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-默认启用 1-禁用）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveSubsystem(HttpSession session,Resource bean) {
        try {
    		this.subsystemService.saveSubsystem(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加子系统信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加子系统信息错误！"));
    }
    
    /**
     * @Description： 修改子系统
     * @return
     */
    @PutMapping("updateSubsystem")
    @ApiOperation(value = "修改接口", notes = "修改子系统资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "resCode", value = "子系统编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "resName", value = "子系统名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "resHref", value = "资源地址", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "routeHref", value = "路由地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-默认启用 1-禁用）", dataType = "int"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改子系统", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateSubsystem(Resource bean) {
        try {
            this.subsystemService.updateSubsystem(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改子系统信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改子系统信息错误！"));
    }
    
    /**
     * @Description： 删除子系统
     * @param id 单个删除
     * @return
     */
    @DeleteMapping("deleteSubsystem")
    @ApiOperation(value = "删除接口", notes = "删除子系统")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除子系统系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteSubsystem(ResourceVO bean) {
        try {
        	this.subsystemService.deleteSubsystem(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除子系统信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除子系统信息错误！"));
    }


}
