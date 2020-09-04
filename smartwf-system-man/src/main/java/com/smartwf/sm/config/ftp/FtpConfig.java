package com.smartwf.sm.config.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class FtpConfig {
	/**
	 * ftp服务器地址
	 */
	@Value("${ftp.url}")
	private String url;
	
	/**
	 * ftp服务器端口
	 */
	@Value("${ftp.port}")
	private int port;
	
	/**
	 * ftp服务器用户名
	 */
	@Value("${ftp.username}")
	private String username;
	
	/**
	 * ftp服务器密码
	 */
	@Value("${ftp.password}")
	private String password;
	
	/**
	 * ftp服务器存放文件的路径
	 */
	@Value("${ftp.remotePath}")
	private String remotePath;
	
	
}
