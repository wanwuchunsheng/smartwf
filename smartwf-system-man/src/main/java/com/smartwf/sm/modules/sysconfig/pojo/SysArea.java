package com.smartwf.sm.modules.sysconfig.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-10-19 20:15:02
 */
@Data
@TableName("sys_area")
public class SysArea implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer cityId;
	/**
	 * 
	 */
	private String cityCode;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String name;

}
