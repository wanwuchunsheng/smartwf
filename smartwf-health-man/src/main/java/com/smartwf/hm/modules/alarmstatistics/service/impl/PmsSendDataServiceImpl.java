package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpRequest;
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
	 *
	 */
	@Override
	public void faultWordOrder(FaultInformationVO bean) {
		//获取登录用户信息
		User user=UserThreadLocal.getUser();
		//查询数据
		FaultInformation fim=this.alarmInboxDao.selectById(bean);
		//封装参数
		Map<String,Object> map = new HashMap<>(16);
		//资产编码
		map.put("faultId", fim.getId());
		//工单状态 0查看 1待审核 2进行中  3待验收 4完成 5搁置 6拒绝 7草稿 8回收站
		map.put("orderStatus", String.valueOf(Constants.ONE));
		//工单类型 6
		map.put("orderType", String.valueOf(Constants.ORDERTYPE));
		//资源3
		map.put("orderSource", String.valueOf(Constants.ORDERSOURCES));
		//租户
		map.put("tenantDomain", user.getTenantDomain());
		//封装头部
		Map<String,String> headers = new HashMap<>(16);
		headers.put("smartwfToken", user.getSmartwfToken());
		//封装参数
		Map<String,Object> mapform = new HashMap<>(16);
		mapform.put("model",  JSONUtil.toJsonStr(map));
		log.info(JSONUtil.toJsonStr(map));
		String url=new StringBuffer().append(pmsServiceUri).append("/workOrder/add").toString();
		String res=HttpRequest.post(url).header(Constants.SMARTWF_TOKEN,  user.getSmartwfToken()).form(mapform).timeout(60000).execute().body();
		log.info("故障转工单返回："+res);
		if(StringUtils.isNotBlank(res)) {
			Map<String,Object> resmap=JSONUtil.parseObj(res);
			if(Constants.EQU_SUCCESS !=Convert.toInt(resmap.get("code"))) {
				log.error("故障报警信息转工单异常：{}");
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
		//获取登录用户信息
		User user=UserThreadLocal.getUser();
		//查询数据
		FaultInformation fim=this.alarmInboxDao.selectById(bean);
		//封装参数
		Map<String,Object> map = new HashMap<>(16);
		//资产编码
		map.put("faultId", fim.getId());
		//工单状态 0查看 1待审核 2进行中  3待验收 4完成 5搁置 6拒绝 7草稿 8回收站
		map.put("orderStatus", String.valueOf(Constants.ONE));
		//工单类型 6
		map.put("orderType", String.valueOf(Constants.ORDERTYPE));
		//资源3
		map.put("orderSource", String.valueOf(Constants.ORDERSOURCES));
		//租户
		map.put("tenantDomain", user.getTenantDomain());
		//封装头部
		Map<String,String> headers = new HashMap<>(16);
		headers.put("smartwfToken", user.getSmartwfToken());
		//封装参数
		Map<String,Object> mapform = new HashMap<>(16);
		mapform.put("model",  JSONUtil.toJsonStr(map));
		log.info(JSONUtil.toJsonStr(map));
		String url=new StringBuffer().append(pmsServiceUri).append("/workOrder/add").toString();
		String res=HttpRequest.post(url).header(Constants.SMARTWF_TOKEN,  user.getSmartwfToken()).form(mapform).timeout(60000).execute().body();
		log.info("缺陷转工单返回："+res);
		if(StringUtils.isNotBlank(res)) {
			Map<String,Object> resmap=JSONUtil.parseObj(res);
			if(Constants.EQU_SUCCESS !=Convert.toInt(resmap.get("code"))) {
				log.error("缺陷转工单异常：{}");
        		throw new CommonException(Constants.INTERNAL_SERVER_ERROR, "转工单异常{生产中心接口返回异常}！");
			}
		}
	}
	
	
}
