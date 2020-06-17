package com.smartwf.sm.modules.wso2.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.utils.DateUtils;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.wso2.pojo.Wso2Tenant;
import com.smartwf.sm.modules.wso2.service.Wso2TenantService;

import lombok.extern.log4j.Log4j2;

/**
 * 功能说明：wso2租户管理服务层
 * @author WCH
 * 
 * */
@Service
@Log4j2
public class Wso2TenantServiceImpl implements Wso2TenantService {
	
	@Autowired
	private Wso2Config wso2Config;
	
	
	/**
	 * 说明：租户新增
	 * @author WCH
	 * @Datatime 2020-6-11 18:30:17
	 * 
	 * */
	@Override
	public Map<String, Object> addTenant(Wso2Tenant bean) {
		//封装wso2参数规范
		StringBuffer sb=new StringBuffer();
		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.mgt.tenant.carbon.wso2.org\" xmlns:xsd=\"http://beans.common.stratos.carbon.wso2.org/xsd\">");
		sb.append(" <soapenv:Header/> <soapenv:Body><ser:addTenant> <ser:tenantInfoBean>");
		sb.append(" <xsd:active>").append(bean.getActive()).append("</xsd:active>");
		sb.append(" <xsd:admin>").append(bean.getAdmin()).append("</xsd:admin> ");
		sb.append(" <xsd:adminPassword>").append(bean.getAdminPassword()).append("</xsd:adminPassword>");
		sb.append(" <xsd:email>").append(bean.getEmail()).append("</xsd:email>");
		sb.append(" <xsd:firstname>").append(bean.getFirstname()).append("</xsd:firstname>");
		sb.append(" <xsd:lastname>").append(bean.getLastname()).append("</xsd:lastname>");
		sb.append(" <xsd:tenantDomain>").append(bean.getTenantDomain()).append("</xsd:tenantDomain>");
		if(StringUtils.isNotBlank(bean.getTenantId())) {
			sb.append(" <xsd:tenantId>").append(bean.getTenantId()).append("</xsd:tenantId>");
		}
		sb.append(" </ser:tenantInfoBean> </ser:addTenant> </soapenv:Body> ");
		sb.append(" </soapenv:Envelope>");
        try {
        	StringBuffer path=new StringBuffer().append(wso2Config.tokenServerUri).append("/services/TenantMgtAdminService?wsdl");
        	log.info("soap start："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	String res=HttpClientUtil.doPostSoap1_1(String.valueOf(path), String.valueOf(sb),null);
        	log.info("soap end："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	if(StringUtils.isNoneBlank(res)) {
        		return JsonUtil.multilayerXmlToMap(res);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * @Description：模拟wso2查询租户
     * @param code,session_state和state
     * @return
     */
	@Override
	public Map<String, Object> selectTenant(Wso2Tenant bean) {
		//封装wso2参数规范
		StringBuffer sb=new StringBuffer();
		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.mgt.tenant.carbon.wso2.org\">");
		sb.append(" <soapenv:Header/> <soapenv:Body><ser:getTenant> ");
		sb.append(" <ser:tenantDomain>").append(bean.getTenantDomain()).append("</ser:tenantDomain>");
		sb.append("  </ser:getTenant> </soapenv:Body> ");
		sb.append(" </soapenv:Envelope> ");
        try {
        	StringBuffer path=new StringBuffer().append(wso2Config.tokenServerUri).append("/services/TenantMgtAdminService?wsdl");
        	log.info("soap start："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	String res=HttpClientUtil.doPostSoap1_1(String.valueOf(path), String.valueOf(sb),null);
        	log.info("soap end："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	if(StringUtils.isNoneBlank(res)) {
        		return JsonUtil.multilayerXmlToMap(res);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * @Description：模拟wso2删除租户
     * @param code,session_state和state
     * @return
     */
	@Override
	public Map<String, Object> deleteTenant(Wso2Tenant bean) {
		//封装wso2参数规范
		StringBuffer sb=new StringBuffer();
		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.mgt.tenant.carbon.wso2.org\">");
		sb.append(" <soapenv:Header/> <soapenv:Body><ser:deleteTenant> ");
		sb.append(" <ser:tenantDomain>").append(bean.getTenantDomain()).append("</ser:tenantDomain>");
		sb.append(" </ser:deleteTenant> </soapenv:Body> ");
		sb.append(" </soapenv:Envelope> ");
        try {
        	StringBuffer path=new StringBuffer().append(wso2Config.tokenServerUri).append("/services/TenantMgtAdminService?wsdl");
        	log.info("soap start："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	String res=HttpClientUtil.doPostSoap1_1(String.valueOf(path), String.valueOf(sb),null);
        	log.info("soap end："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	if(StringUtils.isNoneBlank(res)) {
        		return JsonUtil.multilayerXmlToMap(res);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /**
     * @Description：模拟wso2租户修改
     * @param code,session_state和state
     * @return
     */
	@Override
	public Map<String, Object> updateTenant(Wso2Tenant bean) {
		//封装wso2参数规范
		StringBuffer sb=new StringBuffer();
		sb.append(" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.mgt.tenant.carbon.wso2.org\" xmlns:xsd=\"http://beans.common.stratos.carbon.wso2.org/xsd\">");
		sb.append(" <soapenv:Header/> <soapenv:Body><ser:updateTenant> <ser:tenantInfoBean>");
		if(StringUtils.isNotBlank(bean.getTenantId())){
			sb.append(" <xsd:tenantId>").append(bean.getTenantId()).append("</xsd:tenantId>");
		}
		if(null!=bean.getActive()){
			sb.append(" <xsd:active>").append(bean.getActive()).append("</xsd:active>");
		}
		if(StringUtils.isNotBlank(bean.getAdmin())){
			sb.append(" <xsd:admin>").append(bean.getAdmin()).append("</xsd:admin> ");
		}
		if(StringUtils.isNotBlank(bean.getAdminPassword())){
			sb.append(" <xsd:adminPassword>").append(bean.getAdminPassword()).append("</xsd:adminPassword>");
		}
		if(StringUtils.isNotBlank(bean.getEmail())){
			sb.append(" <xsd:email>").append(bean.getEmail()).append("</xsd:email>");
		}
		if(StringUtils.isNotBlank(bean.getFirstname())){
			sb.append(" <xsd:firstname>").append(bean.getFirstname()).append("</xsd:firstname>");
		}
		if(StringUtils.isNotBlank(bean.getLastname())){
			sb.append(" <xsd:lastname>").append(bean.getLastname()).append("</xsd:lastname>");
		}
		if(StringUtils.isNotBlank(bean.getTenantDomain())){
			sb.append(" <xsd:tenantDomain>").append(bean.getTenantDomain()).append("</xsd:tenantDomain>");
		}
		sb.append(" </ser:tenantInfoBean> </ser:updateTenant> </soapenv:Body> ");
		sb.append(" </soapenv:Envelope>");
        try {
        	StringBuffer path=new StringBuffer().append(wso2Config.tokenServerUri).append("/services/TenantMgtAdminService?wsdl");
        	log.info("soap start："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	String res=HttpClientUtil.doPostSoap1_1(String.valueOf(path), String.valueOf(sb),null);
        	log.info("soap end："+DateUtils.parseDateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        	if(StringUtils.isNoneBlank(res)) {
        		return JsonUtil.multilayerXmlToMap(res);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
