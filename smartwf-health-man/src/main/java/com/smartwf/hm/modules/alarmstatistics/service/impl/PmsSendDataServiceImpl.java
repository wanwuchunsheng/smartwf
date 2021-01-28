package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mockito.internal.util.collections.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.hm.modules.alarmstatistics.dao.AlarmInboxDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.PmsSendDataService;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;


/**
 * 发送数据到PMS
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j2
public class PmsSendDataServiceImpl implements PmsSendDataService {
	
	@Autowired
	private AlarmInboxDao alarmInboxDao;
	
	@Value("${spring.system.pms.server-uri}")
	private String pmsServiceUri;
	
	/**
	 * 故障报警信息转工单
	 * @author WCH
	 * @Date: 2020-7-23 09:29:25
	 * jSON:增加风场ID，管控措施
	 *  json规范：
		{
		  "orderContent": "",
		  "orderType": 6,
		  "createUserId": 301,
		  "orderSource": 3,
		  "orderDescribe": "故障转工单002",
		  "faultId": "ac1dbc9a08da457584ed9ad137ff0621",
		  "orderStatus": "1",
		  "orderTask": [
		    {
		      "taskContent": "ac1dbc9a08da457584ed9ad137ff0621故障",
		      "taskAsset": [
		        {
		          "assetId": "8fb8a075bda943bf"
		        }
		      ],
		      "source": 3
		    }
		  ]
		}
	 *
	 */
	@Override
	public void faultWordOrder(FaultInformationVO bean) {
		JSONObject json1=JSONUtil.createObj();
		JSONObject json2=JSONUtil.createObj();
		JSONObject json3=JSONUtil.createObj();
		List<JSONObject> jsonList= null;
		//获取登录用户信息
		User user=UserThreadLocal.getUser();
		//查询数据
		FaultInformation fim=this.alarmInboxDao.selectById(bean);
		//风场ID
		json1.put("farmId", fim.getWindFarm());
		//故障编码
		json1.put("faultId", fim.getId());
		//工单标题
		json1.put("orderContent", bean.getWorkOrderTitle());
		//管控措施
		json1.put("managementMeasures", bean.getManagementMeasures());
		//工单描述
		json1.put("orderDescribe", fim.getAlarmDescription());
		//工单类型 1巡检,2维护,3实验,4大小修,5培训,6其他)
		json1.put("orderType", Constants.ORDERTYPE);
		//创建人ID
		json1.put("createUserId", fim.getCreateUserId());
		//工单状态 0查看 1待审核 2进行中  3待验收 4完成 5搁置 6拒绝 7草稿 8回收站
		json1.put("orderStatus", String.valueOf(Constants.ONE));
		//工单来源
		//生产中心状态：工单来源(1.PT普通工单,2.JH计划转工单,3.GZ故障转工单,4.QX缺陷转工单,5.JG警告转工单)
		//健康中心状态：1-故障类型2-缺陷类型3告警类型
		Integer orderSource=null;
		String taskContent=null;
		switch (fim.getIncidentType()) {
		case 1:
			taskContent="故障";
			orderSource = 3;
			break;
        case 2:
        	taskContent="缺陷";
        	orderSource = 4;
			break;
        case 3:
        	taskContent="警告";
        	orderSource = 5;
			break;
		default:
			break;
		}
		json1.put("orderSource", orderSource);
		json2.put("source",orderSource );
		json2.put("taskContent", taskContent);
		json3.put("assetId", fim.getAssetNumber());
		//添加第3层
		jsonList= new ArrayList<>(16);
		jsonList.add(json3);
		json2.put("taskAsset",jsonList);
		//添加第2层
		jsonList= new ArrayList<>(16);
		jsonList.add(json2);
		json1.put("orderTask",jsonList);
		//封装头部
		Map<String,String> headers = new HashMap<>(16);
		headers.put("sessionId", user.getSessionId());
		//封装参数
		Map<String,Object> mapform = new HashMap<>(16);
		mapform.put("model",  JSONUtil.toJsonStr(json1));
		log.info(JSONUtil.toJsonStr(json1));
		String url=new StringBuffer().append(pmsServiceUri).append("/workOrder/add").toString();
		String res=HttpRequest.post(url).header(Constants.SESSION_ID,  user.getSessionId()).form(mapform).timeout(60000).execute().body();
		log.info("故障转工单返回："+res);
		if(StringUtils.isNotBlank(res)) {
			Map<String,Object> resmap=JSONUtil.parseObj(res);
			if(Constants.EQU_SUCCESS !=Convert.toInt(resmap.get("code"))) {
				log.error("转工单异常：{}");
        		throw new CommonException(Constants.INTERNAL_SERVER_ERROR, "转工单异常{生产中心接口返回异常}！");
			}
		}
	}

	/**
	 * 缺陷转工单
	 * @author WCH
	 * @Date: 2020-7-23 09:29:25
	 *
	 */
	@Override
	public void faultWordOrder(DefectVO bean) {
		JSONObject json1=JSONUtil.createObj();
		JSONObject json2=JSONUtil.createObj();
		JSONObject json3=JSONUtil.createObj();
		List<JSONObject> jsonList= null;
		//获取登录用户信息
		User user=UserThreadLocal.getUser();
		//查询数据
		FaultInformation fim=this.alarmInboxDao.selectById(bean);
		//风场ID
		json1.put("farmId", fim.getWindFarm());
		//故障编码
		json1.put("faultId", fim.getId());
		//工单标题
		json1.put("orderContent", bean.getWorkOrderTitle());
		//管控措施
		json1.put("managementMeasures", bean.getManagementMeasures());
		//工单描述
		json1.put("orderDescribe", fim.getAlarmDescription());
		//工单类型 1巡检,2维护,3实验,4大小修,5培训,6其他)
		json1.put("orderType", Constants.ORDERTYPE);
		//创建人ID
		json1.put("createUserId", fim.getCreateUserId());
		//工单状态 0查看 1待审核 2进行中  3待验收 4完成 5搁置 6拒绝 7草稿 8回收站
		json1.put("orderStatus", String.valueOf(Constants.ONE));
		//工单来源
		//生产中心状态：工单来源(1.PT普通工单,2.JH计划转工单,3.GZ故障转工单,4.QX缺陷转工单,5.JG警告转工单)
		//健康中心状态：1-故障类型2-缺陷类型3告警类型
		Integer orderSource=null;
		String taskContent=null;
		switch (fim.getIncidentType()) {
		case 1:
			taskContent="故障";
			orderSource = 3;
			break;
        case 2:
        	taskContent="缺陷";
        	orderSource = 4;
			break;
        case 3:
        	taskContent="警告";
        	orderSource = 5;
			break;
		default:
			break;
		}
		json1.put("orderSource", orderSource);
		json2.put("source",orderSource );
		json2.put("taskContent", taskContent);
		json3.put("assetId", fim.getAssetNumber());
		//添加第3层
		jsonList= new ArrayList<>(16);
		jsonList.add(json3);
		json2.put("taskAsset",jsonList);
		//添加第2层
		jsonList= new ArrayList<>(16);
		jsonList.add(json2);
		json1.put("orderTask",jsonList);
		//封装头部
		Map<String,String> headers = new HashMap<>(16);
		headers.put("sessionId", user.getSessionId());
		//封装参数
		Map<String,Object> mapform = new HashMap<>(16);
		mapform.put("model",  JSONUtil.toJsonStr(json1));
		log.info(JSONUtil.toJsonStr(json1));
		String url=new StringBuffer().append(pmsServiceUri).append("/workOrder/add").toString();
		String res=HttpRequest.post(url).header(Constants.SESSION_ID,  user.getSessionId()).form(mapform).timeout(60000).execute().body();
		log.info("故障转工单返回："+res);
		if(StringUtils.isNotBlank(res)) {
			Map<String,Object> resmap=JSONUtil.parseObj(res);
			if(Constants.EQU_SUCCESS !=Convert.toInt(resmap.get("code"))) {
				log.error("转工单异常：{}");
        		throw new CommonException(Constants.INTERNAL_SERVER_ERROR, "转工单异常{生产中心接口返回异常}！");
			}
		}
	}
	
	
}
