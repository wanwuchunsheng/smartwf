package com.smartwf.proxy.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author WCH
 * @Description: 资源
 */
@Data
public class TreeResource implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	private Integer id;
   
    /**
	 * 父级id
	 */
	private Integer pid;
	/**
	 * 上级id
	 */
	private Integer uid;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 层次级别
	 */
	private Integer level;
	/**
	 * 资源编码
	 */
	private String resCode;
	/**
	 * 资源名称
	 */
	private String resName;
	/**
	 * 资源类型  1系统  2模块 3资源
    *
	 */
	private Integer resType;
	/**
	 * 资源链接
	 */
	private String resHref;
	/**
	 * 权重标识
	 */
	private String permission;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 是否有效   0有效    1无效
     *      
	 */
	private Integer enable;
	/**
	 * 用户操作ID
     *      
	 */
	private String actId;
	/**
	 * 用户操作CODE 
     *      
	 */
	private String actCode;
	
	private List<TreeResource> children;
	
}
