package com.smartwf.sm.modules.sysconfig.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 文档文件类型表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-10-22 20:42:37
 */
@Data
@TableName("basic_document_type")
public class DocumentType implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 租户域名
	 */
	private String tenantDomain;
	/**
	 * 风场id
	 */
	private Integer windFarm;
	/**
	 * 样式名称
	 */
	private String styleName;
	/**
	 * 类型 0-可编辑 1非编辑 2图片
	 */
	private Integer type;
	/**
	 * 上传最大值/M
	 */
	private Integer uploadSize;
	/**
	 * 上传最大值/M
	 */
	private String unit;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
