package com.smartwf.sm.modules.admin.pojo;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @Date: 2019-10-25 15:35:18
 * @author WCH
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class GlobalData implements Serializable {

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 租户
	 * 
	 * */
	private Integer tenantId;
     
	/**
	 * 
	 * //刷新类型  0全部 1租户 2组织机构 3职务  4数据字典
	 * 
	 * */
    private String flushType;

}
