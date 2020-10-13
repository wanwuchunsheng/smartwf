package com.smartwf.sm.modules.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.handler.UserProfile;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.config.ftp.FtpConfig;
import com.smartwf.sm.config.ftp.FtpUtil;
import com.smartwf.sm.config.redis.StreamProducer;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2020-4-21 11:46:56
 * @Description: 文件上传控制器
 * @author WCH
 */
@RestController
@RequestMapping("file")
@Slf4j
@Api(description = "文件上传控制器")
public class FileUploadController {
	
	@Value("${web.upload-path}")
	private String localFilePath;
	
	@Autowired
	private FtpConfig ftpConfig;
	
	
	/**
	 * 功能说明：图片上传
	 * @author WCH
	 * @dateTime 2020-4-21 14:09:52
	 * */
	@PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	@ApiOperation(value = "上传图片接口", notes = "上传图片")
	public ResponseEntity<Result<?>> upload(@ApiParam(value = "用户图片", required = true) MultipartFile file) {
	    if (!file.isEmpty()) {
	        if (file.getContentType().contains(Constants.IMAGE)) {
	            try {
	                String temp = "images" + File.separator ;
	                // 获取图片的文件名
	                String fileName = file.getOriginalFilename();
	                // 获取图片的扩展名
	                String extensionName = fileName.substring(fileName.indexOf("."));
	                // 新的图片文件名 = 获取时间戳+"."图片扩展名
	                String newFileName = String.valueOf(System.currentTimeMillis())  + extensionName;
	                // 数据库保存的目录
	                String datdDirectory = temp.concat(newFileName);
	                // 文件路径
	                String filePath = localFilePath+File.separator+temp;
	                File dest = new File(filePath, newFileName);
	                if (!dest.getParentFile().exists()) {
	                    dest.getParentFile().mkdirs();
	                }
	                // 上传到指定目录
	                file.transferTo(dest);
	                boolean result =FtpUtil.ftpUpload(newFileName, ftpConfig.getUrl(),ftpConfig.getPort(),ftpConfig.getUsername(), ftpConfig.getPassword(), dest.toString(), ftpConfig.getRemotePath()+"/image/");
	        		if (result) {
	        			 return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "上传成功！"));
	        		} 
	                return ResponseEntity.status(HttpStatus.OK).body(Result.msg(datdDirectory));
	            }catch (Exception e){
	            	log.error("文件上传异常！{}", e.getMessage(), e);
	            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"失败！上传异常"));
	            }
	        }
	    }
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！请检查上传的文件"));
	}
	
	/**
	 * 功能说明：
	 * @author WCH
	 * @dateTime 2020-9-3 17:58:47
	 * */
    @GetMapping("ftpUpload")
    @ApiOperation(value = "ftp图片上传接口", notes = "ftp图片上传")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "localDir", value = "本地图片路径", dataType = "String")
    })
	public ResponseEntity<Result<?>> ftpUpload(String localDir ) {
		String fileName = UUID.randomUUID().toString();
		fileName=fileName+localDir.substring(localDir.lastIndexOf("."));
		boolean result =FtpUtil.ftpUpload(fileName, ftpConfig.getUrl(),ftpConfig.getPort(),ftpConfig.getUsername(), ftpConfig.getPassword(), localDir, ftpConfig.getRemotePath()+"/image/");
		if (result) {
			 return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS, "上传成功！"));
		} 
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("失败！请检查上传的文件"));
	}
	
	
	/**
     * @Description： 删除文件
     * @param filePath images/11.png
     * @return
     */
    @DeleteMapping("deleteFile")
    @ApiOperation(value = "删除文件接口", notes = "删除文件")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "filePath", value = "删除路径", dataType = "String",required = true)
    })
    @TraceLog(content = "删除文件", paramIndexs = {0})
	public ResponseEntity<Result<?>> delFile(HttpServletRequest request, String filePath) {
    	StringBuffer sb=new StringBuffer();
    	File delFile=null;
		try {
			filePath=sb.append(localFilePath).append("/").append(filePath).toString();
			delFile = new File(filePath);
			delFile.delete();
			return ResponseEntity.ok().body(Result.msg(Constants.EQU_SUCCESS,"删除成功！"));
		} catch (Exception e) {
			log.error("删除文件异常！{}", e.getMessage(), e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("删除失败！"));
	}
	
	/**
	 * 功能说明：文件上传
	 * @author WCH
	 * @dateTime 2020-4-21 14:09:52
	 * */
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
	    File targetFile = new File(filePath);
	    if(!targetFile.exists()){
	        targetFile.mkdirs();
	    }
	    FileOutputStream out = new FileOutputStream(filePath+fileName);
	    out.write(file);
	    out.flush();
	    out.close();
	}

	

	
}
