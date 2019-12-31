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
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.service.DictionaryService;
import com.smartwf.sm.modules.admin.vo.DictionaryVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 数据字典控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("dict")
@Slf4j
@Api(description ="数据字典控制器")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * @Description: 查询数据字典分页
	 * @return
	 */
    @GetMapping("selectDictionaryByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询数据字典")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int",required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "dictCode", value = "字典代码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "dictName", value = "字典名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectDictionaryByPage(Page<Dictionary> page, DictionaryVO bean) {
        try {
            Result<?> result = this.dictionaryService.selectDictionaryByPage(page, bean);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            log.error("分页查询数据字典信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询数据字典信息错误！"));
    }
    
    /**
     * @Description: 主键查询数据字典
     * @return
     */
    @GetMapping("selectDictionaryById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询数据字典")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectDictionaryById(Dictionary bean) {
        try {
            Result<?> result = this.dictionaryService.selectDictionaryById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询数据字典信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询数据字典信息错误！"));
    }
    
    /**
     * @Description: 添加数据字典
     * @return
     */
    @PostMapping("saveDictionary")
    @ApiOperation(value = "添加接口", notes = "添加数据字典接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "uid", value = "上级（主键）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "pid", value = "根节点（主键）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "level", value = "层次级别", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "dictCode", value = "字典编码", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "dictName", value = "字典名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "dictValue", value = "字典值", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
	        @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
		    @ApiImplicitParam(paramType = "query", name = "dictType", value = "字典类型", dataType = "Integer"),
 	        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> saveDictionary(HttpSession session,Dictionary bean) {
        try {
    		this.dictionaryService.saveDictionary(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
        } catch (Exception e) {
            log.error("添加数据字典信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("添加数据字典信息错误！"));
    }
    
    /**
     * @Description： 修改数据字典
     * @return
     */
    @PutMapping("updateDictionary")
    @ApiOperation(value = "修改接口", notes = "修改数据字典资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
		@ApiImplicitParam(paramType = "query", name = "uid", value = "上级（主键）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "pid", value = "根节点（主键）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "level", value = "层次级别", dataType = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "dictCode", value = "字典编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "dictName", value = "字典名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "dictType", value = "字典类型", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改数据字典", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateDictionary(Dictionary bean) {
        try {
            this.dictionaryService.updateDictionary(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("修改数据字典信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("修改数据字典信息错误！"));
    }
    
    /**
     * @Description： 删除数据字典
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteDictionary")
    @ApiOperation(value = "删除接口", notes = "删除数据字典")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除数据字典系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteDictionary(DictionaryVO bean) {
        try {
        	this.dictionaryService.deleteDictionary(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
        } catch (Exception e) {
            log.error("删除数据字典信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除数据字典信息错误！"));
    }


}
