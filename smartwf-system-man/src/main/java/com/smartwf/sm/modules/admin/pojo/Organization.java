package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 组织架构表
 * @author WCH
 */
@Data
@TableName("sys_organization")
public class Organization extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "id不能为空", groups = Update.class)
	@NotNull(message = "id不能为空", groups = Query.class)
	private Integer id;
   
    /**
	 * 父级id
	 */
	@NotNull(message = "父级id不能为空！", groups = Add.class)
	private Integer pid;
	/**
	 * 上级id
	 */
	@NotNull(message = "上级id不能为空！", groups = Add.class)
	private Integer uid;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 机构编码
	 */
	@NotNull(message = "机构编码不能为空！", groups = Add.class)
	private String orgCode;
	/**
	 * 机构名称
	 */
	@NotNull(message = "机构编码不能为空！", groups = Add.class)
	private String orgName;
	/**
	 * 机构类型
	 */
	private String orgType;
	/**
	 * 风场
	 */
	private String windFarm;
	
	/**
	 * 风场标记（是否为风场）
	 * 0-是
	 * 1-否
	 */
	private String wfmark;
	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 租户id
	 */
	@NotNull(message = "租户id不能为空！", groups = Add.class)
	@NotNull(message = "租户id不能为空！", groups = QueryParam.class)
	private Integer tenantId;
	/**
	 * 是否有效
       0有效  
       1无效   
	 */
	@NotNull(message = "是否启用不能为空！", groups = Add.class)
	private Integer enable;
	/**
	 * 层次级别
	 */
	@NotNull(message = "层次级别不能为空！", groups = Add.class)
	private Integer level;
	
}
