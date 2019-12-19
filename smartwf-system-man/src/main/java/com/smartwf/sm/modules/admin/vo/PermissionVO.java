package com.smartwf.sm.modules.admin.vo;

import java.util.List;

import com.smartwf.sm.modules.admin.pojo.Permission;

import lombok.Data;

/**
 * 权限表
 * @date 2019-11-29 13:42:42
 */
@Data
public class PermissionVO extends Permission {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids; //id批量删除
	
	private Integer mgrType;//0普通  1管理员  2超级管理员
	
	
}
