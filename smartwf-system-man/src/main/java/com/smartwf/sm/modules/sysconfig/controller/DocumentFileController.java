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
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.DocumentType;
import com.smartwf.sm.modules.sysconfig.service.DocumentFileService;
import com.smartwf.sm.modules.sysconfig.vo.DocumentTypeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 文档文件配置控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("document")
@Slf4j
@Api(description ="文档文件配置控制器")
public class DocumentFileController {
	
	@Autowired
	private DocumentFileService documentTypeService;
	
	/**
	 * @Description: 查询文档文件配置分页
	 * @param bean
	 * @return
	 */
    @GetMapping("selectDocumentTypeByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询文档文件配置信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "styleName", value = "样式名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型 0-可编辑 1不可编辑 2图片", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "uploadSize", value = "上传最大值/M", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "unit", value = "单位", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectDocumentTypeByPage(Page<DocumentType> page, DocumentTypeVO bean) {
        try {
            Result<?> result = this.documentTypeService.selectDocumentTypeByPage(page, bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询文档文件配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询文档文件配置错误！"));
    }
    
    /**
     * @Description: 主键查询文档文件配置
     * @param bean
     * @return
     */
    @GetMapping("selectDocumentTypeById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询文档文件配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectDocumentTypeById(DocumentType bean) {
        try {
            Result<?> result = this.documentTypeService.selectDocumentTypeById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询文档文件配置错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询文档文件配置错误！"));
    }
    
    /**
     * @Description: 添加文档文件配置
     * @param bean
     * @return
     */
    @PostMapping("saveDocumentType")
    @ApiOperation(value = "添加接口", notes = "添加文档文件配置接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "styleName", value = "样式名称", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "type", value = "类型 0-可编辑 1不可编辑 2图片", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "uploadSize", value = "上传最大值/M", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "unit", value = "单位", dataType = "Integer")
    })
    @TraceLog(content = "添加文档文件配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveDocumentType(HttpServletRequest request, DocumentType bean) {
    	try {
            //保存本地数据
        	this.documentTypeService.saveDocumentType(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 修改文档文件配置
     * @param bean
     * @return
     */
    @PutMapping("updateDocumentType")
    @ApiOperation(value = "修改接口", notes = "修改文档文件配置资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "styleName", value = "样式名称", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "type", value = "类型 0-可编辑 1不可编辑 2图片", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "uploadSize", value = "上传最大值/M", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "unit", value = "单位", dataType = "Integer")
    })
    @TraceLog(content = "修改文档文件配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateDocumentType(HttpServletRequest request, DocumentType bean) {
    	try {
            //保存本地数据
        	this.documentTypeService.updateDocumentType(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 删除文档文件配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteDocumentType")
    @ApiOperation(value = "删除接口", notes = "删除文档文件配置")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除文档文件配置", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteDocumentType(DocumentTypeVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
        this.documentTypeService.deleteDocumentType(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }

}
