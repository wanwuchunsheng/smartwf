package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * 字典数据表
 * 
 * @email sunlightcs@gmail.com
 * @date 2019-12-27 13:02:48
 * @author WCH
 */
@Data
@TableName("sys_dict_data")
public class Dictionary extends BasePojo implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	private Integer id;
	
	/**
	 * 上级id
	 */
	private Integer uid;
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 编码值
	 * 
	 * */
	private String dictValue;
	/**
	 * 编码
	 */
	private String dictCode;
	/**
	 * 名称
	 */
	private String dictName;
	/**
	 * 类型
	 */
	private Integer dictType;
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
