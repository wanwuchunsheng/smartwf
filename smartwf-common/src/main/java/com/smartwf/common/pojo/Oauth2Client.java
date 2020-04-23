package com.smartwf.common.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * @Date: 2019/12/19 13:26
 */
@Data
public class Oauth2Client implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 授权临时票据 code 参数
	 */
	private String code;
	/**
	 * 返回类型：
	 * null:默认返回token 1：返回token&用户基本信息   
	 */
    private String smartwfType; 
    /**
	 * 前端系统token
	 */
    private String smartwfToken;
    
}
