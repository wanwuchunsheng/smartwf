package com.smartwf.common.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * @Date: 2019/12/19 13:26
 */
@Data
public class Oauth2Client implements Serializable {

   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 授权临时票据 code 参数
	 */
	private String code;
    private String session_state;
    private String state;
    
    private String access_token;
    private String refresh_token;
    private String scope;
    private String id_token;
    private String token_type;
    private Integer expires_in;
    
}
