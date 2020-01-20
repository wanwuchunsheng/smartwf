package com.smartwf.common.pojo;

import lombok.Data;

import java.util.Date;

import javax.validation.groups.Default;

/**
 * @Auther: 
 * @Description: 基础javaBean
 */
@Data
public class BasePojo {

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
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改人id
	 */
	private Integer updateUserId;
	/**
	 * 修改人姓名
	 */
	private String updateUserName;

	/**
	 * 继承Default类，可以在不指定@Validated的group时，使用所有默认校验方式。
	 */
    public interface Delete extends Default {
    }
    public interface Update extends Default {
    }
    public interface Add extends Default {
    }
    public interface Query extends Default {
    }
}
