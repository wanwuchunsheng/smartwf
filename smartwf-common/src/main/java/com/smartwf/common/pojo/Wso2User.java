package com.smartwf.common.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author WCH
 * @Description: 用户角色
 */
@Data
public class Wso2User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String displayName;
	private List<Map<String,Object>> members;
	private List<Map<String,Object>> groups;
	
	
}
