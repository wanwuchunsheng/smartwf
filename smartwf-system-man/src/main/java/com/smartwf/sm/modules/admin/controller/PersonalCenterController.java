package com.smartwf.sm.modules.admin.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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

import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.handler.UserProfile;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.config.ftp.SFtpConfig;
import com.smartwf.sm.config.ftp.SFtpUtil;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.PersonalCenterService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 个人中心控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("personal")
@Slf4j
@Api(description ="个人中心控制器")
public class PersonalCenterController {
	
	@Autowired
	private PersonalCenterService personalCenterService;
	
	/**
	 * 获取上传地址
	 * 
	 * */
	@Autowired
    private SFtpConfig config;
	
	
	/**
     * @Description： 修改用户密码
     * @return
     */
    @PutMapping("updateUserPwd")
    @ApiOperation(value = "修改密码接口", notes = "修改密码错误")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "用户（主键）", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "oldPwd", value = "旧密码", dataType = "String", required = true),
    	@ApiImplicitParam(paramType = "query", name = "newPwd", value = "新密码", dataType = "String", required = true),
    })
    @TraceLog(content = "修改密码", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateUserPwd(HttpServletRequest request, Integer id,String oldPwd,String newPwd) {
        try {
        	ResponseEntity<Result<?>> result= this.personalCenterService.updateUserPwd(request,id,oldPwd,newPwd);
            return result;
        } catch (Exception e) {
            log.error("修改密码错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"修改密码错误！"));
    }
    
    
    /**
     * @Description： 修改用户资料
     * @return
     */
    @PutMapping("updateUserInfo")
    @ApiOperation(value = "修改用户资料接口", notes = "修改用户资料资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
        @ApiImplicitParam(paramType = "query", name = "userName", value = "姓名", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "sex", value = "性别（0-女 1-男）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "mobile", value = "手机号", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "address", value = "住址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "phone", value = "电话", dataType = "String"),
 	    @ApiImplicitParam(paramType = "query", name = "mgrType", value = "等级（0-普通 1-管理员 2-超级管理员）", dataType = "Integer"),
 	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改用户资料", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateSysUser(UserInfoVO bean) {
        try {
            this.personalCenterService.updateUserInfo(bean);
            return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"修改成功"));
        } catch (Exception e) {
            log.error("修改用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"修改用户资料信息错误！"));
    }
	
	/**
     * @Description: 主键查询用户资料
     * @return
     */
    @GetMapping("selectUserInfoById")
    @ApiOperation(value = "主键查询个人资料接口", notes = "主键查询个人资料")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectSysUserById(UserInfo bean) {
        try {
            Result<?> result = this.personalCenterService.selectUserInfoById(bean);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            log.error("主键查询个人用户资料信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"主键查询个人用户信息错误！"));
    }
    
    /**
	 * 功能说明：图片上传
	 * @author WCH
	 * @dateTime 2020-4-21 14:09:52
	 * */
	@PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	@ApiOperation(value = "上传图片接口", notes = "上传图片")
	public ResponseEntity<Result<?>> upload(HttpServletRequest request, @ApiParam(value = "用户图片", required = true) MultipartFile file) {
	    if (!file.isEmpty()) {
	        if (file.getContentType().contains(Constants.IMAGE)) {
	            try {
	            	String temp = "image/";
	                // 获取图片的文件名
	                String fileName = file.getOriginalFilename();
	                // 获取图片的扩展名
	                String extensionName = fileName.substring(fileName.indexOf("."));
	                // 新的图片文件名 = 获取时间戳+"."图片扩展名
	                String newFileName = UUID.randomUUID().toString().replaceAll("-", "")  + extensionName;
	                // 数据库保存的目录
	                String datdDirectory = temp.concat(newFileName);
	                //上传文件到sftp
	                SFtpUtil.uploadFile( config,datdDirectory, file.getInputStream());
	                //修改用户头像信息
	                User user=UserProfile.getUser(request);
	                UserInfoVO userinfo=new UserInfoVO();
	                userinfo.setId(user.getId());
	                userinfo.setAvatar(datdDirectory);
	                personalCenterService.updateUserInfo(userinfo);
	        		//返回信息
	                return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS,datdDirectory));
	            }catch (Exception e){
	            	log.error("文件上传异常！{}", e.getMessage(), e);
	            	return ResponseEntity.ok(Result.msg(Constants.INTERNAL_SERVER_ERROR,"失败！上传异常"));
	            }
	        }
	    }
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"失败！请检查上传的文件"));
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
		try {
			 SFtpUtil.deleteFile(config, filePath);
			return ResponseEntity.ok().body(Result.msg(Constants.EQU_SUCCESS,"删除成功！"));
		} catch (Exception e) {
			log.error("删除文件异常！{}", e.getMessage(), e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"删除失败！"));
	}
    
}
