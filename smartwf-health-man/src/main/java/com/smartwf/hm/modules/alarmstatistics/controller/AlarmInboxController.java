package com.smartwf.hm.modules.alarmstatistics.controller;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.annotation.ParamValidated.Add;
import com.smartwf.common.annotation.ParamValidated.Query;
import com.smartwf.common.annotation.ParamValidated.QueryParam;
import com.smartwf.common.annotation.ParamValidated.Update;
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
 * @Description: 警告收件箱控制器
 */
@RestController
@RequestMapping("alarminbox")
@Slf4j
@Api(description = "警告收件箱(重点机位)控制器")
@Validated
public class AlarmInboxController {
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	
	@Autowired
	private DefectService defectService;

	/**
	 * @Description: 分页查询警告信息 
	 *    列表信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectAlarmInforByPage")
    @ApiOperation(value = "警告分页查询接口", notes = "警告分页查询")
    @ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String",required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "警告码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "警告源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "警告描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "警告部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "警告级别(0危急 1严重 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "警告来源(0系统警告 1监控警告 2人工提交 3预警警告)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态( 0未处理 1已转工单 2处理中 3已处理 4已关闭 )", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
    	    @ApiImplicitParam(paramType = "query", name = "current", value = "第几页，默认1", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页多少条，默认10", dataType = "Integer")
    })
    public ResponseEntity<Result<?>> selectAlarmInforByPage( Page<FaultInformation> page,@Validated(value = QueryParam.class) FaultInformationVO bean) {
        try {
        	Result<?> result = this.alarmInboxService.selectAlarmInforByPage(page,bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("警告类型统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("警告类型统计信息错误！"));
    }
    
    /**
	 * @Description: 查询所有故障警告信息 
	 *   今天多少消息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    @PostMapping("selectAlarmInforByAll")
    @ApiOperation(value = "查询所有警告接口", notes = "查询所有警告")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String",required = true),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "警告码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "警告源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "警告描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "警告部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "警告级别(0危急 1严重 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "警告来源(0系统警告 1监控警告 2人工提交 3预警警告)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "警告状态( 0未处理 1已转工单 2处理中 3已处理 4已关闭 )", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> selectAlarmInforByAll(@Validated(value = QueryParam.class) FaultInformationVO bean) {
        try {
        	Result<?> result = this.alarmInboxService.selectAlarmInforByAll(bean);
        	return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("警告类型统计信息错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("警告类型统计信息错误！"));
    }
    
    /**
	 * @Description: 警告修改
	 * @param WCH   
	 * @param id
	 */
    @PutMapping("updateAlarmInforById")
    @ApiOperation(value = "警告修改接口", notes = "警告修改")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmCode", value = "警告码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmSource", value = "警告源", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmDescription", value = "警告描述", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLocation", value = "警告部位", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmLevel", value = "警告级别(0危急 1严重 2一般 3未知)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "faultType", value = "故障类型(0系统警告 1监控警告 2人工提交 3预警警告)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "alarmStatus", value = "故障状态(0未处理 1已转工单 2处理中 3已处理 4已关闭)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "operatingStatus", value = "操作状态(0默认  1重点关注)", dataType = "Integer"),
    	    @ApiImplicitParam(paramType = "query", name = "closureReason", value = "关闭原因", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "workOrderTitle", value = "工单标题", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "managementMeasures", value = "管控措施", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "startTime", value = "起始时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" ),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止时间(yyyy-MM-dd HH:mm:ss)", dataType = "Date" )
    })
    public ResponseEntity<Result<?>> updateAlarmInforById(@Validated(value = Update.class) FaultInformationVO bean) {
        try {
        	 this.alarmInboxService.updateAlarmInforById(bean);
        	 return ResponseEntity.status(HttpStatus.OK).body(Result.msg("修改成功"));
        } catch (Exception e) {
            log.error("警告修改错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("警告修改错误！"));
    }
    
    /**
	 * @Description: 主键查询
	 *    警告详细
	 * @param id
	 */
    @GetMapping("selectAlarmInforById")
    @ApiOperation(value = "警告主键查询（系统警告）接口", notes = "警告主键查询（系统警告）")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectAlarmInforById(@Validated(value = Query.class) FaultInformationVO bean) {
        try {
        	 Result<?> result = this.alarmInboxService.selectAlarmInforById(bean);
        	 return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("警告主键查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("警告主键查询错误！"));
    }
    
    /**
	 * @Description: 警告信息主键查询
	 *    缺陷详细
	 * @param id
	 */
    @GetMapping("selectDefectById")
    @ApiOperation(value = "警告主键查询（人工上报）接口", notes = "警告主键查询（人工上报）")
    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", dataType = "String", required = true ),
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String")
    })
    public ResponseEntity<Result<?>> selectDefectById(@Validated(value = Query.class) DefectVO bean) {
        try {
        	 Result<?> result = this.defectService.selectDefectById(bean);
        	 return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("缺陷信息主键查询错误！{}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"缺陷信息主键查询错误！"));
    }
    
    /**
	 * @Description: 实时警告未处理总数查询
	 *    实时警告总和
	 * @return
	 */
    @GetMapping("selectAlarmsCountByAll")
    @ApiOperation(value = "实时警告未处理总数接口", notes = "实时警告未处理总数查询")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String",required = true)
	})
    public ResponseEntity<Result<?>> selectAlarmsCountByAll(
    		@NotNull(message = "租户域不能为空") String tenantDomain,
    		@NotNull(message = "风场不能为空") String windFarm) {
        try {
        	Integer count=this.alarmInboxService.selectAlarmsCountByAll(tenantDomain,windFarm);
    	   	return ResponseEntity.status(HttpStatus.OK).body(Result.data(Constants.EQU_SUCCESS, count));
        } catch (Exception e) {
            log.error("实时警告总数信息查询错误！{}", e.getMessage(), e);
        }
	   return ResponseEntity.status(HttpStatus.OK).body(Result.data(Constants.EQU_SUCCESS, Constants.ZERO));
    }
    
    /**
	 * 今日新增总数查询接口
	 * @author WCH
	 * @return
	 */
    @GetMapping("selectAlarmsCountByToday")
    @ApiOperation(value = "今日新增总数查询接口", notes = "今日新增总数查询")
    @ApiImplicitParams({
	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String",required = true)
	})
    public ResponseEntity<Result<?>> selectAlarmsCountByToday(
    		@NotNull(message = "租户域不能为空") String tenantDomain,
    		@NotNull(message = "风场不能为空") String windFarm) {
        try {
        	Integer count=this.alarmInboxService.selectAlarmsCountByToday(tenantDomain,windFarm);
    	   	return ResponseEntity.status(HttpStatus.OK).body(Result.data(Constants.EQU_SUCCESS, count));
        } catch (Exception e) {
            log.error("今日新增总数查询错误！{}", e.getMessage(), e);
        }
	   return ResponseEntity.status(HttpStatus.OK).body(Result.data(Constants.EQU_SUCCESS, Constants.ZERO));
    }
    
    
    /**
   	 * @Description: 查询所有故障操作记录信息 
   	 *   故障操作记录
   	 * @param faultInfoId
   	 * @param tenantDomain
   	 * @return
   	 */
      @PostMapping("selectFaultRecordByAll")
      @ApiOperation(value = "查询所有警告操作记录接口", notes = "查询所有警告操作记录")
      @ApiImplicitParams({
  	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
       	    @ApiImplicitParam(paramType = "query", name = "faultInfoId", value = "警告表（主键）", dataType = "String", required = true )
      })
      public ResponseEntity<Result<?>> selectFaultRecordByAll(@Validated(value = QueryParam.class) FaultOperationRecord bean) {
	       try {
	       	 Result<?> result = this.alarmInboxService.selectFaultRecordByAll(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("查询所有警告操作记录信息错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("查询所有警告操作记录信息错误！"));
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
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String"),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
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
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("addKeyPosition")
	    @ApiOperation(value = "重点机位添加接口", notes = "重点机位添加")
	    @ApiImplicitParams({
    	      @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true),
    	      @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String",required = true),
	     	  @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码 ", dataType = "String", required = true ),
	     	  @ApiImplicitParam(paramType = "query", name = "assetNumber", value = "资产编码 ", dataType = "String", required = true ),
	     	  @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称 ", dataType = "String", required = true ),
	     	  @ApiImplicitParam(paramType = "query", name = "status", value = "运行状态（0运行 1停机） ", dataType = "int", required = true ),
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
	    @PostMapping("deleteKeyPosition")
	    @ApiOperation(value = "重点机位删除接口", notes = "重点机位删除")
	    @ApiImplicitParams({
    	      @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true ),
    	      @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场", dataType = "String",required = true),
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
	 	 *   重点风机的警告统计
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByCount")
	    @ApiOperation(value = "重点机位统计图表数据查询接口", notes = "重点机位统计图表数据查询")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String")
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
	 	 *   重点风机的警告统计
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByList")
	    @ApiOperation(value = "重点机位统计列表数据查询接口", notes = "重点机位统计列表数据查询")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String", required = true),
    	    @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String")
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
	 	 * @Description: 单个重点机位所有警告数据
	 	 * @author wch
	 	 * @date 2020-04-07
	 	 * @return
	 	 */
	    @PostMapping("selectKeyPositionByDeviceCode")
	    @ApiOperation(value = "重点机位(单个/全部)警告数据查询接口", notes = "单个重点机位所有警告数据查询")
	    @ApiImplicitParams({
    	        @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String" , required = true),
    	        @ApiImplicitParam(paramType = "query", name = "windFarm", value = "风场{支持多风场：中间逗号拼接}", dataType = "String"),
	     	    @ApiImplicitParam(paramType = "query", name = "deviceCode", value = "设备编码", dataType = "String"),
	     	    @ApiImplicitParam(paramType = "query", name = "deviceName", value = "设备名称", dataType = "String")
	    })
	    public ResponseEntity<Result<?>> selectKeyPositionByDeviceCode(KeyPosition bean) {
	       try {
	       	 Result<?> result = this.alarmInboxService.selectKeyPositionByDeviceCode(bean);
	       	 return ResponseEntity.status(HttpStatus.OK).body(result);
	       } catch (Exception e) {
	           log.error("单个重点机位所有警告数据查询错误！{}", e.getMessage(), e);
	       }
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg("单个重点机位所有警告数据查询错误！"));
	    }
	 
	    
	    /**
		 * @Description: 警告处理意见 - 添加
		 * @author WCH
		 * @dateTime 2020-7-20 17:55:35
		 * @param bean
		 * @return
		 */
	    @PostMapping("addFaultOperationRecord")
	    @ApiOperation(value = "添加警告处理意见接口", notes = "警告处理意见")
	    @ApiImplicitParams({
    	    @ApiImplicitParam(paramType = "query", name = "tenantDomain", value = "租户域", dataType = "String",required = true ),
		    @ApiImplicitParam(paramType = "query", name = "faultInfoId", value = "故障主键（故障表主键）", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "closureType", value = "操作类型{1处理记录  2处理意见}", dataType = "int", required = true),
		    @ApiImplicitParam(paramType = "query", name = "closureReason", value = "操作说明", dataType = "String", required = true),
		    @ApiImplicitParam(paramType = "query", name = "closureStatus", value = "操作状态{5待审核 0未处理  2处理中 3已处理 4已关闭}", dataType = "Integer"),
		    @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", dataType = "String" )
		})
	    public ResponseEntity<Result<?>> addFaultOperationRecord(@Validated(value = Add.class) FaultOperationRecord bean) {
	        try {
	        	this.alarmInboxService.addFaultOperationRecord(bean);
	        	return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"添加成功！"));
	        } catch (Exception e) {
	            log.error("添加警告处理意见错误！{}", e.getMessage(), e);
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.INTERNAL_SERVER_ERROR,"添加警告处理意见错误！"));
	    }
	    
}
