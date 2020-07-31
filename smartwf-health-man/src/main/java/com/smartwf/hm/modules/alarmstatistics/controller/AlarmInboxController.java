package com.smartwf.hm.modules.alarmstatistics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WCH
 * @Date: 2019-10-25 15:04:26
 * @Description: 报警收件箱控制器
 */
@RestController
@RequestMapping("alarminbox")
@Slf4j
@Api(description = "报警收件箱/重点机位控制器")
public class AlarmInboxController {
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	
	@Autowired
	private DefectService defectService;

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
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0危急 1严重 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0系统报警 1预警信息 2人工提交)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(5待审核 6驳回 0未处理 1已转工单 2处理中 3已处理 4已关闭 7回收站 8未解决)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
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
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0危急 1严重 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0系统报警 1预警信息 2人工提交)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(5待审核 6驳回 0未处理 1已转工单 2处理中 3已处理 4已关闭 7回收站 8未解决)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
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
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "报警码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "报警源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "报警描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "报警部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "报警级别(0危急 1严重 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0系统报警 1预警信息 2人工提交)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(5待审核 6驳回 0未处理 1已转工单 2处理中 3已处理 4已关闭 7回收站 8未解决)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
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
    @ApiOperation(value = "故障报警主键查询（系统报警）接口", notes = "故障报警主键查询（系统报警）")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String")
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
	 * @Description: 故障缺陷信息主键查询
	 *    缺陷详细
	 * @param id
	 */
    @GetMapping("selectDefectById")
    @ApiOperation(value = "故障报警主键查询（人工上报）接口", notes = "故障报警主键查询（人工上报）")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectDefectById(DefectVO bean) {
        try {
        	 Result<?> result = this.defectService.selectDefectById(bean);
        	 return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("缺陷信息主键查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"缺陷信息主键查询错误！"));
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
   	 * @param tenantDomain
   	 * @return
   	 */
      @PostMapping("selectFaultRecordByAll")
      @ApiOperation(value = "查询所有故障操作记录接口", notes = "查询所有故障操作记录")
      @ApiImplicitParams({
  	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
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
	    @ApiOperation(value = "重点机位批量查询接口", notes = "查询所有机位数据")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String")
	    })
	    public ResponseEntity<Result<?>> selectKeyPositionByAll(FaultOperationRecord bean) {
	       try {
	    	 //调用生产管理子系统API接口返回数据
	    	 List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    	   Map<String,String> map=null;
	    	   map=new HashMap<>(3);
	    	   map.put("DEVICE_NAME", "2号风机");
	    	   map.put("kks_full_code","equ002");
	    	   map.put("uuid","94105248410062197");
	    	   list.add(map);
	    	   
	    	   map=new HashMap<String, String>(3);
	    	   map.put("DEVICE_NAME", "1号风机");
	    	   map.put("kks_full_code","equ001");
	    	   map.put("uuid","94105248410062198");
	    	   list.add(map);
	    	  
	       	 Result<?> result = Result.data(list);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有机位信息错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有机位信息错误！"));
	    }
	    
	    /**
	 	 * @Description: 重点机位已添加机位数据查询接口
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPosition")
	    @ApiOperation(value = "重点机位已添加机位数据查询接口", notes = "重点机位已添加机位数据查询")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String")
	    })
	    public ResponseEntity<Result<?>> selectKeyPosition(KeyPosition bean) {
	       try {
	    	   Result<?> result = this.alarmInboxService.selectKeyPosition(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("重点机位已添加机位数据查询错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("重点机位已添加机位数据查询错误！"));
	    }
	    
	    /**
	 	 * @Description: 重点机位添加{提供生产中心调用}
	 	 *  批量添加，格式: 设备编号,设备名称,资产编码@设备编号,设备名称,资产编码
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("addKeyPosition")
	    @ApiOperation(value = "重点机位添加接口", notes = "重点机位添加")
	    @ApiImplicitParams({
    	      @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	     	  @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码 ", dataType = "String", required = true ),
	     	  @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码 ", dataType = "String", required = true ),
	     	  @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称 ", dataType = "String", required = true ),
	     	  @ApiImplicitParam(paramType = "query", name = "createUserId", value = "创建人ID ", dataType = "String"),
	     	  @ApiImplicitParam(paramType = "query", name = "createUserName", value = "创建人姓名 ", dataType = "String")
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
	 	 * @Description: 重点机位删除{提供生产中心调用}
	 	 *  通过重点机位表租户域下的资产编码
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @DeleteMapping("deleteKeyPosition")
	    @ApiOperation(value = "重点机位删除接口", notes = "重点机位删除")
	    @ApiImplicitParams({
    	      @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true ),
	     	  @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String", required = true)
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
	 	 * @Description: 重点机位统计数据-图表
	 	 *   重点风机的报警统计
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByCount")
	    @ApiOperation(value = "重点机位统计图表数据查询接口", notes = "重点机位统计图表数据查询")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true)
	    })
	    public ResponseEntity<Result<?>> selectKeyPositionByCount(KeyPosition bean) {
	       try {
	       	 Result<?> result = this.alarmInboxService.selectKeyPositionByCount(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有重点机位统计图表数据错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有重点机位统计图表数据错误！"));
	    }
	    
	    /**
	 	 * @Description: 重点机位统计数据-列表
	 	 *   重点风机的报警统计
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByList")
	    @ApiOperation(value = "重点机位统计列表数据查询接口", notes = "重点机位统计列表数据查询")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true)
	    })
	    public ResponseEntity<Result<?>> selectKeyPositionByList(KeyPosition bean) {
	       try {
	       	 Result<?> result = this.alarmInboxService.selectKeyPositionByList(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有重点机位统计列表数据错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有重点机位统计列表数据错误！"));
	    }
	    
	    /**
	 	 * @Description: 单个重点机位所有故障报警数据
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByDeviceCode")
	    @ApiOperation(value = "重点机位(单个/全部)故障报警数据查询接口", notes = "单个重点机位所有故障报警数据查询")
	    @ApiImplicitParams({
    	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String" , required = true),
	     	    @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码", dataType = "String")
	    })
	    public ResponseEntity<Result<?>> selectKeyPositionByDeviceCode(KeyPosition bean) {
	       try {
	       	 Result<?> result = this.alarmInboxService.selectKeyPositionByDeviceCode(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("单个重点机位所有故障报警数据查询错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("单个重点机位所有故障报警数据查询错误！"));
	    }
	 
	    
	    /**
		 * @Description: 故障处理意见
		 *    添加
		 * @author WCH
		 * @dateTime 2020-7-20 17:55:35
		 * @param bean
		 * @return
		 */
	    @PostMapping("addFaultOperationRecord")
	    @ApiOperation(value = "添加故障处理意见接口", notes = "故障处理意见")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true ),
		    @ApiImplicitParam(paramType = "query", name = "faultInfoId", value = "故障主键（故障表主键）", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "closureType", value = "操作类型{1处理记录  2处理意见}", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "closureReason", value = "操作说明", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "closureStatus", value = "操作状态{5待审核 0未处理  6驳回 2处理中 3已处理 4已关闭 7回收站 8未解决}", dataType = "Integer"),
		    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String" )
		})
	    public ResponseEntity<Result<?>> addFaultOperationRecord(FaultOperationRecord bean) {
	        try {
	        	this.alarmInboxService.addFaultOperationRecord(bean);
	        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"添加成功！"));
	        } catch (Exception e) {
	            log.error("添加故障处理意见错误！{}", e.getMessage(), e);
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"添加故障处理意见错误！"));
	    }
	    
}
