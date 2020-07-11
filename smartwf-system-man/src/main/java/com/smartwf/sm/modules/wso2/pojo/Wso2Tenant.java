package com.smartwf.sm.modules.wso2.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
/**
 * 
 * @author WCH
 * 
 * */
@Data
@XmlRootElement
public class Wso2Tenant implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 添加的租户是否启用
	 * true-启用
	 * false-禁用
	 * */
	private Boolean active;
	/***
	 * 登录wso2系统管理
	 * 登录名
	 * 
	 * */
	private String admin;
	/***
	 * 登录wso2系统管理
	 * 登录密码(长度不小于5个)
	 * 
	 * */
	private String adminPassword;
	/**
	 * 创建日期时间
	 * 
	 * */
	private String createdDate;
	/**
	 * 邮箱创建日期时间
	 * 
	 * */
	private String email;
	
	/**
	 * 姓
	 * 
	 * */
	private String firstname;
	/**
	 * 名
	 * 
	 * */
	private String lastname;
	private String originatedService;
	private String successKey;
	/**
	 * 租户域名，一般后面带.com
	 *  不带添加会失败，wso2严格约定
	 * 
	 * */
	private String tenantDomain;
	private String tenantId;
	private String usagePlan;
	

}
