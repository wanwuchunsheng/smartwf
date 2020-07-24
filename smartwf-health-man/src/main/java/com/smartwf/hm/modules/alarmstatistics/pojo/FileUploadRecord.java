package com.smartwf.hm.modules.alarmstatistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 文件附件记录表
 * 
 * @author WCH
 * @email 
 * @date 2019-12-27 13:02:48
 */
@Data
@TableName("fault_file_record")
public class FileUploadRecord implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
	
	/**
	 * 外键pid
	 */
	private String pid;
	/**
	 * 附件路径
	 */
	private String filePath;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人id
	 */
	private Integer createUserId;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 租户域
	 */
	private String tenantDomain;
	
	

}
