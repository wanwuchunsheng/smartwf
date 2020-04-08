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
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2019-10-25 15:04:26
 * @Description: 报警收件箱控制器
 */
@RestController
@RequestMapping("api/alarminbox")
@Slf4j
@Api(description = "报警收件箱控制器")
public class AlarmInboxController {
	
	@Autowired
	private AlarmInboxService alarmInboxService;

	/**
	 * @Description: 分页查询故障报警信息 
	 *    列表信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectAlarmInforByPage")
    @ApiOperation(value = "故障报警分页查询接口", notes = "故障报警分页查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0严重 1普通 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0故障 1报警 2缺陷)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1处理中 2已处理 3已关闭)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
    	    @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectAlarmInforByPage( Page<FaultInformation> page, FaultInformationVO bean) {
        try {
        	Result<?> result = this.alarmInboxService.selectAlarmInforByPage(page,bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("故障类型统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障类型统计信息错误！"));
    }
    
    /**
	 * @Description: 查询所有故障报警信息 
	 *   今天多少消息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectAlarmInforByAll")
    @ApiOperation(value = "查询所有故障报警接口", notes = "查询所有故障报警")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0严重 1普通 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0故障 1报警 2缺陷)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1处理中 2已处理 3已关闭)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> selectAlarmInforByAll(FaultInformationVO bean) {
        try {
        	Result<?> result = this.alarmInboxService.selectAlarmInforByAll(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("故障类型统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障类型统计信息错误！"));
    }
    
    /**
	 * @Description: 故障报警修改
	 *    重点关注
	 * @param id
	 */
    @PutMapping("updateAlarmInforById")
    @ApiOperation(value = "故障报警修改接口", notes = "故障报警修改")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0严重 1普通 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0故障 1报警 2缺陷)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1已转工单  2处理中 3已处理 4已关闭)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0-默认 1重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "closureReason", value = "关闭原因", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> updateAlarmInforById(FaultInformationVO bean) {
        try {
        	 this.alarmInboxService.updateAlarmInforById(bean);
        	 return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("故障报警修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障报警修改错误！"));
    }
    
    /**
	 * @Description: 主键查询
	 *    故障详细
	 * @param id
	 */
    @GetMapping("selectAlarmInforById")
    @ApiOperation(value = "故障报警主键查询接口", notes = "故障报警主键查询")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectAlarmInforById(FaultInformationVO bean) {
        try {
        	 Result<?> result = this.alarmInboxService.selectAlarmInforById(bean);
        	 return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("故障报警主键查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("故障报警主键查询错误！"));
    }
    
    /**
	 * @Description: 实时故障报警总数查询
	 *    实时故障总和
	 * @return
	 */
    @GetMapping("selectAlarmsCountByAll")
    @ApiOperation(value = "实时故障报警总数接口", notes = "实时故障报警总数查询")
    public Integer selectAlarmsCountByAll() {
        try {
        	return this.alarmInboxService.selectAlarmsCountByAll();
        } catch (Exception e) {
            log.error("实时故障报警总数信息查询错误！{}", e.getMessage(), e);
        }
		return Constants.ZERO;
    }
    
    /**
   	 * @Description: 查询所有故障报警记录信息 
   	 *   故障操作记录
   	 * @param faultInfoId
   	 * @param tenantCode
   	 * @return
   	 */
      @PostMapping("selectFaultRecordByAll")
      @ApiOperation(value = "查询所有故障操作记录接口", notes = "查询所有故障操作记录")
      @ApiImplicitParams({
       	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
       	    @ApiImplicitParam(paramType = "query", name = "faultInfoId", value = "故障报警表（主键）", dataType = "String", required = true )
      })
      public ResponseEntity<Result<?>> selectFaultRecordByAll(FaultOperationRecord bean) {
	       try {
	       	 Result<?> result = this.alarmInboxService.selectFaultRecordByAll(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有故障操作记录信息错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有故障操作记录信息错误！"));
      }
      
      
	    /**
	 	 * @Description: 机位查询
	 	 *   1）调用生产管理子系统，返回所有机位信息
	 	 *   2）获得JOSN数据，返回前端
	 	 *   信息内容： 设备编码，设备名称，资产编号
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByAll")
	    @ApiOperation(value = "批量机位查询接口", notes = "查询所有机位数据")
	    @ApiImplicitParams({
	     	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String")
	    })
	    public ResponseEntity<Result<?>> selectKeyPositionByAll(FaultOperationRecord bean) {
	       try {
	    	 //调用生产管理子系统API接口返回数据
	       	 Result<?> result = null;
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有机位信息错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有机位信息错误！"));
	    }
	    /**
	 	 * @Description: 重点机位添加
	 	 *  批量添加，格式: 设备编号,设备名称,资产编码@设备编号,设备名称,资产编码
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("addKeyPosition")
	    @ApiOperation(value = "重点机位添加接口", notes = "重点机位添加")
	    @ApiImplicitParams({
	     	   @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String"),
	     	   @ApiImplicitParam(paramType = "query", name = "remark", value = "批量添加格式:设备编号,设备名称,资产编码@设备编号,设备名称,资产编码", dataType = "String", required = true)
	    })
	    public ResponseEntity<Result<?>> addKeyPosition(KeyPosition bean) {
	       try {
	       	 this.alarmInboxService.addKeyPosition(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(Result.msg("添加成功"));
	       } catch (Exception e) {
	           log.error("重点机位添加错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("重点机位添加错误！"));
	    }
	    
	    /**
	 	 * @Description: 重点机位删除
	 	 *  通过重点机位表主键ID删除
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("deleteKeyPosition")
	    @ApiOperation(value = "重点机位删除接口", notes = "重点机位删除")
	    @ApiImplicitParams({
	     	   @ApiImplicitParam(paramType = "query", name = "id", value = "重点机位主键（id）", dataType = "String", required = true)
	    })
	    public ResponseEntity<Result<?>> deleteKeyPosition(KeyPosition bean) {
	       try {
	       	 this.alarmInboxService.deleteKeyPosition(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(Result.msg("删除成功"));
	       } catch (Exception e) {
	           log.error("重点机位删除错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("重点机位删除错误！"));
	    }
	    
	    /**
	 	 * @Description: 重点机位统计数据
	 	 *   重点风机的报警统计
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByCount")
	    @ApiOperation(value = "重点机位统计数据查询接口", notes = "重点机位统计数据查询")
	    @ApiImplicitParams({
	     	    @ApiImplicitParam(paramType = "query", name = "tenantCode", value = "租户（编码）", dataType = "String")
	    })
	    public ResponseEntity<Result<?>> selectKeyPositionByCount(KeyPosition bean) {
	       try {
	       	 Result<?> result = this.alarmInboxService.selectKeyPositionByCount(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有重点机位统计数据错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有重点机位统计数据错误！"));
	    }
        
}
