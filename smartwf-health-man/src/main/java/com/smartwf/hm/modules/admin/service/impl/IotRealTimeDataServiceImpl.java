package com.smartwf.hm.modules.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.hm.config.redis.StreamProducer;
import com.smartwf.hm.modules.admin.service.IotRealTimeDataService;
import com.smartwf.hm.modules.alarmstatistics.dao.AlarmInboxDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 说明：底层实时数据接入
 * @author WCH
 * */
@Service
@Slf4j
public class IotRealTimeDataServiceImpl implements IotRealTimeDataService{
	
	@Autowired
	private AlarmInboxDao alarmInboxDao;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private StreamProducer streamProducer;
	
	/** 生产中心 */
	@Value("${spring.system.pms.server-uri}")
	private String pmsServiceUri;
	/** 消息中心 */
	@Value("${spring.system.message.server-uri}")
	private String messageServiceUri;
	/** 系统管理中心  */
	@Value("${spring.system.management.server-uri}")
	private String managementServiceUri;

	/**
	 * 说明：底层实时数据接入
	 * @param fault_type
	 *        0-系统故障  1-监控警告 2-人工提交 3-预警警告
	 * @author WCH
	 * */
	@Override
	public void saveIotRealTimeData(String value) {
		try {
			if(StrUtil.isNotEmpty(value)) {
				Map<String,Object> map=JSONUtil.parseObj(value);
				//problemType  0-系统故障  1-监控警告  2-人工提交  3-预警警告
				if(map.containsKey("problemType")) {
				    Integer problemType=Convert.toInt(map.get("problemType"));
				    FaultInformation fit=null;
					switch (problemType) {
	        			case 1:
	        				//警告
	        				try {
		        				//调用生产中心补全数据
		        				String res=HttpRequest.post(new StringBuffer().append(pmsServiceUri).append("/asset/getAssetForHealth").toString()).form(map).timeout(15000).execute().body();
		        				if(StrUtil.isEmpty(res)) {
		        					//redis记录异常数据
		        					Map<String,String> errMap= new HashMap<>();
		        					map.put("msg", "补全数据为空");
		        					errMap.put("iot", JSONUtil.toJsonStr(map));
		        					streamProducer.sendMsg(Constants.HEALTH_TOPIC_ERROR, errMap);
		        					break;
		        				}
		        				Map<String,Object> resmap=JSONUtil.parseObj(res);
		        				//预警
								fit=new FaultInformation();
								//报警码
								fit.setAlarmCode(Convert.toStr(resmap.get("alarmCode")));
								//报警源
								fit.setAlarmSource(Convert.toStr(resmap.get("alarmSource")));
								//报警描述
								fit.setAlarmDescription(Convert.toStr(resmap.get("alarmescription")));
								//报警部位
								fit.setAlarmLocation(Convert.toStr(resmap.get("alarmLocation")));
								//厂家
								fit.setAlarmLocation(Convert.toStr(resmap.get("manufacturers")));
								//开始时间
								fit.setStartTime(Convert.toStr(map.get("time")));
								//1-故障  2-缺陷   3-警告
								fit.setIncidentType(3);
								//0-危急 1-严重  2-一般  3-未知 
								fit.setAlarmLevel(3);
						        //数据来源 0-系统故障  1-监控告警  2人工提交  3预警警告
								fit.setFaultType(problemType);
								//设备编码（风机ID）
								fit.setDeviceCode(Convert.toStr(resmap.get("deviceCode")));
								//设备名称（风机名称）
								fit.setDeviceName(Convert.toStr(resmap.get("deviceName")));
								//场站（风场ID）
								fit.setWindFarm(Convert.toStr(resmap.get("wfid")));
								//资产编码=风机ID
								fit.setAssetNumber(Convert.toStr(resmap.get("assetNumber")));
								//租户域  
								fit.setTenantDomain(Convert.toStr(resmap.get("tenantDomain")));
								//0未处理 2处理中 3已处理 4已关闭
								fit.setAlarmStatus(Constants.ZERO);
								fit.setCreateTime(new Date());
								fit.setUpdateTime(fit.getCreateTime());
							    this.alarmInboxDao.insert(fit);
							    //发送消息-消息中心
							    this.sendDataMsg(fit, "2_1");
							} catch (Exception e) {
								//redis记录异常数据
	        					Map<String,String> errMap= new HashMap<>();
	        					map.put("msg", "异常数据");
	        					errMap.put("iot", JSONUtil.toJsonStr(map));
	        					streamProducer.sendMsg(Constants.HEALTH_TOPIC_ERROR, errMap);
								log.error("告警数据补全接口异常{}-{}",e,e.getMessage());
							}
							break;
						case 3:
							//预警
							fit=new FaultInformation();
							//报警码
							fit.setAlarmCode(Convert.toStr(map.get("problemCode")));
							//报警源
							fit.setAlarmSource(Convert.toStr(map.get("problemSource")));
							//报警描述
							fit.setAlarmDescription(Convert.toStr(map.get("problemDescr")));
							//报警部位
							fit.setAlarmLocation(Convert.toStr(map.get("problemParts")));
							//开始时间
							fit.setStartTime(Convert.toStr(map.get("begin")));
							//结束时间
							fit.setEndTime(Convert.toStr(map.get("end")));
							//1-故障  2-缺陷   3-警告
							fit.setIncidentType(3);
							//0-危急 1-严重  2-一般  3-未知      底层：1040未知 1041低 1042中 1043高 
							String problemLevel=Convert.toStr(map.get("problemLevel"));
							if("1043".equals(problemLevel)) {
								fit.setAlarmLevel(0);
							}else if("1042".equals(problemLevel)) {
								fit.setAlarmLevel(1);
							}else if("1041".equals(problemLevel)) {
								fit.setAlarmLevel(2);
							}else {
								fit.setAlarmLevel(3);
							}
					        //数据来源 0-系统故障  1-监控告警  2人工提交  3预警警告
							fit.setFaultType(problemType);
							//设备编码（风机ID）
							fit.setDeviceCode(Convert.toStr(map.get("wtid")));
							//设备名称（风机名称）
							fit.setDeviceName(Convert.toStr(map.get("wtName")));
							//场站（风场ID）
							fit.setWindFarm(Convert.toStr(map.get("wfid")));
							//资产编码=风机ID
							fit.setAssetNumber(Convert.toStr(map.get("wtid")));
							//租户域  
							fit.setTenantDomain(CkUtils.verifyWindfarmTenant(redisService, fit.getWindFarm()));
							//0未处理 2处理中 3已处理 4已关闭
							fit.setAlarmStatus(Constants.ZERO);
							fit.setCreateTime(new Date());
							fit.setUpdateTime(fit.getCreateTime());
						    this.alarmInboxDao.insert(fit);
						    //推送消息-消息中心
						    this.sendDataMsg(fit,"2_1");
							break;
						default:
							Map<String,String> errMap= new HashMap<>();
							map.put("msg", "未消费数据");
        					errMap.put("iot", JSONUtil.toJsonStr(map));
        					streamProducer.sendMsg(Constants.HEALTH_TOPIC_ERROR, errMap);
							break;
				   };
				}
			}
		} catch (Exception e) {
			log.error("底层实时数据接入异常！{}-{}",e,e.getMessage());
		}
	}
	
	
	
