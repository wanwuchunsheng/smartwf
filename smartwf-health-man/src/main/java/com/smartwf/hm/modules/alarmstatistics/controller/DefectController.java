package com.smartwf.hm.modules.alarmstatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 缺陷收件箱控制器
 */
@RestController
@RequestMapping("api/defect")
@Slf4j
@Api(description = "缺陷收件箱控制器")
public class DefectController {
	
	@Autowired
	private DefectService defectService;

	/**
	 * @Description: 缺陷工单处理
	 *     缺陷工单审核，缺陷工单状态修改（缺陷工单关闭）
	 * @param id
	 * @param alarmStatus 5待审核  6驳回  0未处理  1已转工单  2处理中  3已处理  4已关闭  7回收站  8未解决
     *
	 */
    @PutMapping("updateDefectById")
    @ApiOperation(value = "缺陷工单处理接口", notes = "缺陷工单处理")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "orderNumber", value = "缺陷工单号", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(5待审核  6驳回  0未处理  1已转工单  2处理中  3已处理  4已关闭  7回收站  8未解决)", dataType = "int",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "incidentType", value = "事变类型(1故障类型 2缺陷类型)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String")
    })
    public ResponseEntity<Result<?>> updateDefectById(DefectVO bean) {
        try {
        	 this.defectService.updateDefectById(bean);
        	 return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("缺陷工单处理错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("缺陷工单处理错误！"));
    }
	   
    /**
	 * @Description: 缺陷信息主键查询
	 *    缺陷详细
	 * @param id
	 */
    @GetMapping("selectDefectById")
    @ApiOperation(value = "缺陷信息主键查询接口", notes = "缺陷信息主键查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectDefectById(DefectVO bean) {
        try {
        	 Result<?> result = this.defectService.selectDefectById(bean);
        	 return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("缺陷信息主键查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("缺陷信息主键查询错误！"));
    }

    /**
	 * @Description: 分页查询缺陷信息 
	 *    列表信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectDefectByPage")
    @ApiOperation(value = "分页查询缺陷信息 接口", notes = "分页查询缺陷信息查询")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "事变等级(0危急 1严重 2一般 3未知)", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "incidentType", value = "事变类型(1故障类型 2缺陷类型)", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "报警来源(0故障 1报警 2人工缺陷)", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "manufacturers", value = "厂家", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "缺陷工单号", dataType = "String"),
	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "故障起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
        @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String" ),
        @ApiImplicitParam(paramType = "query", name = "discovererName", value = "发现人姓名（逗号拼接）", dataType = "String" ),
        @ApiImplicitParam(paramType = "query", name = "discovererId", value = "发现人id（逗号拼接）", dataType = "String" ),
        @ApiImplicitParam(paramType = "query", name = "stime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
        @ApiImplicitParam(paramType = "query", name = "etime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date"),
        @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
	})
    public ResponseEntity<Result<?>> selectDefectByPage( Page<DefectVO> page, DefectVO bean) {
        try {
        	Result<?> result = this.defectService.selectDefectByPage(page,bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("分页查询缺陷信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("分页查询缺陷信息错误！"));
    }
 
    /**
	 * @Description: 实时缺陷总数查询
	 * @return
	 */
    @GetMapping("selectDefectCountByAll")
    @ApiOperation(value = "实时缺陷总数查询接口", notes = "实时缺陷总数查询")
    public Integer selectDefectCountByAll() {
        try {
        	return this.defectService.selectDefectCountByAll();
        } catch (Exception e) {
            log.error("实时缺陷总数查询错误！{}", e.getMessage(), e);
        }
		return Constants.ZERO;
    }
    
    /**
   	 * @Description: 查询所有缺陷记录信息 
   	 * @param faultInfoId
   	 * @param tenantCode
   	 * @return
   	 */
      @PostMapping("selectDefectByAll")
      @ApiOperation(value = "查询所有缺陷记录信息接口", notes = "查询所有缺陷记录信息")
      @ApiImplicitParams({
       	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
       	    @ApiImplicitParam(paramType = "query", name = "faultInfoId", value = "故障报警表（主键）", dataType = "String", required = true )
      })
      public ResponseEntity<Result<?>> selectDefectByAll(FaultOperationRecord bean) {
	       try {
	       	 Result<?> result = this.defectService.selectDefectByAll(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有缺陷记录信息错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有缺陷记录信息错误！"));
      }
}
