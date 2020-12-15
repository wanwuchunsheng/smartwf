package com.smartwf.sm.modules.admin.vo;

import com.smartwf.sm.modules.admin.pojo.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * @author WCH
 * 
 * */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UserInfoVO extends UserInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id批量删除
	 */
	private String ids; 
	/**
	 * 组织架构ids
	 */
	private String organizationIds; 
	/**
	 * 职务id
	 */
	private String postIds; 
	/**
	 *  角色id
	 */
	private String roleIds; 
	
	/**
	 * 组织架构
	 */
	private String organizationName; 
	/**
	 * 职务
	 */
	private String postName; 
	/**
	 *  角色
	 */
	private String roleName; 
	
	/**
	 *  风场名称
	 */
	private String windfarmName; 

}
