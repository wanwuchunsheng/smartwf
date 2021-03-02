package com.smartwf.sm.modules.admin.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.annotation.ParamValidated.Add;
import com.smartwf.common.annotation.ParamValidated.Query;
import com.smartwf.common.annotation.ParamValidated.QueryParam;
import com.smartwf.common.annotation.ParamValidated.Update;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 组织架构控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("org")
@Slf4j
@Api(description ="组织架构控制器")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserInfoService userInfoService;
	
    
    /**
	 * @Description: 查询所有组织架构（树形结构）
	 * @return
	 */
    @GetMapping("selectOrganizationByPage")
    @ApiOperation(value = "树形查询所有数据接口", notes = "查询组织架构所有数据")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "orgCode", value = "组织架构代码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "orgName", value = "组织架构名称", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "orgType", value = "组织架构类型（0-分公司  1-风场   2-一般组织）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "Date")
    })
    public ResponseEntity<Result<?>> selectOrganizationByPage(@Validated(value = QueryParam.class) OrganizationVO bean) {
        try {
            Result<?> result = this.organizationService.selectOrganizationByAll(bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询组织架构信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询组织架构信息错误！"));
    }
  
    /**
     * @Description: 主键查询组织架构
     * @return
     */
    @GetMapping("selectOrganizationById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询组织架构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectOrganizationById(@Validated(value = Query.class) Organization bean) {
        try {
            Result<?> result = this.organizationService.selectOrganizationById(bean);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("主键查询组织架构信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键查询用户信息错误！"));
    }
    
    /**
     * @Description: 添加组织架构
     * @return
     */
    @PostMapping("saveOrganization")
    @ApiOperation(value = "添加接口", notes = "添加组织架构接口")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "uid", value = "上级（主键）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "pid", value = "根节点（主键）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "level", value = "层次级别", dataType = "int", required = true),
	    	@ApiImplicitParam(paramType = "query", name = "orgCode", value = "组织架构编码", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "orgName", value = "组织架构名称", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "orgType", value = "组织架构类型（0-分公司  1-风场   2-一般组织）", dataType = "int",required = true),
		    @ApiImplicitParam(paramType = "query", name = "typeLevel", value = "组织架构类型（0分公司 1一般组织 2风场 3风电 4风光 5光伏 6综合）", dataType = "int",required = true),
		    @ApiImplicitParam(paramType = "query", name = "wfmark", value = "标记{逗号拼接}", dataType = "String"),
		    @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "添加组织架构", paramIndexs = {0})
    public ResponseEntity<Result<?>> saveOrganization(HttpSession session,@Validated(value = Add.class) Organization bean) {
		this.organizationService.saveOrganization(bean);
    	return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
    }
    
    /**
     * @Description： 修改组织架构
     * @return
     */
    @PutMapping("updateOrganization")
    @ApiOperation(value = "修改接口", notes = "修改组织架构资料")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "int", required = true),
    	@ApiImplicitParam(paramType = "query", name = "uid", value = "上级（主键）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "pid", value = "根节点（主键）", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "sort", value = "排序", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "level", value = "层次级别", dataType = "Integer"),
    	@ApiImplicitParam(paramType = "query", name = "orgCode", value = "组织架构编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "orgName", value = "组织架构名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "orgType", value = "组织架构类型（0-分公司  1-风场   2-一般组织）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "typeLevel", value = "组织架构类型（0分公司 1一般组织 2风场 3风电 4风光 5光伏 6综合）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "wfmark", value = "标记{逗号拼接}", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "enable", value = "状态（0-启用 1-禁用）", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    @TraceLog(content = "修改组织架构", paramIndexs = {0})
    public ResponseEntity<Result<?>> updateOrganization(@Validated(value = Update.class) Organization bean) {
        this.organizationService.updateOrganization(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
    }
    
    /**
     * @Description： 删除组织架构
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
    @DeleteMapping("deleteOrganization")
    @ApiOperation(value = "删除接口", notes = "删除组织架构")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键单个删除", dataType = "int", required = true)
    })
    @TraceLog(content = "删除组织架构系统用户", paramIndexs = {0})
    public ResponseEntity<Result<?>> deleteOrganization(OrganizationVO bean) {
    	if(null==bean.getId() && StringUtils.isBlank(bean.getIds()) ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("主键参数为空！"));
    	}
    	this.organizationService.deleteOrganization(bean);
        return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
    }

    /**
	 * @Description: 查询组织机构人员信息-部分返回（知识中心提供）
	 * @return
	 */
    @GetMapping("selectUserOrganizationByParam")
    @ApiOperation(value = "查询组织机构人员信息-部分返回（知识中心提供）", notes = "查询组织机构人员信息（知识中心提供）")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "组织机构id", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "orgType", value = "是否返回人员信息（0-否 1：是）", dataType = "int",required = true)
    })
    public ResponseEntity<Result<?>> selectUserOrganizationByParam(OrganizationVO bean) {
        try {
            Result<?> result = this.organizationService.selectUserOrganizationByParam(bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("查询组织机构人员信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询组织机构人员信息错误！"));
    }
    
    /**
	 * @Description: 查询组织机构人员信息-全局返回（知识中心提供）
	 * @return
	 */
    @GetMapping("selectUserOrganizationByAll")
    @ApiOperation(value = "查询组织机构人员信息-全局返回（知识中心提供）", notes = "查询组织机构人员信息（知识中心提供）")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true)
    })
    public ResponseEntity<Result<?>> selectUserOrganizationByAll(OrganizationVO bean) {
        try {
            Result<?> result = this.organizationService.selectUserOrganizationByAll(bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("查询组织机构人员信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询组织机构人员信息错误！"));
    }
    
    /**
	 * @Description: 全局用户查询（知识中心提供）
	 * @return
	 */
    @GetMapping("selectUserInfoByParam")
    @ApiOperation(value = "全局用户查询（知识中心提供）", notes = "全局用户查询（知识中心提供）")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户（主键）", dataType = "int", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "模糊查询（人员姓名，手机号）", dataType = "String",required = true)
    })
    public ResponseEntity<Result<?>> selectUserInfoByParam(UserInfo bean) {
        try {
            Result<?> result = this.userInfoService.selectUserInfoByParam(bean);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("查询组织机构人员信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询组织机构人员信息错误！"));
    }
    
    
}
