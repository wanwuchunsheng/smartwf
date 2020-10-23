package com.smartwf.sm.modules.sysconfig.controller;

import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.config.ftp.SFtpConfig;
import com.smartwf.sm.config.ftp.SFtpUtil;
import com.smartwf.sm.modules.sysconfig.pojo.AssetClassification;
import com.smartwf.sm.modules.sysconfig.service.AssetClassifService;
import com.smartwf.sm.modules.sysconfig.vo.AssetClassifVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 资产分类控制层
 * @author WCH
 * @Date: 
 */
@RestController
@RequestMapping("asset")
@Slf4j
@Api(description ="资产分类控制器")
public class AssetClassifController {
	@Autowired
	private AssetClassifService assetClassifService;
	/**
	 * 获取上传地址
	 * 
	 * */
	@Autowired
    private SFtpConfig config;
	
	/**
	 * @Description: 查询资产分类分页
	 * @param bean
	 * @return
	 */
    @GetMapping("selectAssetClassifByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询资产分类信息")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "classifyName", value = "资产分类名", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "svg", value = "svg图片路径", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectAssetClassifByPage(Page<AssetClassification> page, AssetClassifVO bean) {
        try {
            Result<?> result = this.assetClassifService.selectAssetClassifByPage(page, bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询资产分类错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询资产分类错误！"));
    }
    
    /**
     * @Description: 主键查询资产分类
     * @param bean
     * @return
     */
    @GetMapping("selectAssetClassifById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询资产分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectAssetClassifById(AssetClassification bean) {
        try {
            Result<?> result = this.assetClassifService.selectAssetClassifById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询资产分类错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询资产分类错误！"));
    }
    
    /**
     * @Description: 添加资产分类
     * @param bean
     * @return
     */
    @PostMapping(value="saveAssetClassif", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "添加接口", notes = "添加资产分类接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户主键ID", dataType = "int",required = true),
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "int"),
	    @ApiImplicitParam(paramType = "query", name = "classifyName", value = "资产分类名", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "添加资产分类", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveAssetClassif(@ApiParam(value = "svg图片") MultipartFile file, AssetClassification bean) {
    	try {
    		if(null !=file) {
            	//保存图片
	        	String temp = "document/";
                // 获取图片的文件名
                String fileName = file.getOriginalFilename();
                // 获取图片的扩展名
                String extensionName = fileName.substring(fileName.indexOf("."));
                // 新的图片文件名 = 获取时间戳+"."图片扩展名
                String newFileName = UUID.randomUUID().toString().replaceAll("-", "")  + extensionName;
                // 数据库保存的目录
                String datdDirectory = temp.concat(newFileName);
                //上传文件到sftp
                boolean flag = SFtpUtil.uploadFile( config,datdDirectory, file.getInputStream());
	        	if(flag) {
	        		//上传成功
	        		bean.setSvg(datdDirectory);
	        	}
    		}
            //保存本地数据
        	this.assetClassifService.saveAssetClassif(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 修改资产分类
     * @param bean
     * @return
     */
    @PutMapping(value ="updateAssetClassif", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "修改接口", notes = "修改资产分类资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "classifyName", value = "资产分类名", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改资产分类", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateAssetClassif(@ApiParam(value = "svg图片") MultipartFile file, AssetClassification bean) {
    	try {
    		if(null !=file) {
            	//保存图片
	        	String temp = "document/";
                // 获取图片的文件名
                String fileName = file.getOriginalFilename();
                // 获取图片的扩展名
                String extensionName = fileName.substring(fileName.indexOf("."));
                // 新的图片文件名 = 获取时间戳+"."图片扩展名
                String newFileName = UUID.randomUUID().toString().replaceAll("-", "")  + extensionName;
                // 数据库保存的目录
                String datdDirectory = temp.concat(newFileName);
                //上传文件到sftp
                boolean flag = SFtpUtil.uploadFile( config,datdDirectory, file.getInputStream());
	        	if(flag) {
	        		//上传成功
	        		bean.setSvg(datdDirectory);
	        	}
            }
            //保存本地数据
        	this.assetClassifService.updateAssetClassif(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"成功"));
    	}catch (Exception e) {
			log.error("ERROR:保存失败！{}-{}",e,e.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！"));
    }
    
    /**
     * @Description： 删除资产分类
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteAssetClassif")
    @ApiOperation(value = "删除接口", notes = "删除资产分类")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "ids", value = "主键批量删除（逗号拼接）", dataType = "String")
    })
    @TraceLog(content = "删除资产分类", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteAssetClassif(AssetClassifVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
        this.assetClassifService.deleteAssetClassif(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }
}
