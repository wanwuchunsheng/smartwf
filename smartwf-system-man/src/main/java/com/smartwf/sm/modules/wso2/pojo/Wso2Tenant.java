package com.smartwf.sm.modules.wso2.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.smartwf.common.annotation.ParamValidated.Add;
import com.smartwf.common.annotation.ParamValidated.Query;
import com.smartwf.common.annotation.ParamValidated.QueryParam;

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
	@NotNull(message = "租户启停状态不能为空", groups = Add.class)
	private Boolean active;
	/***
	 * 登录wso2系统管理
	 * 登录名
	 * 
	 * */
	@NotNull(message = "登录名不能为空", groups = Add.class)
	private String admin;
	/***
	 * 登录wso2系统管理
	 * 登录密码(长度不小于5个)
	 * 
	 * */
	@NotNull(message = "密码不能为空", groups = Add.class)
	@Size(min = 6,max = 12,message = "密码长度必须在5到12")
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
	@NotNull(message = "邮箱不能为空", groups = Add.class)
	private String email;
	
	/**
	 * 姓
	 * 
	 * */
	@NotNull(message = "姓不能为空", groups = Add.class)
	private String firstname;
	/**
	 * 名
	 * 
	 * */
	@NotNull(message = "名不能为空", groups = Add.class)
	private String lastname;
	private String originatedService;
	private String successKey;
	/**
	 * 租户域名，一般后面带.com
	 *  不带添加会失败，wso2严格约定
	 * 
	 * */
	@NotNull(message = "租户域不能为空", groups = Add.class)
	@NotNull(message = "租户域不能为空", groups = QueryParam.class)
	private String tenantDomain;
	
	@NotNull(message = "租户ID不能为空", groups = Query.class)
	private String tenantId;
	private String usagePlan;
	

}
