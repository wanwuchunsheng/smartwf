package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * @author WCH
 * 
 * */
@Data
@TableName("sys_exception_log")
public class ExceptionLog implements Serializable{
       
	    private static final long serialVersionUID = 1L;

		/**
		 * 主键
		 */
		@TableId(type = IdType.AUTO)
		private Integer id;
		/**
		 * 日志类型
		 */
		private String logType;
		/**
		 * 日志模块
		 */
		private String logModul;
		/**
		 * 请求uri
		 */
		private String logUri;
		/**
		 * 请求方法
		 */
		private String logMethod;
		/**
		 * 请求参数
		 */
		private String logRequParam;
		/**
		 * 异常信息
		 */
		private String logDesc;
		/**
		 * 请求IP
		 */
		private String ipAddress;
		/**
		 * 操作人
		 */
		private String logUser;
		/**
		 * 创建时间
		 */
		private Date createTime;
}
