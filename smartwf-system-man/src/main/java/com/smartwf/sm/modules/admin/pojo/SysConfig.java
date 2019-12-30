package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * 系统配置表
 * 
 * @email sunlightcs@gmail.com
 * @date 2019-12-27 13:02:48
 */
@Data
@TableName("sys_config")
public class SysConfig extends BasePojo implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 编码
	 */
	private String configCode;
	/**
	 * 名称
	 */
	private String configName;
	/**
	 * 参数键
	 */
	private String configKey;
	/**
	 * 参数值
	 */
	private String configValue;
	/**
	 * 系统内置
            0是 1否
	 */
	private Integer isSys;
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
	/**
	 * 是否有效
            0有效  
            1无效
            
	 */
	private Integer enable;

}
