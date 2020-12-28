package com.smartwf.hm.modules.admin.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.CkUtils;
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
	
	@Value("${spring.system.pms.server-uri}")
	private String pmsServiceUri;

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
		        				String res=HttpRequest.post(new StringBuffer().append(pmsServiceUri).append("/workOrder/add").toString()).form(map).timeout(60000).execute().body();
		        				log.info("底层实时数据补全返回："+res);
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
								//0-危急 1-严重  2-一般  3-未知      底层：1040未知 1041低 1042中 1043高 
								fit.setAlarmLevel(Constants.ZERO);
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
							} catch (Exception e) {
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
							break;
						default:
							break;
				   };
				}
			}
		} catch (Exception e) {
			log.error("底层实时数据接入异常！{}-{}",e,e.getMessage());
		}
	}

}