	/**
	 * 说明：推送消息-》消息中心
	 * @author WCH
	 * @datetime 2021-2-26 09:55:11
	 * 
	 * {
			"messageSource": 1,
			"moduleSource": "1_1",
			"content": "工单消息:工单号202101122待审核",
			"createId": "35",
			"receiverId": "1,3,4",
			"receiverRole": "factory_director,chief_engineer",
			"businessState": 1,
			"messageState": 2,
			"businessId": "600a6092cd1c933249120a67",
			"tenantDomain": "carbon.super",
			"farmId": "6"
		}
		messageSource	消息来源于哪个子系统(1生产中心,2健康中心,3系统中心)
		moduleSource	消息来自哪个模块(1_1.生产中心-工单,1_2生产中心-计划,2_1健康中心-故障,2_2健康中心-预警,2_3健康中心缺陷,3_1系统中心-系统消息)
		createId	创建消息的用户编号
		receiverId	接收消息的用户id多个用逗号隔开
		receiverRole	接收消息的用户角色的英文名,多个用逗号隔开
		businessState	业务状态(工单状态,故障状态等)
		businessId	对应业务主键编号(工单编号,计划编号,故障编号,警告编号,缺陷编号等)
		tenantDomain	租户域
		farmId	风场编号
	 * 
	 * */
     public void sendDataMsg(FaultInformation bean,String type) {
    	 try {
    		 Map<String,Object> map = new HashMap<>();
     		//1）封装参数
 			map.put("messageSource", Constants.TWO);
 			map.put("moduleSource", "2_1");
 			map.put("content", "故障警告消息");
 			map.put("createId", "-1");
 			map.put("businessId", UUID.randomUUID());
 			map.put("businessState", bean.getIncidentType());
 			map.put("tenantDomain", bean.getTenantDomain());
 			map.put("farmId", bean.getWindFarm());
 			//2）从redis读取人员，角色列表
 			String res=this.redisService.get("msgUserRoles"+bean.getWindFarm());
 			if(StrUtil.isNotBlank(res)) {
 				Map<String,Object> resmap=JSONUtil.parseObj(res);
 				map.put("receiverId", resmap.get("userId"));
 				map.put("receiverRole", resmap.get("roleName"));
 				HttpRequest.post(new StringBuffer().append(messageServiceUri).append("/add").toString()).form("model", JSONUtil.toJsonStr(map)).form(map).timeout(10000).execute().body();
 			}else {
 				//3）从数据库读取人员，角色列表
 				String url=new StringBuffer().append(managementServiceUri).append("/route/selectWindfarmUserAndRole").toString();
 				res=HttpRequest.post(url).form(map).timeout(10000).execute().body();
 				if(StringUtils.isNotBlank(res)) {
 					//保存redis 15分钟
 					this.redisService.set("msgUserRoles"+bean.getWindFarm(), res, 900);
 					Map<String,Object> resmap=JSONUtil.parseObj(res);
 					map.put("receiverId", resmap.get("userId"));
 					map.put("receiverRole", resmap.get("roleName"));
 					HttpRequest.post(new StringBuffer().append(messageServiceUri).append("/add").toString()).form("model",JSONUtil.toJsonStr(map)).timeout(10000).execute().body();
 				}
 			}
		} catch (Exception e) {
			Map<String,String> errMap= new HashMap<>();
			errMap.put("msg", "推送消息中心异常！");
			errMap.put("message", JSONUtil.toJsonStr(bean));
			streamProducer.sendMsg(Constants.HEALTH_TOPIC_ERROR, errMap);
		}
	 }
	
}
