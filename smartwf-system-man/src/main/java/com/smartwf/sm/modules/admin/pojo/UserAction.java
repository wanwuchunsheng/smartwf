package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 用户操作表
 */
@Data
@Table(name = "sys_user_action")
public class UserAction extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
	private Integer id;
   
	/**
	 * 编码
	 */
	private String actCode;
	/**
	 * 名称
	 */
	private String actName;
	
	/**
	** 是否有效  0有效    1无效
     *       
	 */
	private Integer enable;
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
	
}
