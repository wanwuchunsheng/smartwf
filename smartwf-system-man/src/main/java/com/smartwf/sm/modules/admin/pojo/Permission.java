package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * 权限表
 * @date 2019-11-29 13:42:42
 */
@Data
@Table(name ="sys_permission")
public class Permission extends BasePojo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
    @GeneratedValue(generator = "JDBC")
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
