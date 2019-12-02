package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

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

import com.github.pagehelper.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.service.PostService;
import com.smartwf.sm.modules.admin.vo.PostVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 职务控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("post")
@Slf4j
@Api(description ="职务控制器")
public class PostController {
	
	@Autowired
	private PostService PostService;
	
	/**
	 * @Description: 查询职务分页
	 * @return
	 */
    @GetMapping("selectPostByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询职务")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "mgrType", value = "管理员类型（0-普通 1管理员  2超级管理员）", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "organizationId", value = "组织架构（主键）", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "postCode", value = "职务代码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "postName", value = "职务名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "postType", value = "职务类型（0高管 1中层 2基层）", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectPostByPage(Page<Object> page, PostVO bean) {
        try {
            Result<?> result = this.PostService.selectPostByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询职务信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询职务信息错误！"));
    }
    
    /**
     * @Description: 主键查询职务
     * @return
     */
    @GetMapping("selectPostById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询职务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectPostById(Post bean) {
        try {
            Result<?> result = this.PostService.selectPostById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询职务信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加职务
     * @return
     */
    @PostMapping("savePost")
    @ApiOperation(value = "添加接口", notes = "添加职务接口")
    @ApiImplicitParams({
	    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "organizationId", value = "组织架构（主键）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "postCode", value = "职务代码", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "postName", value = "职务名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "postType", value = "职务类型（0高管 1中层 2基层）", dataType = "Integer"),
		    @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> savePost(HttpSession session,Post bean) {
        try {
    		this.PostService.savePost(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加职务信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加职务信息错误！"));
    }
    
    /**
     * @Description： 修改职务
     * @return
     */
    @PutMapping("updatePost")
    @ApiOperation(value = "修改接口", notes = "修改职务资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "职务（主键）", dataType = "int",required = true),
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int"),
        @ApiImplicitParam(paramType = "query", name = "organizationId", value = "组织架构（主键）", dataType = "int"),
	    @ApiImplicitParam(paramType = "query", name = "postCode", value = "职务代码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "postName", value = "职务名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "postType", value = "职务类型（0高管 1中层 2基层）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改职务", paramIndexs = {0})
    public ResponseEntity<Result<?>> updatePost(Post bean) {
        try {
            this.PostService.updatePost(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改职务信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改职务信息错误！"));
    }
    
    /**
     * @Description： 删除职务
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deletePost")
    @ApiOperation(value = "删除接口", notes = "删除职务")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "int"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除职务系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deletePost(PostVO bean) {
        try {
        	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除数据为空！"));
        	}
        	this.PostService.deletePost(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除职务信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除职务信息错误！"));
    }


}
