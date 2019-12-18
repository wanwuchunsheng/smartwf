package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 职务表
 */
@Data
@TableName("sys_post")
public class Post extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	private Integer id;
   
    /**
	 * 组织机构表id
	 */
	private Integer organizationId;
	/**
	 * 职务编码
	 */
	private String postCode;
	/**
	 * 职务名称
	 */
	private String postName;
	/**
	 * 职务分类
            0高管
            1中层
            2基层
            
	 */
	private Integer postType;
	/**
	 * 是否有效
            0有效  
            1无效
            
	 */
	private Integer enable;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	
}
