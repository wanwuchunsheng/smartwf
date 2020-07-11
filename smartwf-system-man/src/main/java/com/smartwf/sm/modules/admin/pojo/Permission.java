package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * 权限表
 * @date 2019-11-29 13:42:42
 * @author WCH
 */
@Data
@TableName("sys_permission")
public class Permission extends BasePojo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 操作用户表id
	 */
	private Integer actId;
	/**
	 * 资源表id
	 */
	private Integer resId;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 权重
	 */
	private String permissionCode;

}
