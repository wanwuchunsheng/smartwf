package com.smartwf.sm.modules.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 门户菜单表
 * @author chenshun
 * @date 2020-07-30 16:47:46
 */
@Data
@TableName("sys_route")
public class Route implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自动编号
	 */
	@TableId
	private Integer id;
	/**
	 * 路由地址
	 */
	private String path;
	/**
	 * 路由名称
	 */
	private String pathName;
	/**
	 * 重定向地址
	 */
	private String redirect;
	/**
	 * 是否隐藏显示
	 */
	private Boolean hidden;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 系统发布地址
	 */
	private String address;
	/**
	 * 菜单显示排序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date updateTime;
	/**
	 * 更新时间
	 */
	private Date createTime;
	/**
	 * 备注
	 * 0-门户
	 * 1-docker
	 * 2-k8s
	 */
	private String remark;

}
