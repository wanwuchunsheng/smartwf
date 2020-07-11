package com.smartwf.sm.modules.admin.vo;

import java.util.Date;

import com.smartwf.sm.modules.admin.pojo.Permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 权限表
 * @date 2019-11-29 13:42:42
 * @author WCH
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PermissionVO extends Permission {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id批量删除
	 * 
	 * */
	private String ids; 
	/**
	 * 开始时间
	 * 
	 * */
    private Date startTime;
	/**
	 * 结束时间
	 * 
	 * */
	private Date endTime;
}
